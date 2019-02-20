package com.custom.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.core.app.ui.base.BaseHome;
import com.core.app.util.ActivityUtil;
import com.custom.app.BuildConfig;
import com.custom.app.CustomApp;
import com.custom.app.R;
import com.custom.app.ui.login.LoginActivity;
import com.custom.app.ui.logout.LogoutDialog;
import com.custom.app.ui.menu.MenuDialog;
import com.custom.app.ui.notification.NotificationActivity;
import com.custom.app.ui.profile.ProfileFragment;
import com.custom.app.ui.setting.SettingActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

import static com.custom.app.util.Constants.HOME_FRAGMENT;
import static com.custom.app.util.Constants.INT_CONST_FOR_HOME_FRAGMENT;
import static com.custom.app.util.Constants.INT_CONST_FOR_PROFILE_FRAGMENT;
import static com.custom.app.util.Constants.LOGOUT_DIALOG;
import static com.custom.app.util.Constants.MENU_DIALOG;
import static com.custom.app.util.Constants.PROFILE_FRAGMENT;

public class HomeActivity extends BaseHome implements OnBackStackChangedListener,
        HomeFragment.Callback, MenuDialog.Callback, LogoutDialog.Callback {

    private int selectedItemId;

    @BindView(R.id.appbar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnLongClick(R.id.toolbar)
    boolean toggleGhostFab() {
        if (BuildConfig.DEBUG) {
            if (ghostFab.getVisibility() == View.VISIBLE) {
                ghostFab.hide();
            } else {
                ghostFab.show();
            }
        }
        return true;
    }

    @BindView(R.id.ghost_fab)
    FloatingActionButton ghostFab;

    @OnClick(R.id.ghost_fab)
    public void showGhostMenu(View view) {
        super.showGhostMenu(view);
    }

    @BindView(R.id.bottom_app_bar)
    BottomAppBar bottomAppBar;

    @BindView(R.id.fab_action)
    FloatingActionButton fabAction;

    @BindView(R.id.btn_profile)
    ImageButton btnProfile;

    @BindView(R.id.btn_home)
    ImageButton btnHome;

    @OnClick(R.id.btn_home)
    public void openHome(View view) {
        navigateScreen(R.id.item_home);
    }

    @OnClick(R.id.btn_profile)
    public void openProfile(View view) {
        navigateScreen(R.id.item_profile);
    }

    @OnClick(R.id.btn_menu)
    public void openMenu(View view) {
        MenuDialog.newInstance().show(getSupportFragmentManager(), MENU_DIALOG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        ((CustomApp) getApplication()).getHomeComponent().inject(this);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        showLandingFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_notifications:
                ActivityUtil.startActivity(this, NotificationActivity.class, false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLandingFragment() {
        showHomeFragment();
    }

    private void showHomeFragment() {
        showFragment(INT_CONST_FOR_HOME_FRAGMENT, HomeFragment.newInstance(), HOME_FRAGMENT);
    }

    private void navigateScreen(@IdRes int itemId) {
        switch (itemId) {
            case R.id.item_home:
                showHomeFragment();
                break;
            case R.id.item_profile:
                showFragment(INT_CONST_FOR_PROFILE_FRAGMENT, ProfileFragment.newInstance(), PROFILE_FRAGMENT);
                break;
        }
    }

    @Override
    public void onBackStackChanged() {
        setNavFragment(getSupportFragmentManager().findFragmentById(R.id.layout_main));
    }

    private void setNavFragment(Fragment fragment) {
        if (fragment instanceof HomeFragment) {
            setNavSelectedItem(R.id.item_home);
        } else if (fragment instanceof ProfileFragment) {
            setNavSelectedItem(R.id.item_profile);
        }
    }

    private void setNavSelectedItem(@IdRes int itemId) {
        if (selectedItemId != itemId) {
            switch (itemId) {
                case R.id.item_home:
                    bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                    fabAction.setImageResource(R.drawable.ic_home_white_24dp);
                    btnProfile.setVisibility(View.VISIBLE);
                    btnHome.setVisibility(View.INVISIBLE);
                    break;
                case R.id.item_profile:
                    bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                    fabAction.setImageResource(R.drawable.ic_profile_white_24dp);
                    btnProfile.setVisibility(View.INVISIBLE);
                    btnHome.setVisibility(View.VISIBLE);
                    break;
            }
            selectedItemId = itemId;
        }
    }

    @Override
    public void showSettingScreen() {
        ActivityUtil.startActivity(HomeActivity.this, SettingActivity.class, false);
    }

    @Override
    public void showLogoutDialog() {
        LogoutDialog.newInstance().show(getSupportFragmentManager(), LOGOUT_DIALOG);
    }

    @Override
    public void showLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityUtil.startActivity(this, intent, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ((CustomApp) getApplication()).releaseHomeComponent();
    }
}