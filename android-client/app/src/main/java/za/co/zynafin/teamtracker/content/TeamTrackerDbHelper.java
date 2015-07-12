/**********************************************************************************************************************************************************************
****** AUTO GENERATED FILE BY ANDROID SQLITE HELPER SCRIPT BY FEDERICO PAOLINELLI. ANY CHANGE WILL BE WIPED OUT IF THE SCRIPT IS PROCESSED AGAIN. *******
**********************************************************************************************************************************************************************/
package za.co.zynafin.teamtracker.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import java.util.Date;

public class TeamTrackerDbHelper {
    private static final String TAG = "TeamTracker";

    private static final String DATABASE_NAME = "TeamTracker.db";
    private static final int DATABASE_VERSION = 1;


    // Variable to hold the database instance
    protected SQLiteDatabase mDb;
    // Context of the application using the database.
    private final Context mContext;
    // Database open/upgrade helper
    private DbHelper mDbHelper;
    
    public TeamTrackerDbHelper(Context context) {
        mContext = context;
        mDbHelper = new DbHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public TeamTrackerDbHelper open() throws SQLException { 
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
                                                     
    public void close() {
        mDb.close();
    }

    public static final String ROW_ID = "_id";

    
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
    
    
    // -------------- TRACER DEFINITIONS ------------
    public static final String TRACER_TABLE = "Tracer";
    
    public static final String TRACER_CUSTOMERID_COLUMN = "customerId";
    public static final int TRACER_CUSTOMERID_COLUMN_POSITION = 1;
    
    public static final String TRACER_TYPE_COLUMN = "type";
    public static final int TRACER_TYPE_COLUMN_POSITION = 2;
    
    public static final String TRACER_DATE_COLUMN = "date";
    public static final int TRACER_DATE_COLUMN_POSITION = 3;
    
    


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
    
    // Tracer CREATION 
    private static final String DATABASE_TRACER_CREATE = "create table " + TRACER_TABLE + " (" +
                                "_id integer primary key autoincrement, " +
                                TRACER_CUSTOMERID_COLUMN + " integer, " +
                                TRACER_TYPE_COLUMN + " text, " +
                                TRACER_DATE_COLUMN + " integer" +
                                ")";
    

    
    // ----------------Customer HELPERS -------------------- 
    public long addCustomer (long customerId, String name, String geoLocation, String physicalAddress, int coverage) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_CUSTOMERID_COLUMN, customerId);
        contentValues.put(CUSTOMER_NAME_COLUMN, name);
        contentValues.put(CUSTOMER_GEOLOCATION_COLUMN, geoLocation);
        contentValues.put(CUSTOMER_PHYSICALADDRESS_COLUMN, physicalAddress);
        contentValues.put(CUSTOMER_COVERAGE_COLUMN, coverage);
        return mDb.insert(CUSTOMER_TABLE, null, contentValues);
    }

    public long updateCustomer (long rowIndex,long customerId, String name, String geoLocation, String physicalAddress, int coverage) {
        String where = ROW_ID + " = " + rowIndex;
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_CUSTOMERID_COLUMN, customerId);
        contentValues.put(CUSTOMER_NAME_COLUMN, name);
        contentValues.put(CUSTOMER_GEOLOCATION_COLUMN, geoLocation);
        contentValues.put(CUSTOMER_PHYSICALADDRESS_COLUMN, physicalAddress);
        contentValues.put(CUSTOMER_COVERAGE_COLUMN, coverage);
        return mDb.update(CUSTOMER_TABLE, contentValues, where, null);
    }

    public boolean removeCustomer(long rowIndex){
        return mDb.delete(CUSTOMER_TABLE, ROW_ID + " = " + rowIndex, null) > 0;
    }

    public boolean removeAllCustomer(){
        return mDb.delete(CUSTOMER_TABLE, null, null) > 0;
    }

    public Cursor getAllCustomer(){
    	return mDb.query(CUSTOMER_TABLE, new String[] {
                         ROW_ID,
                         CUSTOMER_CUSTOMERID_COLUMN,
                         CUSTOMER_NAME_COLUMN,
                         CUSTOMER_GEOLOCATION_COLUMN,
                         CUSTOMER_PHYSICALADDRESS_COLUMN,
                         CUSTOMER_COVERAGE_COLUMN
                         }, null, null, null, null, null);
    }

    public Cursor getCustomer(long rowIndex) {
        Cursor res = mDb.query(CUSTOMER_TABLE, new String[] {
                                ROW_ID,
                                CUSTOMER_CUSTOMERID_COLUMN,
                                CUSTOMER_NAME_COLUMN,
                                CUSTOMER_GEOLOCATION_COLUMN,
                                CUSTOMER_PHYSICALADDRESS_COLUMN,
                                CUSTOMER_COVERAGE_COLUMN
                                }, ROW_ID + " = " + rowIndex, null, null, null, null);

        if(res != null){
            res.moveToFirst();
        }
        return res;
    }
    
    // ----------------Tracer HELPERS -------------------- 
    public long addTracer (long customerId, String type, Date date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRACER_CUSTOMERID_COLUMN, customerId);
        contentValues.put(TRACER_TYPE_COLUMN, type);
        contentValues.put(TRACER_DATE_COLUMN, date.getTime());
        return mDb.insert(TRACER_TABLE, null, contentValues);
    }

    public long updateTracer (long rowIndex,long customerId, String type, Date date) {
        String where = ROW_ID + " = " + rowIndex;
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRACER_CUSTOMERID_COLUMN, customerId);
        contentValues.put(TRACER_TYPE_COLUMN, type);
        contentValues.put(TRACER_DATE_COLUMN, date.getTime());
        return mDb.update(TRACER_TABLE, contentValues, where, null);
    }

    public boolean removeTracer(long rowIndex){
        return mDb.delete(TRACER_TABLE, ROW_ID + " = " + rowIndex, null) > 0;
    }

    public boolean removeAllTracer(){
        return mDb.delete(TRACER_TABLE, null, null) > 0;
    }

    public Cursor getAllTracer(){
    	return mDb.query(TRACER_TABLE, new String[] {
                         ROW_ID,
                         TRACER_CUSTOMERID_COLUMN,
                         TRACER_TYPE_COLUMN,
                         TRACER_DATE_COLUMN
                         }, null, null, null, null, null);
    }

    public Cursor getTracer(long rowIndex) {
        Cursor res = mDb.query(TRACER_TABLE, new String[] {
                                ROW_ID,
                                TRACER_CUSTOMERID_COLUMN,
                                TRACER_TYPE_COLUMN,
                                TRACER_DATE_COLUMN
                                }, ROW_ID + " = " + rowIndex, null, null, null, null);

        if(res != null){
            res.moveToFirst();
        }
        return res;
    }
    

    private static class DbHelper extends SQLiteOpenHelper {
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

