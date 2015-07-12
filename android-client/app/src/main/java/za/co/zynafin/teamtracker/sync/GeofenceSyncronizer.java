package za.co.zynafin.teamtracker.sync;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationStatusCodes;

import za.co.zynafin.teamtracker.R;
import za.co.zynafin.teamtracker.trace.Constants;
import za.co.zynafin.teamtracker.trace.GeofenceTransitionsIntentService;

public abstract class GeofenceSyncronizer implements
        GoogleApiClient.ConnectionCallbacks,
        ResultCallback<Status>, GoogleApiClient.OnConnectionFailedListener {

    private Context mContext;
    private Constants.REQUEST_TYPE requestType;
    protected boolean mInProgress;
    private PendingIntent geofencePendingIntent;
    protected GoogleApiClient mLocationClient;

    public GeofenceSyncronizer(Context context, Constants.REQUEST_TYPE requestType) {
        mContext = context;
        this.requestType = requestType;
        mLocationClient = null;
        mInProgress = false;
    }

    public void setInProgressFlag(boolean flag) {
        mInProgress = flag;
    }

    public boolean getInProgressFlag() {
        return mInProgress;
    }

    public void run() {
        if (!mInProgress) {
            requestConnection();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private void performAction(){
        if (!mLocationClient.isConnected()) {
            Log.d(Constants.TAG,"Google api client not available");
            return;
        }
        doPerformAction();
    }

    protected abstract void doPerformAction();

    protected void requestConnection() {
        getLocationClient().connect();
    }

    private GoogleApiClient getLocationClient() {
        if (mLocationClient == null) {
            mLocationClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        return mLocationClient;
    }

    private void requestDisconnection() {
        mInProgress = false;
        getLocationClient().disconnect();
    }

    @Override
    public void onConnected(Bundle arg0) {
        Log.d(Constants.TAG, mContext.getString(R.string.connected));
        performAction();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mInProgress = false;
        Log.d(Constants.TAG, mContext.getString(R.string.disconnected));
        mLocationClient = null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mInProgress = false;
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {

            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult((Activity) mContext,
                        Constants.CONNECTION_FAILURE_RESOLUTION_REQUEST);
            /*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             */
            } catch (IntentSender.SendIntentException e) {
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

            Intent errorBroadcastIntent = new Intent(Constants.ACTION_CONNECTION_ERROR);
            errorBroadcastIntent.addCategory(Constants.CATEGORY_LOCATION_SERVICES)
                    .putExtra(Constants.EXTRA_CONNECTION_ERROR_CODE,
                            connectionResult.getErrorCode());
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(errorBroadcastIntent);
        }
    }

    protected PendingIntent createGeofencingPendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(mContext, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onResult(Status status) {
        Log.d(Constants.TAG, "Result: " + status);
        Intent broadcastIntent = new Intent();

        if (status.getStatusCode() == LocationStatusCodes.SUCCESS) {
            Log.d(Constants.TAG, requestType + " geofences was successfully performed");
            Log.d(Constants.TAG, status.toString());
            broadcastIntent.setAction(requestType.name());
        } else {
            Log.e(Constants.TAG, requestType + " Failed: " + status.getStatusCode());
            broadcastIntent.setAction(Constants.ACTION_GEOFENCE_ERROR);
            broadcastIntent.putExtra(Constants.EXTRA_GEOFENCE_STATUS, status.getStatusCode());
        }

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(broadcastIntent);

        requestDisconnection();
    }
}