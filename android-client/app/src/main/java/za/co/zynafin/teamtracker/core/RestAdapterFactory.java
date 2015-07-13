package za.co.zynafin.teamtracker.core;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import za.co.zynafin.teamtracker.Config;
import za.co.zynafin.teamtracker.account.AccountUtils;

public class RestAdapterFactory {

    private static final String TAG = RestAdapterFactory.class.getName();

    public static RestAdapter create(Context context, Account account) {
        //todo: once oauth2 is introduced, retrieve auth token from google account
//        AccountManager mgr = AccountManager.get(context);
//            final String authToken = mgr.blockingGetAuthToken(account, za.co.zynafin.teamtracker.account.Constants.CONTENT_AUTHORITY, true);
        Log.d(TAG, "Creating rest adapter for: " + Config.SERVER_URL);
        try {
            final String authToken = AccountUtils.getAuthToken();
            Log.d(TAG, "Auth token retrieved - " + authToken);
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestInterceptor.RequestFacade request) {
                    request.addHeader("x-auth-token", authToken);
                }
            };

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new IsoDateTypeAdapter())
                    .create();

            return new RestAdapter.Builder()
                    .setEndpoint(Config.SERVER_URL)
                    .setRequestInterceptor(requestInterceptor)
                    .setConverter(new GsonConverter(gson))
                    .build();
        } catch (Exception e) {
            Log.w(TAG, "Unable to create rest adapter", e);
            return new RestAdapter.Builder().build();
        }

    }

}
