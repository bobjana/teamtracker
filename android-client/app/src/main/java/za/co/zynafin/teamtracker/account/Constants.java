package za.co.zynafin.teamtracker.account;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class Constants {

    public static final int SYNC_FREQUENCY = 60 * 60;
    public static final String CONTENT_AUTHORITY = "za.co.zynafin.teamtracker";

    public static final String TRANSISTION_TYPE_ENTER = "ENTER";
    public static final String TRANSISTION_TYPE_EXIT = "EXIT";
    public static final String TRANSISTION_TYPE_DWELL = "DWELL";

    public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String GEO_FENCE = "geofences";
    public static final String TABLE_GEO_FENCE = "geofences";
    public static final String GEO_FENCE_EVENT = "geofenceevents";
    public static final String TABLE_GEO_FENCE_EVENT = "geofenceevents";

    public static class GeoFenceColumns implements BaseColumns {
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.geofence.entries";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.geofence.entry";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(GEO_FENCE).build();
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LNG = "lng";
        public static final String COLUMN_NAME_RADIUS = "radius";
    }

    public static class GeoFenceEventColumns implements BaseColumns {
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.geofenceevent.entries";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.geofenceevent.entry";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(GEO_FENCE_EVENT).build();
        public static final String COLUMN_GEO_FENCE_ID = "geo_fence_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TYPE = "type";
    }

}
