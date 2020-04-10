package com.network.app.oauth;

import android.accounts.AccountManager;
import android.content.Context;

import com.user.app.data.UserManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AccountModule {

    @Provides
    @Singleton
    TokenService provideTokenService(Retrofit retrofit) {
        return retrofit.create(TokenService.class);
    }

    @Provides
    @Singleton
    AccountManager provideAccountManager(Context context) {
        return AccountManager.get(context);
    }

    @Provides
    @Singleton
    TokenAuthenticator provideTokenAuthenticator(Context context, UserManager userManager,
                                                 AccountManager accountManager) {
        return new TokenAuthenticator(context, userManager, accountManager);
    }

    @Provides
    @Singleton
    OAuthTokenManager provideOAuthTokenManager(Context context, UserManager userManager,
                                               AccountManager accountManager) {
        return new OAuthTokenManager(context, userManager, accountManager);
    }
}