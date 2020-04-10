package com.base.app.ui.base;

import android.os.Handler;

import androidx.fragment.app.FragmentManager;

import com.base.app.R;
import com.core.app.util.AlertUtil;

import static com.base.app.util.Constants.BACK_PRESS_INTERVAL;

public abstract class BaseHome extends BaseActivity {

    private Handler backPressHandler = new Handler();
    private boolean doubleBackToExitPressedOnce = false;
    private final Runnable backPressRunnable = () -> doubleBackToExitPressedOnce = false;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStackImmediate();
            return;
        } else {
            doubleBackToExitPressedOnce = true;
        }

        AlertUtil.showToast(this, getString(R.string.app_exit_msg), BACK_PRESS_INTERVAL);
        backPressHandler.postDelayed(backPressRunnable, BACK_PRESS_INTERVAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (backPressHandler != null) {
            backPressHandler.removeCallbacks(backPressRunnable);
        }
    }
}