package com.custom.app.ui.splash;

import android.os.Bundle;

import com.core.app.ui.base.BaseActivity;
import com.core.app.util.ActivityUtil;
import com.core.app.util.RxUtils;
import com.custom.app.R;
import com.custom.app.ui.home.HomeActivity;
import com.custom.app.ui.login.LoginActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.custom.app.util.Constants.SPLASH_TIMEOUT_IN_SECONDS;

public class SplashActivity extends BaseActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        disposable = Single.timer(SPLASH_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(success -> {
                    if (userManager.isLoggedIn()) {
                        ActivityUtil.startActivity(this, HomeActivity.class, true);
                    } else {
                        ActivityUtil.startActivity(this, LoginActivity.class, true);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RxUtils.dispose(disposable);
    }
}