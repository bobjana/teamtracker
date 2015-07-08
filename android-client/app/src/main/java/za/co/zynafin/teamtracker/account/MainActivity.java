package za.co.zynafin.teamtracker.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import za.co.zynafin.teamtracker.R;
import za.co.zynafin.teamtracker.events.GeoFenceEvent;
import za.co.zynafin.teamtracker.events.GeofenceRequester;
import za.co.zynafin.teamtracker.events.GeofenceUtils;
import za.co.zynafin.teamtracker.sync.SyncService;
import za.co.zynafin.teamtracker.sync.SyncUtils;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends Activity {

    private static final String TAG = "MAIN";

    private MainActivity _this;

    private GeofenceRequester mGeofenceRequester;
    private GeofenceSampleReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        _this = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGeofenceRequester = new GeofenceRequester(_this);
        mBroadcastReceiver = new GeofenceSampleReceiver();

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCES_ADDED);
        mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCES_REMOVED);
        mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCE_ERROR);
        mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCE_TRANSITION);
        mIntentFilter.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SyncUtils.triggerSync(_this);
        if (servicesConnected()) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, mIntentFilter);
        }

//        pickUserAccount();
    }

    private void pickUserAccount() {
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sync:
                SyncUtils.triggerSync(_this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setMessage(String msg) {
        TextView tv = (TextView) this.findViewById(R.id.eventTxtView);
        String existingmsg = tv.getText().toString();
        tv.setText(msg + "\r\n" + existingmsg);
    }

    private boolean servicesConnected() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == resultCode) {
            Log.d(GeofenceUtils.TAG, getString(R.string.play_services_available));
            return true;
        } else {
            Log.e(GeofenceUtils.TAG, getString(R.string.no_resolution));
            //todo: implement dialog notification
            // Display an error dialog
//            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
//            if (dialog != null) {
//                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
//                errorFragment.setDialog(dialog);
//                errorFragment.show(getSupportFragmentManager(), GeofenceUtils.TAG);
//            }
            return false;
        }
    }

    public class GeofenceSampleReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_ERROR)) {
                handleGeofenceError(context, intent);
            } else if (
                    TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCES_ADDED) ||
                            TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCES_REMOVED)) {
                handleGeofenceStatus(context, intent);
            } else if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_TRANSITION)) {
                handleGeofenceTransition(context, intent);
            } else {
                Log.e(GeofenceUtils.TAG, getString(R.string.invalid_action_detail, action));
                Toast.makeText(context, R.string.invalid_action, Toast.LENGTH_LONG).show();
            }
        }

        private void handleGeofenceStatus(Context context, Intent intent) {
            String msg = String.format("Status: " + intent.getAction());
            setMessage(msg);
        }

        private void handleGeofenceTransition(Context context, Intent intent) {
            GeoFenceEvent event = (GeoFenceEvent) intent.getSerializableExtra(GeofenceUtils.EXTRA_GEOFENCE_EVENT);
            String msg = String.format("GeoFence %s: %s", event.getTransitionType(), event.getGeoFence());
//            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            setMessage(msg);
        }

        private void handleGeofenceError(Context context, Intent intent) {
            String msg = intent.getStringExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS);
            Log.e(GeofenceUtils.TAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }


    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /**
         * Set the dialog to display
         *
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }



}