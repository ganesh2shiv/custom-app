package com.custom.app.oauth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.custom.app.ui.login.LoginActivity;

import timber.log.Timber;

import static android.accounts.AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE;
import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.accounts.AccountManager.KEY_INTENT;
import static com.network.app.oauth.AccountConstants.KEY_TOKEN_TYPE;
import static com.network.app.oauth.AccountConstants.TYPE_FULL_ACCESS;
import static com.network.app.oauth.AccountConstants.TYPE_FULL_ACCESS_LABEL;
import static com.network.app.oauth.AccountConstants.TYPE_READ_ONLY;
import static com.network.app.oauth.AccountConstants.TYPE_READ_ONLY_LABEL;

class AccountAuthenticator extends AbstractAccountAuthenticator {

    private final Context context;

    AccountAuthenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
                             String authTokenType, String[] requiredFeatures, Bundle options) {
        Timber.d("Adding account...");

        final Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(KEY_TOKEN_TYPE, authTokenType);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account,
                               String authTokenType, Bundle options) {
        Timber.d("Fetching auth token...");

        if (!authTokenType.equals(TYPE_READ_ONLY) && !authTokenType.equals(TYPE_FULL_ACCESS)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "Invalid token type!");
            return result;
        }

        final AccountManager accountManager = AccountManager.get(context);

        String authToken = accountManager.peekAuthToken(account, authTokenType);

        if (!TextUtils.isEmpty(authToken)) {
            Timber.d("Found auth token: %s", authToken);

            final Bundle result = new Bundle();
            result.putString(KEY_ACCOUNT_NAME, account.name);
            result.putString(KEY_ACCOUNT_TYPE, account.type);
            return result;
        }

        final Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(KEY_TOKEN_TYPE, authTokenType);
        intent.putExtra(KEY_ACCOUNT_NAME, account.name);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_INTENT, intent);

        return bundle;
    }


    @Override
    public String getAuthTokenLabel(String authTokenType) {
        switch (authTokenType) {
            case TYPE_FULL_ACCESS:
                return TYPE_FULL_ACCESS_LABEL;
            case TYPE_READ_ONLY:
                return TYPE_READ_ONLY_LABEL;
            default:
                return authTokenType + " (Label)";
        }
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) {
        return null;
    }
}