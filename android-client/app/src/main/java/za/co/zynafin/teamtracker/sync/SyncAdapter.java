/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

import retrofit.RestAdapter;
import za.co.zynafin.teamtracker.content.TeamTrackerProviderClient;
import za.co.zynafin.teamtracker.core.RestAdapterFactory;
import za.co.zynafin.teamtracker.customer.Customer;
import za.co.zynafin.teamtracker.customer.CustomerService;

class SyncAdapter extends AbstractThreadedSyncAdapter {

    protected static final String TAG = SyncAdapter.class.getName();

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(TAG, "Syncing data...." + extras);

        RestAdapter restAdapter = RestAdapterFactory.create(getContext(), account);
        List<Customer> customersFromServer = restAdapter.create(CustomerService.class).list();
        sync(customersFromServer, provider);

    }

    private void sync(List<Customer> customers, ContentProviderClient provider) {
        try {
            //REMOVE ALL
            TeamTrackerProviderClient.removeAllCustomer(provider);
            new GeofenceRemover(getContext()).run();
            //ADD ALL
            GeofenceRequester geofenceRequester = new GeofenceRequester(getContext());
            for (Customer customer : customers) {
                Uri uri = TeamTrackerProviderClient.addCustomer(provider, customer);
                geofenceRequester.add(customer.getId().toString(), toLatLng(customer.getGeoLocation()), customer.getCoverage());
            }
            geofenceRequester.run();
        } catch (RemoteException e) {
            Log.e(TAG,"Unable to sync customers", e);
        }
    }

    private LatLng toLatLng(String geoLocation) {
        String[] parts = geoLocation.split(",");
        return new LatLng(new Double(parts[0]), new Double(parts[1]));
    }

}
