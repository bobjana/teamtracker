package za.co.zynafin.teamtracker;

import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import za.co.zynafin.teamtracker.content.TeamTrackerDbHelper;
import za.co.zynafin.teamtracker.content.TeamTrackerProvider;
import za.co.zynafin.teamtracker.sync.SyncUtils;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    protected static final String TAG = MainActivity.class.getName();

    private PendingIntent mGeofencePendingIntent;
    private SharedPreferences mSharedPreferences;


    private SimpleCursorAdapter adapter;
    private ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        listView = (ListView) findViewById(R.id.customer_list_view);

        adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.customer_list_view,
                null,
                new String[] { TeamTrackerDbHelper.CUSTOMER_NAME_COLUMN, TeamTrackerDbHelper.CUSTOMER_GEOLOCATION_COLUMN},
                new int[] { R.id.customer_name , R.id.customer_geoLocation }, 0);

        listView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(0, null, this);

        SyncUtils.triggerSync(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sync:
                SyncUtils.triggerSync(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = TeamTrackerProvider.CUSTOMER_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
