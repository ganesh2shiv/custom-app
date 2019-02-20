package com.core.app;

import android.net.NetworkInfo;

import com.core.app.data.event.EventMessage;
import com.core.app.data.prefs.PrefsModule;
import com.core.app.data.user.UserModule;
import com.core.app.http.HttpModule;
import com.core.app.location.LocationModule;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import org.greenrobot.eventbus.EventBus;

import androidx.multidex.MultiDexApplication;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.core.app.util.Constants.EVENT_CONNECTIVITY_CONNECTED;
import static com.core.app.util.Constants.EVENT_CONNECTIVITY_LOST;

public class CoreApplication extends MultiDexApplication {

    private Disposable disposable;
    private CoreComponent coreComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//      StrictMode.enableDefaults();
//      ButterKnife.setDebug(BuildConfig.DEBUG);

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

    public CoreComponent getCoreComponent() {
        if (coreComponent == null) {
            coreComponent = DaggerCoreComponent.builder()
                    .application(this)
                    .coreModule(new CoreModule())
                    .httpModule(new HttpModule())
                    .prefsModule(new PrefsModule())
                    .locationModule(new LocationModule())
                    .userModule(new UserModule())
                    .build();
        }
        return coreComponent;
    }
}