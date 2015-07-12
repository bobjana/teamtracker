package za.co.zynafin.teamtracker.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import za.co.zynafin.teamtracker.content.TeamTrackerProviderClient;
import za.co.zynafin.teamtracker.core.RestAdapterFactory;
import za.co.zynafin.teamtracker.customer.Customer;
import za.co.zynafin.teamtracker.customer.CustomerService;
import za.co.zynafin.teamtracker.trace.Trace;
import za.co.zynafin.teamtracker.trace.TraceService;

class SyncAdapter extends AbstractThreadedSyncAdapter {

    protected static final String TAG = SyncAdapter.class.getName();

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(TAG, "Syncing data...." + extras);

        syncFromServer(account, provider);
        syncToServer(account, provider);
    }

    private void syncToServer(Account account, final ContentProviderClient provider) {
        try {
            RestAdapter restAdapter = RestAdapterFactory.create(getContext(), account);
            TraceService traceService = restAdapter.create(TraceService.class);
            List<Trace> traces = TeamTrackerProviderClient.listAllTracers(provider);
            for (final Trace trace : traces) {
                traceService.save(trace, new Callback<Void>() {
                    @Override
                    public void success(Void aVoid, Response response) {
                        Log.v(TAG, String.format("Trace '%s' sucessfully synced to server", trace.toString()));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(TAG, "Unable to sync traces to server", error.getCause());
                    }
                });
            }

            TeamTrackerProviderClient.removeAllTracer(provider);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to syncToServer", e);
        }
    }

    private void syncFromServer(Account account, ContentProviderClient provider) {
        try {
            RestAdapter restAdapter = RestAdapterFactory.create(getContext(), account);
            CustomerService customerService = restAdapter.create(CustomerService.class);
            //REMOVE ALL
            TeamTrackerProviderClient.removeAllCustomer(provider);
            new GeofenceRemover(getContext()).run();
            //ADD ALL
            GeofenceRequester geofenceRequester = new GeofenceRequester(getContext());
            List<Customer> customersFromServer = customerService.list();
            for (Customer customer : customersFromServer) {
                Uri uri = TeamTrackerProviderClient.addCustomer(provider, customer);
                geofenceRequester.add(customer.getId().toString(), toLatLng(customer.getGeoLocation()), customer.getCoverage());
            }
            geofenceRequester.run();
        } catch (RemoteException e) {
            Log.e(TAG,"Unable to syncFromServer", e);
        }
    }

    private LatLng toLatLng(String geoLocation) {
        String[] parts = geoLocation.split(",");
        return new LatLng(new Double(parts[0]), new Double(parts[1]));
    }

}
