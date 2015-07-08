package za.co.zynafin.teamtracker.events;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.Date;
import java.util.List;

import za.co.zynafin.teamtracker.R;
import za.co.zynafin.teamtracker.account.Constants;

public class GeoFenceReceiver extends BroadcastReceiver {

    Context context;
    Intent broadcastIntent = new Intent();

    public GeoFenceReceiver() {
        System.out.println("here");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        broadcastIntent.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES);
        if (LocationClient.hasError(intent)) {
            handleError(intent);
        } else {
            handleEnterExit(intent);
        }
    }

    @SuppressLint("StringFormatMatches")
    private void handleError(Intent intent){
        int errorCode = LocationClient.getErrorCode(intent);
        String errorMessage = LocationServiceErrorMessages.getErrorString(context, errorCode);
        Log.e(GeofenceUtils.TAG, context.getResources().getString(R.string.geofence_transition_error_detail, errorMessage));

        broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_ERROR)
                .putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, errorMessage);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
    }

    private void handleEnterExit(Intent intent) {
        int transition = LocationClient.getGeofenceTransition(intent);
        List<Geofence> geofences = LocationClient.getTriggeringGeofences(intent);
        for (int index = 0; index < geofences.size(); index++) {
            String requestId = geofences.get(index).getRequestId();
            Log.d(GeofenceUtils.TAG, context.getResources().getString(
                            R.string.geofence_transition_notification_title,
                            GeofenceUtils.getTransitionString(transition),
                            requestId)
            );
            saveEvent(GeofenceUtils.getTransitionString(transition), requestId);
            broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_TRANSITION).
                    putExtra("id", requestId).
                    putExtra("transition", GeofenceUtils.getTransitionString(transition));
            LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        }
    }

    private void saveEvent(String transitionType, String geoFenceId) {
        ContentValues mNewValues = new ContentValues();
        //todo: derive user id
        mNewValues.put(Constants.GeoFenceEventColumns.COLUMN_GEO_FENCE_ID, geoFenceId);
        mNewValues.put(Constants.GeoFenceEventColumns.COLUMN_TYPE, transitionType);
        mNewValues.put(Constants.GeoFenceEventColumns.COLUMN_DATE, new Date().getTime());
        context.getContentResolver().insert(Constants.GeoFenceEventColumns.CONTENT_URI, mNewValues);
    }

}
