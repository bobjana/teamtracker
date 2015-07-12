/**********************************************************************************************************************************************************************
****** AUTO GENERATED FILE BY ANDROID SQLITE HELPER SCRIPT BY FEDERICO PAOLINELLI. ANY CHANGE WILL BE WIPED OUT IF THE SCRIPT IS PROCESSED AGAIN. *******
**********************************************************************************************************************************************************************/
package za.co.zynafin.teamtracker.content;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.Date;

public class TeamTrackerProvider extends ContentProvider {
    private static final String TAG = "TeamTrackerProvider";

    protected static final String DATABASE_NAME = "TeamTracker.db";
    protected static final int DATABASE_VERSION = 1;

    // --------------- URIS --------------------
    public static final Uri CUSTOMER_URI = Uri.parse("content://za.co.zynafin.teamtracker/Customer");
    public static final Uri TRACER_URI = Uri.parse("content://za.co.zynafin.teamtracker/Trace");
    
    // -------------- CUSTOMER DEFINITIONS ------------
    public static final String CUSTOMER_TABLE = "Customer";

    public static final String CUSTOMER_CUSTOMERID_COLUMN = "customerId";
    public static final int CUSTOMER_CUSTOMERID_COLUMN_POSITION = 1;
    public static final String CUSTOMER_NAME_COLUMN = "name";
    public static final int CUSTOMER_NAME_COLUMN_POSITION = 2;
    public static final String CUSTOMER_GEOLOCATION_COLUMN = "geoLocation";
    public static final int CUSTOMER_GEOLOCATION_COLUMN_POSITION = 3;
    public static final String CUSTOMER_PHYSICALADDRESS_COLUMN = "physicalAddress";
    public static final int CUSTOMER_PHYSICALADDRESS_COLUMN_POSITION = 4;
    public static final String CUSTOMER_COVERAGE_COLUMN = "coverage";
    public static final int CUSTOMER_COVERAGE_COLUMN_POSITION = 5;
    public static final int ALL_CUSTOMER = 0;
    public static final int SINGLE_CUSTOMER = 1;

    
    // -------------- TRACER DEFINITIONS ------------
    public static final String TRACER_TABLE = "Trace";

    public static final String TRACER_CUSTOMERID_COLUMN = "customerId";
    public static final int TRACER_CUSTOMERID_COLUMN_POSITION = 1;
    public static final String TRACER_TYPE_COLUMN = "type";
    public static final int TRACER_TYPE_COLUMN_POSITION = 2;
    public static final String TRACER_DATE_COLUMN = "date";
    public static final int TRACER_DATE_COLUMN_POSITION = 3;
    public static final int ALL_TRACER = 2;
    public static final int SINGLE_TRACER = 3;

    

    public static final String ROW_ID = "_id";

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    
        uriMatcher.addURI("za.co.zynafin.teamtracker", "Customer", ALL_CUSTOMER);
        uriMatcher.addURI("za.co.zynafin.teamtracker", "Customer/#", SINGLE_CUSTOMER);
    
        uriMatcher.addURI("za.co.zynafin.teamtracker", "Trace", ALL_TRACER);
        uriMatcher.addURI("za.co.zynafin.teamtracker", "Trace/#", SINGLE_TRACER);
    }
 

    // -------- TABLES CREATION ----------
    
    // Customer CREATION 
    private static final String DATABASE_CUSTOMER_CREATE = "create table " + CUSTOMER_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                CUSTOMER_CUSTOMERID_COLUMN + " integer, " +
                                CUSTOMER_NAME_COLUMN + " text, " +
                                CUSTOMER_GEOLOCATION_COLUMN + " text, " +
                                CUSTOMER_PHYSICALADDRESS_COLUMN + " text, " +
                                CUSTOMER_COVERAGE_COLUMN + " integer" +
                                ")";
    
    // Trace CREATION
    private static final String DATABASE_TRACER_CREATE = "create table " + TRACER_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                TRACER_CUSTOMERID_COLUMN + " integer, " +
                                TRACER_TYPE_COLUMN + " text, " +
                                TRACER_DATE_COLUMN + " integer" +
                                ")";
    

    protected DbHelper myOpenHelper;

    @Override
    public boolean onCreate() {
        myOpenHelper = new DbHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        return true;
    }

    /**
    * Returns the right table name for the given uri
    * @param uri
    * @return
    */
    private String getTableNameFromUri(Uri uri){
        switch (uriMatcher.match(uri)) {
            case ALL_CUSTOMER:
            case SINGLE_CUSTOMER:
                return CUSTOMER_TABLE;
            case ALL_TRACER:
            case SINGLE_TRACER:
                return TRACER_TABLE;
            default: break;
        }
        return null;
    }
    
    /**
    * Returns the parent uri for the given uri
    * @param uri
    * @return
    */
    private Uri getContentUriFromUri(Uri uri){
        switch (uriMatcher.match(uri)) {
            case ALL_CUSTOMER:
            case SINGLE_CUSTOMER:
                return CUSTOMER_URI;
            case ALL_TRACER:
            case SINGLE_TRACER:
                return TRACER_URI;
            default: break;
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
        String[] selectionArgs, String sortOrder) {

        // Open thedatabase.
        SQLiteDatabase db;
        try {
            db = myOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = myOpenHelper.getReadableDatabase();
        }

        // Replace these with valid SQL statements if necessary.
        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // If this is a row query, limit the result set to the passed in row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_CUSTOMER:
            case SINGLE_TRACER:
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(ROW_ID + "=" + rowID);
            default: break;
        }

        // Specify the table on which to perform the query. This can
        // be a specific table or a join as required.
        queryBuilder.setTables(getTableNameFromUri(uri));

        // Execute the query.
        Cursor cursor = queryBuilder.query(db, projection, selection,
                    selectionArgs, groupBy, having, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the result Cursor.
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // Return a string that identifies the MIME type
        // for a Content Provider URI
        switch (uriMatcher.match(uri)) {
            case ALL_CUSTOMER:
                return "vnd.android.cursor.dir/vnd.za.co.zynafin.teamtracker.content.Customer";
            case SINGLE_CUSTOMER:
                return "vnd.android.cursor.dir/vnd.za.co.zynafin.teamtracker.content.Customer";
            case ALL_TRACER:
                return "vnd.android.cursor.dir/vnd.za.co.zynafin.teamtracker.content.Trace";
            case SINGLE_TRACER:
                return "vnd.android.cursor.dir/vnd.za.co.zynafin.teamtracker.content.Trace";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_CUSTOMER:
            case SINGLE_TRACER:
                String rowID = uri.getPathSegments().get(1);
                selection = ROW_ID + "=" + rowID + (!TextUtils.isEmpty(selection) ?  " AND (" + selection + ')' : "");
            default: break;
        }

        if (selection == null)
            selection = "1";

        int deleteCount = db.delete(getTableNameFromUri(uri),
                selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String nullColumnHack = null;

        long id = db.insert(getTableNameFromUri(uri), nullColumnHack, values);
        if (id > -1) {
            Uri insertedId = ContentUris.withAppendedId(getContentUriFromUri(uri), id);
                                getContext().getContentResolver().notifyChange(insertedId, null);
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        } else {
            return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // Open a read / write database to support the transaction.
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_CUSTOMER:
            case SINGLE_TRACER:
                String rowID = uri.getPathSegments().get(1);
                selection = ROW_ID + "=" + rowID + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
            default: break;
        }

        // Perform the update.
        int updateCount = db.update(getTableNameFromUri(uri), values, selection, selectionArgs);
        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

    protected static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // Called when no database exists in disk and the helper class needs
        // to create a new one. 
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CUSTOMER_CREATE);
            db.execSQL(DATABASE_TRACER_CREATE);
        }

        // Called when there is a database version mismatch meaning that the version
        // of the database on disk needs to be upgraded to the current version.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Log the version upgrade.
            Log.w(TAG, "Upgrading from version " + 
                        oldVersion + " to " +
                        newVersion + ", which will destroy all old data");
            
            // Upgrade the existing database to conform to the new version. Multiple 
            // previous versions can be handled by comparing _oldVersion and _newVersion
            // values.

            // The simplest case is to drop the old table and create a new one.
            db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TRACER_TABLE + ";");
            // Create a new one.
            onCreate(db);
        }
    }
}

