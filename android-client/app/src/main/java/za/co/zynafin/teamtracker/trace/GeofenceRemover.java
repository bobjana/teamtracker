package za.co.zynafin.teamtracker.trace;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.LocationServices;

public class GeofenceRemover extends GeofenceSyncronizer {

    public GeofenceRemover(Context context) {
        super(context, Constants.REQUEST_TYPE.REMOVE);
    }

    @Override
    protected void doPerformAction() {
        Log.d(Constants.TAG, "About to remove geofences");
        LocationServices.GeofencingApi.removeGeofences(mLocationClient, createGeofencingPendingIntent());
    }


}
