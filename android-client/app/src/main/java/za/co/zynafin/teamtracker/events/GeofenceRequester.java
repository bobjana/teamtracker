package za.co.zynafin.teamtracker.events;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationStatusCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import za.co.zynafin.teamtracker.R;
import za.co.zynafin.teamtracker.account.MainActivity;
import za.co.zynafin.teamtracker.sync.GeoFence;

/**
 * Class for connecting to Location Services and requesting geofences.
 * <b>
 * Note: Clients must ensure that Google Play services is available before requesting geofences.
 * </b> Use GooglePlayServicesUtil.isGooglePlayServicesAvailable() to check.
 * <p/>
 * <p/>
 * To use a GeofenceRequester, instantiate it and call AddGeofence(). Everything else is done
 * automatically.
 */
public class GeofenceRequester
        implements
//        GoogleApiClient.OnAddGeofencesResultListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "GEO_REQUESTER";
    private final Context context;
    private PendingIntent mGeofencePendingIntent;
    private Collection<GeoFence> mCurrentGeofences;
//    private LocationClient mLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private boolean mInProgress;

    public GeofenceRequester(Context context) {
        this.context = context;
        mGeofencePendingIntent = null;
        mGoogleApiClient =  new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mInProgress = false;
    }

    public void setInProgressFlag(boolean flag) {
        mInProgress = flag;
    }

    public boolean getInProgressFlag() {
        return mInProgress;
    }

    public PendingIntent getRequestPendingIntent() {
        return createRequestPendingIntent();
    }

    public void addGeofences(List<GeoFence> geoFences) throws UnsupportedOperationException {
        mCurrentGeofences = geoFences;
        if (!mInProgress) {
            mInProgress = true;
            requestConnection();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private void requestConnection() {
        mGoogleApiClient.connect();
    }

//    private GooglePlayServicesClient getLocationClient() {
//        if (mLocationClient == null) {
//            mLocationClient = new LocationClient(context, this, this);
//        }
//        return mLocationClient;
//    }

    private void continueAddGeofences() {
        Log.d(TAG, "Start adding geoFences to location service");
        // Get a PendingIntent that Location Services issues when a geofence transition occurs
        mGeofencePendingIntent = createRequestPendingIntent();
        // Send a request to add the current geofences
        List<Geofence> locationGeofences = new ArrayList<Geofence>();
        for (GeoFence serverGeoFence : mCurrentGeofences) {
            Log.d(TAG, serverGeoFence.toString());
            locationGeofences.add(new Geofence.Builder().
                            setRequestId(serverGeoFence.getId().toString()).
                            setExpirationDuration(Geofence.NEVER_EXPIRE).
                            //todo: enable loitering for production purposes
//                            setLoiteringDelay(GeofenceUtils.DEFAULT_LOITERING_PERIOD_IN_MS).
//                            setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT).
                            setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT).
                            setNotificationResponsiveness(GeofenceUtils.DEFAULT_NOTIFICATION_RESPONSIVENESS_MS).
                            setCircularRegion(serverGeoFence.getLat(), serverGeoFence.getLng(), serverGeoFence.getRadius()).
                            build()
            );
        }


        mGoogleApiClient.addGeofences(new ArrayList<Geofence>(locationGeofences), mGeofencePendingIntent, this);


        Log.d(TAG, "Completed adding geoFences to location service");
    }

    /*
     * Handle the result of adding the geofences
     */
    @Override
    public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {
        Intent broadcastIntent = new Intent();
        String msg;
        if (LocationStatusCodes.SUCCESS == statusCode) {
            msg = context.getString(R.string.add_geofences_result_success,
                    Arrays.toString(geofenceRequestIds));
            Log.d(GeofenceUtils.TAG, msg);
            broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCES_ADDED)
                    .addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
                    .putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, msg);
        } else {
            msg = context.getString(
                    R.string.add_geofences_result_failure,
                    statusCode,
                    Arrays.toString(geofenceRequestIds)
            );
            Log.e("GeoFenceRequester", msg);
            broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_ERROR)
                    .addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
                    .putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, msg);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        requestDisconnection();
    }

    /**
     * Get a location client and disconnect from Location Services
     */
    private void requestDisconnection() {
        mInProgress = false;
        getLocationClient().disconnect();
    }

    /*
     * Called by Location Services once the location client is connected.
     *
     * Continue by adding the requested geofences.
     */
    @Override
    public void onConnected(Bundle arg0) {
        Log.d(GeofenceUtils.TAG, context.getString(R.string.connected));
        continueAddGeofences();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDisconnected() {
        mInProgress = false;
        Log.d(GeofenceUtils.TAG, context.getString(R.string.disconnected));
        mLocationClient = null;
    }

    private PendingIntent createRequestPendingIntent() {
        if (mGeofencePendingIntent == null) {
            Intent intent = new Intent(context, ReceiveTransitionsIntentService.class);
//            Intent intent = new Intent(GeofenceUtils.ACTION_GEOFENCE_TRANSITION);
//            Intent intent = new Intent("za.co.zynafin.teamtracker.ACTION_GEOFENCE_TRANSITION");
//            Intent intent = new Intent(context, GeoFenceReceiver.class);
            mGeofencePendingIntent = PendingIntent.getService(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return mGeofencePendingIntent;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mInProgress = false;
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                //todo: don't think this activity is correct
                connectionResult.startResolutionForResult(new MainActivity(),
                        GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

            /*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             */
            } catch (SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }

        /*
         * If no resolution is available, put the error code in
         * an error Intent and broadcast it back to the main Activity.
         * The Activity then displays an error dialog.
         * is out of date.
         */
        } else {
            Intent errorBroadcastIntent = new Intent(GeofenceUtils.ACTION_CONNECTION_ERROR);
            errorBroadcastIntent.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
                    .putExtra(GeofenceUtils.EXTRA_CONNECTION_ERROR_CODE,
                            connectionResult.getErrorCode());
            LocalBroadcastManager.getInstance(context).sendBroadcast(errorBroadcastIntent);
        }
    }
}
