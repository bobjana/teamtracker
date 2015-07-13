/**
 * *******************************************************************************************************************************************************************
 * ***** AUTO GENERATED FILE BY ANDROID SQLITE HELPER SCRIPT BY FEDERICO PAOLINELLI. ANY CHANGE WILL BE WIPED OUT IF THE SCRIPT IS PROCESSED AGAIN. *******
 * ********************************************************************************************************************************************************************
 */
package za.co.zynafin.teamtracker.content;


import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import za.co.zynafin.teamtracker.customer.Customer;
import za.co.zynafin.teamtracker.trace.Trace;

import static za.co.zynafin.teamtracker.content.TeamTrackerDbHelper.*;

public class TeamTrackerProviderClient {


    private static final String TAG = TeamTrackerProviderClient.class.getName();

    // ------------- CUSTOMER_HELPERS ------------
    public static Uri addCustomer(long customerId,
                                  String name,
                                  String geoLocation,
                                  String physicalAddress,
                                  int coverage,
                                  Context c) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TeamTrackerProvider.CUSTOMER_CUSTOMERID_COLUMN, customerId);
        contentValues.put(TeamTrackerProvider.CUSTOMER_NAME_COLUMN, name);
        contentValues.put(TeamTrackerProvider.CUSTOMER_GEOLOCATION_COLUMN, geoLocation);
        contentValues.put(TeamTrackerProvider.CUSTOMER_PHYSICALADDRESS_COLUMN, physicalAddress);
        contentValues.put(TeamTrackerProvider.CUSTOMER_COVERAGE_COLUMN, coverage);
        ContentResolver cr = c.getContentResolver();
        return cr.insert(TeamTrackerProvider.CUSTOMER_URI, contentValues);
    }

    public static Uri addCustomer(ContentProviderClient contentProviderClient, Customer customer) throws RemoteException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TeamTrackerProvider.CUSTOMER_CUSTOMERID_COLUMN, customer.getId());
        contentValues.put(TeamTrackerProvider.CUSTOMER_NAME_COLUMN, customer.getName());
        contentValues.put(TeamTrackerProvider.CUSTOMER_GEOLOCATION_COLUMN, customer.getGeoLocation());
        contentValues.put(TeamTrackerProvider.CUSTOMER_PHYSICALADDRESS_COLUMN, customer.getPhysicalAddress());
        contentValues.put(TeamTrackerProvider.CUSTOMER_COVERAGE_COLUMN, customer.getCoverage());
        return contentProviderClient.insert(TeamTrackerProvider.CUSTOMER_URI, contentValues);
    }


    public static int removeCustomer(long rowIndex, Context c) {
        ContentResolver cr = c.getContentResolver();
        Uri rowAddress = ContentUris.withAppendedId(TeamTrackerProvider.CUSTOMER_URI, rowIndex);
        return cr.delete(rowAddress, null, null);
    }

    public static int removeAllCustomer(ContentProviderClient c) throws RemoteException {
        return c.delete(TeamTrackerProvider.CUSTOMER_URI, null, null);
    }

    public static Cursor getAllCustomer(ContentProvider c) {
        String[] resultColumns = new String[]{
                TeamTrackerProvider.ROW_ID,
                TeamTrackerProvider.CUSTOMER_CUSTOMERID_COLUMN,
                TeamTrackerProvider.CUSTOMER_NAME_COLUMN,
                TeamTrackerProvider.CUSTOMER_GEOLOCATION_COLUMN,
                TeamTrackerProvider.CUSTOMER_PHYSICALADDRESS_COLUMN,
                TeamTrackerProvider.CUSTOMER_COVERAGE_COLUMN
        };

        Cursor resultCursor = c.query(TeamTrackerProvider.CUSTOMER_URI, resultColumns, null, null, null);
        return resultCursor;
    }

    public static Cursor getCustomer(long rowId, Context c) {
        ContentResolver cr = c.getContentResolver();
        String[] resultColumns = new String[]{
                TeamTrackerProvider.ROW_ID,
                TeamTrackerProvider.CUSTOMER_CUSTOMERID_COLUMN,
                TeamTrackerProvider.CUSTOMER_NAME_COLUMN,
                TeamTrackerProvider.CUSTOMER_GEOLOCATION_COLUMN,
                TeamTrackerProvider.CUSTOMER_PHYSICALADDRESS_COLUMN,
                TeamTrackerProvider.CUSTOMER_COVERAGE_COLUMN
        };

        Uri rowAddress = ContentUris.withAppendedId(TeamTrackerProvider.CUSTOMER_URI, rowId);
        String where = null;
        String whereArgs[] = null;
        String order = null;

        Cursor resultCursor = cr.query(rowAddress, resultColumns, where, whereArgs, order);
        return resultCursor;
    }

    public static int updateCustomer(int rowId,
                                     long customerId,
                                     String name,
                                     String geoLocation,
                                     String physicalAddress,
                                     int coverage,
                                     Context c) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TeamTrackerProvider.CUSTOMER_CUSTOMERID_COLUMN, customerId);
        contentValues.put(TeamTrackerProvider.CUSTOMER_NAME_COLUMN, name);
        contentValues.put(TeamTrackerProvider.CUSTOMER_GEOLOCATION_COLUMN, geoLocation);
        contentValues.put(TeamTrackerProvider.CUSTOMER_PHYSICALADDRESS_COLUMN, physicalAddress);
        contentValues.put(TeamTrackerProvider.CUSTOMER_COVERAGE_COLUMN, coverage);
        Uri rowAddress = ContentUris.withAppendedId(TeamTrackerProvider.CUSTOMER_URI, rowId);

        ContentResolver cr = c.getContentResolver();
        int updatedRowCount = cr.update(rowAddress, contentValues, null, null);
        return updatedRowCount;
    }

    // ------------- TRACER_HELPERS ------------
    public static Uri addTracer(long customerId,
                                String type,
                                Date date,
                                Context c) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TeamTrackerProvider.TRACER_CUSTOMERID_COLUMN, customerId);
        contentValues.put(TeamTrackerProvider.TRACER_TYPE_COLUMN, type);
        contentValues.put(TeamTrackerProvider.TRACER_DATE_COLUMN, date.getTime());
        ContentResolver cr = c.getContentResolver();

        Uri insert = cr.insert(TeamTrackerProvider.TRACER_URI, contentValues);
        Log.d(TAG, "Added trace to DB: " + insert);
        return insert;
    }

    public static int removeTracer(long rowIndex, Context c) {
        ContentResolver cr = c.getContentResolver();
        Uri rowAddress = ContentUris.withAppendedId(TeamTrackerProvider.TRACER_URI, rowIndex);
        return cr.delete(rowAddress, null, null);
    }

    public static int removeAllTracer(ContentProviderClient c) throws RemoteException {
        return c.delete(TeamTrackerProvider.TRACER_URI, null, null);
    }

    public static List<Trace> listAllTracers(ContentProviderClient c) throws RemoteException {
        String[] resultColumns = new String[]{
                TeamTrackerProvider.ROW_ID,
                TeamTrackerProvider.TRACER_CUSTOMERID_COLUMN,
                TeamTrackerProvider.TRACER_TYPE_COLUMN,
                TeamTrackerProvider.TRACER_DATE_COLUMN
        };

        Cursor resultCursor = c.query(TeamTrackerProvider.TRACER_URI, resultColumns, null, null, null);
        List<Trace> result = new ArrayList<>();
        while (resultCursor.moveToNext()){
            Trace trace = new Trace(resultCursor.getLong(TRACER_CUSTOMERID_COLUMN_POSITION),
                    resultCursor.getString(TRACER_TYPE_COLUMN_POSITION),
                    new Date(resultCursor.getInt(TRACER_DATE_COLUMN_POSITION)));
            result.add(trace);
        }
        return result;
    }

    public static Cursor getTracer(long rowId, Context c) {
        ContentResolver cr = c.getContentResolver();
        String[] resultColumns = new String[]{
                TeamTrackerProvider.ROW_ID,
                TeamTrackerProvider.TRACER_CUSTOMERID_COLUMN,
                TeamTrackerProvider.TRACER_TYPE_COLUMN,
                TeamTrackerProvider.TRACER_DATE_COLUMN
        };

        Uri rowAddress = ContentUris.withAppendedId(TeamTrackerProvider.TRACER_URI, rowId);
        String where = null;
        String whereArgs[] = null;
        String order = null;

        Cursor resultCursor = cr.query(rowAddress, resultColumns, where, whereArgs, order);
        return resultCursor;
    }

    public static int updateTracer(int rowId,
                                   long customerId,
                                   String type,
                                   Date date,
                                   Context c) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TeamTrackerProvider.TRACER_CUSTOMERID_COLUMN, customerId);
        contentValues.put(TeamTrackerProvider.TRACER_TYPE_COLUMN, type);
        contentValues.put(TeamTrackerProvider.TRACER_DATE_COLUMN, date.getTime());
        Uri rowAddress = ContentUris.withAppendedId(TeamTrackerProvider.TRACER_URI, rowId);

        ContentResolver cr = c.getContentResolver();
        int updatedRowCount = cr.update(rowAddress, contentValues, null, null);
        return updatedRowCount;
    }

}
