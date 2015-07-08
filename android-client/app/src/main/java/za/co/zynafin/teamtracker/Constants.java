package za.co.zynafin.teamtracker;

import com.google.android.gms.location.Geofence;

public interface Constants {

    String TAG = "TeamTracker";
    int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    long CONNECTION_TIME_OUT_MS = 100;
    long GEOFENCE_EXPIRATION_TIME = Geofence.NEVER_EXPIRE;

}
