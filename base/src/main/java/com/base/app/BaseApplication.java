package com.base.app;

import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.chuckerteam.chucker.api.ChuckerCollector;
import com.core.app.BuildConfig;
import com.data.app.db.DbModule;
import com.data.app.event.EventMessage;
import com.data.app.prefs.PrefsModule;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.location.app.LocationModule;
import com.network.app.http.HttpModule;
import com.network.app.oauth.AccountModule;
import com.user.app.data.UserModule;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.network.app.util.Constants.EVENT_CONNECTIVITY_CONNECTED;
import static com.network.app.util.Constants.EVENT_CONNECTIVITY_LOST;

public class BaseApplication extends MultiDexApplication {

    @Inject
    ChuckerCollector chuckerCollector;

    private Disposable disposable;
    private BaseComponent baseComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//      StrictMode.enableDefaults();
//      ButterKnife.setDebug(BuildConfig.DEBUG);

        AndroidThreeTen.init(this);

        getBaseComponent().inject(this);

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

        if (disposable == null) {
            disposable = ReactiveNetwork.observeNetworkConnectivity(this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(connectivity -> {
                        if (connectivity.getState() != NetworkInfo.State.CONNECTED) {
                            EventBus.getDefault().post(new EventMessage(EVENT_CONNECTIVITY_LOST));
                        } else {
                            EventBus.getDefault().post(new EventMessage(EVENT_CONNECTIVITY_CONNECTED));
                        }
                    });
        }
    }

    public BaseComponent getBaseComponent() {
        if (baseComponent == null) {
            baseComponent = DaggerBaseComponent.builder()
                    .application(this)
                    .baseModule(new BaseModule())
                    .httpModule(new HttpModule())
                    .prefsModule(new PrefsModule())
                    .accountModule(new AccountModule())
                    .locationModule(new LocationModule())
                    .userModule(new UserModule())
                    .dbModule(new DbModule())
                    .build();
        }
        return baseComponent;
    }
}