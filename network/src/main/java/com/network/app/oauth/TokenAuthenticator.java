package com.network.app.oauth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.network.app.R;
import com.user.app.data.UserManager;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import timber.log.Timber;

import static com.network.app.oauth.AccountConstants.TYPE_FULL_ACCESS;

public class TokenAuthenticator implements Authenticator {

    private Context context;
    private UserManager userManager;
    private AccountManager accountManager;
    private WeakReference<Activity> activity;

    public TokenAuthenticator(Context context, UserManager userManager, AccountManager accountManager) {
        this.context = context;
        this.userManager = userManager;
        this.accountManager = accountManager;
    }

    public void setCallingActivity(Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    public Request authenticate(Route route, Response response) {
        Timber.d("Authenticating for response: %s", response);
        Timber.d("Challenges: %s", response.challenges());

        String url = response.request().url().toString();

        if (!url.endsWith("/api/auth/login")) {
            if (responseCount(response) >= 2) {
                return null;
            }

            Account[] accounts = accountManager.getAccountsByType(context.getString(R.string.account_type));
            Account account = getUserAccount(accounts);

            if (account != null) {
                String expiredToken = accountManager.peekAuthToken(account, TYPE_FULL_ACCESS);

                if (!TextUtils.isEmpty(expiredToken)) {
                    Timber.d("Invalidating auth token...");
                    accountManager.invalidateAuthToken(context.getString(R.string.account_type), expiredToken);
                }

                try {
                    Timber.d("Fetching new token...");

                    AccountManagerFuture<Bundle> accountManagerFuture =
                            accountManager.getAuthTokenByFeatures(context.getString(R.string.account_type),
                                    TYPE_FULL_ACCESS, null, activity.get(), null, null, null, null);

                    Bundle result = accountManagerFuture.getResult();

                    if (accountManagerFuture.isDone() && !accountManagerFuture.isCancelled()) {
                        Timber.d("Authorisation done...");

                        String renewedToken = accountManager.peekAuthToken(account, TYPE_FULL_ACCESS);

                        if (result != null && !TextUtils.isEmpty(renewedToken)) {
                            Timber.d("Found access token...");

                            return response.request().newBuilder()
                                    .header("Authorization", String.format("Bearer %s", renewedToken))
                                    .build();
                        }
                    }
                } catch (IOException | OperationCanceledException | AuthenticatorException e) {
                    Timber.e(e);
                }
            }
        }

        return null;
    }

    private Account getUserAccount(Account[] accounts) {
        if (accounts != null && accounts.length > 0) {
            for (Account item : accounts) {
                if (item.name.equals(userManager.getUsername())) {
                    return item;
                }
            }
        }
        return null;
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}