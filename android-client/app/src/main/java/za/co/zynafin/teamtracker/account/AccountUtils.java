/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package za.co.zynafin.teamtracker.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import za.co.zynafin.teamtracker.R;
import za.co.zynafin.teamtracker.core.BusinessException;

public class AccountUtils {

    private static Account defaultAccount;
    private static Context context;

    public static Account obtainDefaultAccount(Context context) {
        if (defaultAccount == null) {
            AccountManager accountMgr = AccountManager.get(context);
            Account[] accounts = accountMgr.getAccounts();
            if (accounts.length == 0) {
                throw new BusinessException(R.string.default_google_account_does_not_exist,"No accounts present");
            }
            for (Account account : accounts) {
                if (account.type.equals(Constants.GOOGLE_ACCOUNT_TYPE)) {
                    defaultAccount = account;
                    ContentResolver.setIsSyncable(account, Constants.CONTENT_AUTHORITY, 1);
                    ContentResolver.setSyncAutomatically(account, Constants.CONTENT_AUTHORITY, true);
                    ContentResolver.addPeriodicSync(account, Constants.CONTENT_AUTHORITY, new Bundle(), Constants.SYNC_FREQUENCY);
                }
            }
            if (defaultAccount == null){
                throw new BusinessException(R.string.default_google_account_does_not_exist, "Google account does not exist"/*R.string.default_google_account_does_not_exist*/);
            }
        }
        return defaultAccount;
    }

    public static String getAuthToken(){
        //todo: obtain auth token
//        accountMgr.getAuthToken(account, "mail", null, false, new GetAuthTokenCallback(), null);
        throw new RuntimeException("Not yet implemented!!!");
    }


//    private class GetAuthTokenCallback implements AccountManagerCallback<Bundle> {
//
//        private static final String TAG = GetAuthTokenCallback.class.getName();
//
//        public void run(AccountManagerFuture<Bundle> result) {
//            Bundle bundle;
//            try {
//                bundle = result.getResult();
//                Intent intent = (Intent) bundle.get(AccountManager.KEY_INTENT);
//                if (intent != null) {
//                    // User input required
//                    startActivity(intent);
//                } else {
//                    _this.setMessage("Token: " + bundle.getString(AccountManager.KEY_AUTHTOKEN));
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "Unable to get auth token", e);
//            }
//        }
//    }


}
