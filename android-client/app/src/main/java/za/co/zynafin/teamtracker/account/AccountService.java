package za.co.zynafin.teamtracker.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import za.co.zynafin.teamtracker.Config;

public class AccountService extends Service {

	private static final String TAG = "ACC";

	private AccountAuthenticator mAccountAuthenticator;

	@Override
	public void onCreate() {
		Log.i(TAG, "Service created");
		mAccountAuthenticator = new AccountAuthenticator(this);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "Service destroyed");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mAccountAuthenticator.getIBinder();
	}

	public class AccountAuthenticator extends AbstractAccountAuthenticator {
		public AccountAuthenticator(Context context) {
			super(context);
		}

		@Override
		public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse,
		                             String s) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                 String accountType, String authTokenType, String[] strings, Bundle bundle)
			throws NetworkErrorException {
//            final Bundle result;
//            final Intent intent;
//            intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
////            intent.putExtra(Constants.AUTHTOKEN_TYPE, authTokenType);
//            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
//            result = new Bundle();
//            result.putParcelable(AccountManager.KEY_INTENT, intent);
//            return result;
            return null;
		}

		@Override
		public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
		                                 Account account, Bundle bundle)
			throws NetworkErrorException {

//            retrieveUserInfo(account);
//            User user = userEntity.getBody();
////            AccountManager.K
			return null;
		}

		@Override
		public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse,
		                           Account account, String s, Bundle bundle)
			throws NetworkErrorException {
            throw new UnsupportedOperationException();
		}

		@Override
		public String getAuthTokenLabel(String s) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
		                                Account account, String s, Bundle bundle)
			throws NetworkErrorException {
			throw new UnsupportedOperationException();
		}

		@Override
		public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse,
		                          Account account, String[] strings)
			throws NetworkErrorException {
			throw new UnsupportedOperationException();
		}
	}


}

