package za.co.zynafin.teamtracker.events;


import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import za.co.zynafin.teamtracker.R;
import za.co.zynafin.teamtracker.account.Constants;
import za.co.zynafin.teamtracker.sync.GeoFence;

import static za.co.zynafin.teamtracker.Constants.CONNECTION_TIME_OUT_MS;
import static za.co.zynafin.teamtracker.Constants.TAG;

public class GeoFenceTransitionIntentService extends IntentService
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    private GeoFenceEvent currentGeoFence;

    public GeoFenceTransitionIntentService() {
        super(GeoFenceTransitionIntentService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    /**
     * Handles incoming intents.
     *
     * @param intent The Intent sent by Location Services. This Intent is provided to Location
     *               Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geoFenceEvent = GeofencingEvent.fromIntent(intent);
        if (geoFenceEvent.hasError()) {
            int errorCode = geoFenceEvent.getErrorCode();
            Log.e(TAG, "Location Services error: " + errorCode);
        } else {




//            int transitionType = geoFenceEvent.getGeofenceTransition();
//            if (Geofence.GEOFENCE_TRANSITION_ENTER == transitionType) {
                // Connect to the Google Api service in preparation for sending a DataItem.
                mGoogleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                // Get the geofence id triggered. Note that only one geofence can be triggered at a
                // time in this example, but in some cases you might want to consider the full list
                // of geofences triggered.

                List<Geofence> geofences = geoFenceEvent.getTriggeringGeofences();
                for (int index = 0; index < geofences.size(); index++) {
                    String requestId = geofences.get(index).getRequestId();
                    handleGeoFenceEvent(new GeoFenceEvent(GeofenceUtils.getTransitionString(geoFenceEvent.getGeofenceTransition()), new GeoFence(Long.getLong(requestId))));
                }


//                String triggeredGeoFenceId = geoFenceEvent.getTriggeringGeofences().get(0).getRequestId();
//                // Create a DataItem with this geofence's id. The wearable can use this to create
//                // a notification.
//                final PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(GEOFENCE_DATA_ITEM_PATH);
//                putDataMapRequest.getDataMap().putString(KEY_GEOFENCE_ID, triggeredGeoFenceId);
//
//                if (mGoogleApiClient.isConnected()) {
//                    Wearable.DataApi.putDataItem(mGoogleApiClient, putDataMapRequest.asPutDataRequest()).await();
//                } else {
//                    Log.e(TAG, "Failed to send data item: " + putDataMapRequest + " - Client disconnected from Google Play Services");
//                }


                Toast.makeText(this, getString(R.string.entering_geofence), Toast.LENGTH_SHORT).show();
                mGoogleApiClient.disconnect();



//
//
//            } else if (Geofence.GEOFENCE_TRANSITION_EXIT == transitionType) {
//
//
//
//
//                // Delete the data item when leaving a geofence region.
//                mGoogleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
//                Wearable.DataApi.deleteDataItems(mGoogleApiClient, GEOFENCE_DATA_ITEM_URI).await();
//                showToast(this, R.string.exiting_geofence);
//                mGoogleApiClient.disconnect();
//
//
//
//
//            }
        }
    }

    /**
     * Showing a toast message, using the Main thread
     */
    private void showToast(final Context context, final int resourceId) {
        Handler mainThread = new Handler(Looper.getMainLooper());
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void handleGeoFenceEvent(GeoFenceEvent geoFenceEvent) {
        if (currentGeoFence != null && geoFenceEvent.equals(currentGeoFence)){
            Log.d(GeofenceUtils.TAG,getString(R.string.geofence_transition_duplicate_title,
                geoFenceEvent.getTransitionType(),geoFenceEvent.getGeoFence().getId())
            );
            return;
        }
        Log.i(GeofenceUtils.TAG,
                getString(
                        R.string.geofence_transition_notification_title,
                        geoFenceEvent.getTransitionType(),geoFenceEvent.getGeoFence().getId())
        );
        saveGeoFenceEvent(geoFenceEvent);
        currentGeoFence = geoFenceEvent;

        LocalBroadcastManager.getInstance(this).sendBroadcast(
                new Intent().setAction(GeofenceUtils.ACTION_GEOFENCE_TRANSITION).putExtra(GeofenceUtils.EXTRA_GEOFENCE_EVENT, geoFenceEvent)
        );
    }

    private void saveGeoFenceEvent(GeoFenceEvent geoFenceEvent) {
        ContentValues mNewValues = new ContentValues();
        mNewValues.put(Constants.GeoFenceEventColumns.COLUMN_GEO_FENCE_ID, geoFenceEvent.getGeoFence().getId());
        mNewValues.put(Constants.GeoFenceEventColumns.COLUMN_TYPE, geoFenceEvent.getTransitionType());
        mNewValues.put(Constants.GeoFenceEventColumns.COLUMN_DATE, geoFenceEvent.getDate().getTime());
        getContentResolver().insert(Constants.GeoFenceEventColumns.CONTENT_URI, mNewValues);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
    }

    @Override
    public void onConnectionSuspended(int cause) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
    }
}