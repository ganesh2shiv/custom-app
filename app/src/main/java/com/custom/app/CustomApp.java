package com.custom.app;

import android.app.Activity;

import com.core.app.CoreApplication;
import com.custom.app.ui.home.di.HomeComponent;
import com.custom.app.ui.notification.di.NotificationModule;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class CustomApp extends CoreApplication implements HasActivityInjector, HasSupportFragmentInjector {

    private AppComponent appComponent;
    private HomeComponent homeComponent;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
//          Timber.plant(new CrashlyticsTree());
        }

        getAppComponent().inject(this);
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .coreComponent(getCoreComponent())
                    .appModule(new AppModule())
                    .build();
        }
        return appComponent;
    }

    public HomeComponent getHomeComponent() {
        if (homeComponent == null) {
            homeComponent = getAppComponent().plus(
                    new NotificationModule());
        }
        return homeComponent;
    }

    public void releaseHomeComponent() {
        homeComponent = null;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingFragmentInjector;
    }
}