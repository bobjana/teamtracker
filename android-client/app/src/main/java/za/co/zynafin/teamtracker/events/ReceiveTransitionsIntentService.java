package za.co.zynafin.teamtracker.events;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Date;
import java.util.List;

import za.co.zynafin.teamtracker.R;
import za.co.zynafin.teamtracker.account.Constants;
import za.co.zynafin.teamtracker.sync.GeoFence;

/**
 * This class receives geofence transition events from Location Services, in the
 * form of an Intent containing the transition type and geofence id(s) that triggered
 * the event.
 */
public class ReceiveTransitionsIntentService{
        /*extends IntentService {

    private GeoFenceEvent currentGeoFence;

    public ReceiveTransitionsIntentService() {
        super("ReceiveTransitionsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.addCategory(za.co.zynafin.teamtracker.events.GeofenceUtils.CATEGORY_LOCATION_SERVICES);


        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()

        if (LocationClient.hasError(intent)) {
            int errorCode = LocationClient.getErrorCode(intent);
            String errorMessage = LocationServiceErrorMessages.getErrorString(this, errorCode);
            Log.e("ReceiveTransitionIntentService", getString(R.string.geofence_transition_error_detail, errorCode, errorMessage));
            broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_ERROR).putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, errorMessage);
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        } else {
            int transition = LocationClient.getGeofenceTransition(intent);
            List<Geofence> geofences = LocationClient.getTriggeringGeofences(intent);
            for (int index = 0; index < geofences.size(); index++) {
                String requestId = geofences.get(index).getRequestId();
                handleGeoFenceEvent(new GeoFenceEvent(GeofenceUtils.getTransitionString(transition),new GeoFence(Long.getLong(requestId))));
            }

        }
    }

    private void handleGeoFenceEvent(GeoFenceEvent geoFenceEvent) {
        if (currentGeoFence != null && geoFenceEvent.equals(currentGeoFence)){
            Log.d(GeofenceUtils.TAG,
                    getString(
                            R.string.geofence_transition_duplicate_title,
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
            new Intent().setAction(GeofenceUtils.ACTION_GEOFENCE_TRANSITION).
                putExtra(GeofenceUtils.EXTRA_GEOFENCE_EVENT, geoFenceEvent)
        );
    }

    private void saveGeoFenceEvent(GeoFenceEvent geoFenceEvent) {
        ContentValues mNewValues = new ContentValues();
        mNewValues.put(Constants.GeoFenceEventColumns.COLUMN_GEO_FENCE_ID, geoFenceEvent.getGeoFence().getId());
        mNewValues.put(Constants.GeoFenceEventColumns.COLUMN_TYPE, geoFenceEvent.getTransitionType());
        mNewValues.put(Constants.GeoFenceEventColumns.COLUMN_DATE, geoFenceEvent.getDate().getTime());
        getContentResolver().insert(Constants.GeoFenceEventColumns.CONTENT_URI, mNewValues);
    }
    */

}
