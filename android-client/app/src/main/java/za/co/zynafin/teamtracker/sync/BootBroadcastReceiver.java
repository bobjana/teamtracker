package za.co.zynafin.teamtracker.sync;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Log.d("BootBroadCastReceiver","Starting team tracker...");
            SyncUtils.triggerSync(context);
        }
        else{
            Log.v("BootBroadCastReceiver","Some other action not off interest: " + intent.getAction());
        }
    }
}