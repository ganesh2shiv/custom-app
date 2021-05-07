package com.base.app;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.chuckerteam.chucker.api.ChuckerCollector;
import com.core.app.BuildConfig;
import com.jakewharton.threetenabp.AndroidThreeTen;

import javax.inject.Inject;

import timber.log.Timber;

public class BaseApplication extends MultiDexApplication {

    @Inject
    ChuckerCollector chuckerCollector;

    @Override
    public void onCreate() {
        super.onCreate();

//      StrictMode.enableDefaults();

        AndroidThreeTen.init(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                protected void log(int priority, String tag, @NonNull String msg, Throwable error) {
                    super.log(priority, tag, msg, error);

                    if (priority == Log.ERROR) {
                        chuckerCollector.onError(tag, error);
                    }
                }
            });
        } else {
//          Timber.plant(new CrashlyticsTree());
        }
    }
}