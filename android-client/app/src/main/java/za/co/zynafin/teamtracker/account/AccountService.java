package za.co.zynafin.teamtracker.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import retrofit.RestAdapter;
import za.co.zynafin.teamtracker.*;


//import za.co.zyanfin.teamtracker.Config;

//todo: check feasibility and finish implementation
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

		private Context context;

		public AccountAuthenticator(Context context) {
			super(context);
			this.context = context;
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
		                           Account account, String authTokenType, Bundle bundle)
			throws NetworkErrorException {

			final AccountManager am = AccountManager.get(context);

			String authToken = am.peekAuthToken(account, authTokenType);

			// Lets give another try to authenticate the user
			if (TextUtils.isEmpty(authToken)) {
//				final String email = am.getUserData(account,)
//				final String password = am.getPassword(account);
				//todo: retrieve username & password from account
				final String username = "admin";
				final String password = "admin";
				if (password != null && username != null) {
					RestAdapter restAdapter = new RestAdapter.Builder()
							.setEndpoint(Config.SERVER_URL)
							.build();
					LoginService service = restAdapter.create(LoginService.class);
					AuthToken auth = service.authenticate("admin", "admin");
					authToken = auth.getToken();
//					authToken = sServerAuthenticate.userSignIn(/*account.name*/, password, authTokenType);
				}
			}

			// If we get an authToken - we return it
			if (!TextUtils.isEmpty(authToken)) {
				final Bundle result = new Bundle();
				result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
				result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
				result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
				return result;
			}

//			// If we get here, then we couldn't access the user's password - so we
//			// need to re-prompt them for their credentials. We do that by creating
//			// an intent to display our AuthenticatorActivity.
//			final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
//			intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
//			intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, account.type);
//			intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
//			final Bundle bundle = new Bundle();
//			bundle.putParcelable(AccountManager.KEY_INTENT, intent);
//			return bundle;


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

