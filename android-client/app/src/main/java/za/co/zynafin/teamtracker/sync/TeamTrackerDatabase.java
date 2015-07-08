package za.co.zynafin.teamtracker.sync;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import za.co.zynafin.teamtracker.account.Constants;

public class TeamTrackerDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "geofence.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_LONG = " LONG";
    private static final String TYPE_DOUBLE = " DOUBLE";
    private static final String TYPE_FLOAT = " FLOAT";
    private static final String COMMA_SEP = ",";

    private static final String[] SQL_CREATE = {
            "CREATE TABLE " + Constants.GEO_FENCE + " (" +
                    Constants.GeoFenceColumns._ID + " LONG PRIMARY KEY," +
                    Constants.GeoFenceColumns.COLUMN_NAME_LAT + TYPE_DOUBLE + COMMA_SEP +
                    Constants.GeoFenceColumns.COLUMN_NAME_LNG + TYPE_DOUBLE + COMMA_SEP +
                    Constants.GeoFenceColumns.COLUMN_NAME_RADIUS + TYPE_FLOAT + ")",
            "CREATE TABLE " + Constants.GEO_FENCE_EVENT + " (" +
                    Constants.GeoFenceEventColumns._ID + " LONG PRIMARY KEY," +
                    Constants.GeoFenceEventColumns.COLUMN_GEO_FENCE_ID + TYPE_INTEGER + COMMA_SEP +
                    Constants.GeoFenceEventColumns.COLUMN_DATE + TYPE_LONG + COMMA_SEP +
                    Constants.GeoFenceEventColumns.COLUMN_TYPE + TYPE_TEXT + ")",
    };


    private static final String[] SQL_DELETE = {
            "DROP TABLE IF EXISTS " + Constants.GEO_FENCE,
            "DROP TABLE IF EXISTS " + Constants.GEO_FENCE_EVENT
    };

    public TeamTrackerDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sql : SQL_CREATE) {
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        for (String sql : SQL_DELETE) {
            db.execSQL(sql);
        }
        onCreate(db);
    }
}


