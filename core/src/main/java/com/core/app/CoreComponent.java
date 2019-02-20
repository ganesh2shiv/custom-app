package com.core.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.core.app.data.prefs.PrefsModule;
import com.core.app.data.user.UserManager;
import com.core.app.data.user.UserModule;
import com.core.app.http.ApiManager;
import com.core.app.http.HttpModule;
import com.core.app.location.LocationModule;
import com.core.app.ui.base.BaseActivity;
import com.core.app.ui.base.BaseDialog;
import com.core.app.util.FileCacher;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;

import java.io.File;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {CoreModule.class, HttpModule.class, PrefsModule.class,
        LocationModule.class, UserModule.class})
public interface CoreComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        Builder coreModule(CoreModule coreModule);

        Builder httpModule(HttpModule httpModule);

        Builder prefsModule(PrefsModule prefsModule);

        Builder locationModule(LocationModule locationModule);

        Builder userModule(UserModule userModule);

        CoreComponent build();

    }

    void inject(BaseActivity base);

    void inject(BaseDialog base);

    // Exposed objects below //

    Context provideContext();

    Retrofit provideRetrofit();

    Resources provideResources();

    ApiManager provideApiManager();

    UserManager provideUserManager();

    FileCacher<File> provideFileCacher();

    SharedPreferences providePreferences();

    LocationRequest provideLocationRequest();

    LocationSettingsRequest provideLocationSettingsRequest();

    ReactiveLocationProvider provideReactiveLocationProvider();

}