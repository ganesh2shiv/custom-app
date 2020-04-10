package com.base.app;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.base.app.ui.base.BaseActivity;
import com.base.app.ui.base.BaseDialog;
import com.chuckerteam.chucker.api.ChuckerCollector;
import com.core.app.util.FileCacher;
import com.data.app.db.DbModule;
import com.data.app.prefs.PrefsModule;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.location.app.LocationModule;
import com.network.app.http.ApiManager;
import com.network.app.http.HttpModule;
import com.network.app.oauth.AccountModule;
import com.network.app.oauth.OAuthTokenManager;
import com.network.app.oauth.TokenAuthenticator;
import com.network.app.oauth.TokenService;
import com.squareup.sqlbrite3.BriteDatabase;
import com.user.app.data.UserManager;
import com.user.app.data.UserModule;

import java.io.File;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {BaseModule.class, HttpModule.class, PrefsModule.class,
        AccountModule.class, LocationModule.class, UserModule.class, DbModule.class})
public interface BaseComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        Builder baseModule(BaseModule baseModule);

        Builder httpModule(HttpModule httpModule);

        Builder prefsModule(PrefsModule prefsModule);

        Builder accountModule(AccountModule accountModule);

        Builder locationModule(LocationModule locationModule);

        Builder userModule(UserModule userModule);

        Builder dbModule(DbModule dbModule);

        BaseComponent build();
    }

    void inject(BaseDialog base);

    void inject(BaseActivity base);

    void inject(BaseApplication base);

    // Exposed objects below //

    Context provideContext();

    Retrofit provideRetrofit();

    Resources provideResources();

    ApiManager provideApiManager();

    UserManager provideUserManager();

    TokenService provideTokenService();

    BriteDatabase provideBriteDatabase();

    FileCacher<File> provideFileCacher();

    AccountManager provideAccountManager();

    LocationRequest provideLocationRequest();

    ChuckerCollector provideChuckerCollector();

    SharedPreferences provideSharedPreferences();

    OAuthTokenManager provideOAuthTokenManager();

    TokenAuthenticator provideTokenAuthenticator();

    LocationSettingsRequest provideLocationSettingsRequest();

    ReactiveLocationProvider provideReactiveLocationProvider();

}