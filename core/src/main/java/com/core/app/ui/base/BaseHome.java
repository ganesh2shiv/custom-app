package com.core.app.ui.base;

import android.os.Handler;
import android.util.SparseArray;

import com.core.app.R;
import com.core.app.util.AlertUtil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static com.core.app.util.Constants.BACK_PRESS_INTERVAL;

public abstract class BaseHome extends BaseActivity {

    protected Fragment currentFragment;
    private SparseArray<Fragment> fragmentStore = new SparseArray<>();

    private Handler backPressHandler = new Handler();
    private boolean doubleBackToExitPressedOnce = false;
    private final Runnable backPressRunnable = () -> doubleBackToExitPressedOnce = false;

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

        AlertUtil.showToast(getApplicationContext(), getString(R.string.app_exit_msg), BACK_PRESS_INTERVAL);
        backPressHandler.postDelayed(backPressRunnable, BACK_PRESS_INTERVAL);
    }

    protected void showFragment(int key, Fragment fragment, String tag) {
        currentFragment = fragmentStore.get(key);
        if (currentFragment == null) {
            currentFragment = fragment;
            fragmentStore.put(key, fragment);
        }
        fragmentTransition(currentFragment, tag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (backPressHandler != null) {
            backPressHandler.removeCallbacks(backPressRunnable);
        }
    }
}