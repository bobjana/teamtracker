package za.co.zynafin.teamtracker.sync;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.LocationServices;

import za.co.zynafin.teamtracker.sync.GeofenceSyncronizer;
import za.co.zynafin.teamtracker.trace.Constants;

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
