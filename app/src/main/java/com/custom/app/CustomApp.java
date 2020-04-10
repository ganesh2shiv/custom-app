package com.custom.app;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.base.app.BaseApplication;
import com.custom.app.ui.home.di.HomeComponent;
import com.user.app.data.UserManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class CustomApp extends BaseApplication implements HasActivityInjector, HasSupportFragmentInjector {

    private AppComponent appComponent;
    private HomeComponent homeComponent;

    @Inject
    UserManager userManager;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        getAppComponent().inject(this);
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .baseComponent(getBaseComponent())
                    .appModule(new AppModule())
                    .build();
        }
        return appComponent;
    }

    public HomeComponent getHomeComponent() {
        if (homeComponent == null) {
            homeComponent = getAppComponent().plus();
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