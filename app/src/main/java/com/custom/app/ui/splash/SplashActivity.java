package com.custom.app.ui.splash;

import android.os.Bundle;

import com.base.app.ui.base.BaseActivity;
import com.core.app.util.ActivityUtil;
import com.custom.app.ui.home.HomeActivity;
import com.custom.app.ui.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (userManager.isLoggedIn()) {
            ActivityUtil.startActivity(this, HomeActivity.class, true);
        } else {
            ActivityUtil.startActivity(this, LoginActivity.class, true);
        }
    }
}