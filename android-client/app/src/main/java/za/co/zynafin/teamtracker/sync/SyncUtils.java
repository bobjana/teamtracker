package za.co.zynafin.teamtracker.sync;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import za.co.zynafin.teamtracker.account.AccountUtils;
import za.co.zynafin.teamtracker.account.Constants;

public class SyncUtils {

    public static void triggerSync(Context context){
        Account defaultAccount = AccountUtils.obtainDefaultAccount(context);
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(defaultAccount, Constants.CONTENT_AUTHORITY, b);
    }
}
