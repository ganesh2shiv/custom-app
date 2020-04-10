package com.network.app.oauth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.text.TextUtils;

import com.network.app.R;
import com.network.app.auth.TokenResponse;
import com.user.app.data.UserManager;

import org.threeten.bp.Instant;

import timber.log.Timber;

import static com.network.app.oauth.AccountConstants.KEY_REFRESH_TOKEN;
import static com.network.app.oauth.AccountConstants.KEY_TOKEN_EXPIRE_IN;
import static com.network.app.oauth.AccountConstants.TOKEN_EXPIRE_IN_MILLIS;
import static com.network.app.oauth.AccountConstants.TYPE_FULL_ACCESS;

public class OAuthTokenManager {

    private Context context;
    private UserManager userManager;
    private TokenService tokenService;
    private AccountManager accountManager;

    OAuthTokenManager(Context context, UserManager userManager, AccountManager accountManager) {
        this.context = context;
        this.userManager = userManager;
        this.accountManager = accountManager;
    }

    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public synchronized String getAccessToken() {

        Account[] accounts = accountManager.getAccountsByType(context.getString(R.string.account_type));
        Account account = getUserAccount(accounts);

        if (account != null) {
            String tokenExpireIn = accountManager.getUserData(account, KEY_TOKEN_EXPIRE_IN);
            if (!TextUtils.isEmpty(tokenExpireIn)) {
                try {
                    long expireTimestamp = Long.parseLong(tokenExpireIn);
                    long currentTimestamp = Instant.now().toEpochMilli();
                    if ((currentTimestamp < expireTimestamp) || expireTimestamp == 0) {
                        return accountManager.peekAuthToken(account, TYPE_FULL_ACCESS);
                    } else {
                        return getRefreshToken();
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    Timber.e(e);
                }
            }
        }

        return null;
    }

    private synchronized String getRefreshToken() throws NullPointerException {

        Account[] accounts = accountManager.getAccountsByType(context.getString(R.string.account_type));
        Account account = getUserAccount(accounts);

        if (account != null) {
            String refreshToken = accountManager.getUserData(account, KEY_REFRESH_TOKEN);

            String header = String.format("Bearer %s", refreshToken);

            TokenResponse body = tokenService.refreshToken(header).blockingGet();

            String accessToken = body.getToken();

            accountManager.setAuthToken(account, TYPE_FULL_ACCESS, accessToken);

            String tokenExpireInMillis = String.valueOf(Instant.now().toEpochMilli() + TOKEN_EXPIRE_IN_MILLIS);

            accountManager.setUserData(account, KEY_TOKEN_EXPIRE_IN, tokenExpireInMillis);

            return accessToken;
        }

        return null;
    }

    public synchronized void revokeAccessToken() {
        Account[] accounts = accountManager.getAccountsByType(context.getString(R.string.account_type));
        Account account = getUserAccount(accounts);

        if (account != null) {
            String expiredToken = accountManager.peekAuthToken(account, TYPE_FULL_ACCESS);

            if (!TextUtils.isEmpty(expiredToken)) {
                accountManager.invalidateAuthToken(context.getString(R.string.account_type), expiredToken);

                accountManager.removeAccount(account, null, null);
            }
        }
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
}