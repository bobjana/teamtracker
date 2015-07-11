package za.co.zynafin.teamtracker.trace;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeofenceRequester extends GeofenceSyncronizer {

    private List<Geofence> geoList = new ArrayList<>();

    private String TAG = GeofenceRequester.class.getName();

    public GeofenceRequester(Context context) {
        super(context, Constants.REQUEST_TYPE.ADD);
    }

    public GeofenceRequester add(HashMap<String, LatLng> entries) {
        for (Map.Entry<String, LatLng> entry : entries.entrySet()) {
            geoList.add(new Geofence.Builder()
                    .setRequestId(entry.getKey())
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            za.co.zynafin.teamtracker.Constants.GEOFENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(za.co.zynafin.teamtracker.Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
        }
        return this;
    }

    @Override
    protected void doPerformAction() {
        Log.d(TAG, "Adding geofences....");
        LocationServices.GeofencingApi.addGeofences(mLocationClient, geoList, createGeofencingPendingIntent()).setResultCallback(this);
        geoList.clear();
    }

}
