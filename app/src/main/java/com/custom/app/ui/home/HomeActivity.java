package com.custom.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener;

import com.base.app.ui.base.BaseHome;
import com.core.app.BuildConfig;
import com.core.app.util.ActivityUtil;
import com.core.app.util.AlertUtil;
import com.custom.app.CustomApp;
import com.custom.app.R;
import com.custom.app.ui.login.LoginActivity;
import com.custom.app.ui.logout.LogoutDialog;
import com.custom.app.ui.setting.SettingActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.gson.Gson;
import com.media.app.GlideApp;
import com.user.app.data.UserData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static com.core.app.util.Constants.BLUETOOTH_SETTINGS_REQUEST;
import static com.core.app.util.Constants.LOCATION_SETTINGS_REQUEST;
import static com.custom.app.util.Constants.HOME_FRAGMENT;
import static com.custom.app.util.Constants.LOGOUT_DIALOG;

public class HomeActivity extends BaseHome implements HomeFragment.Callback,
        OnNavigationItemSelectedListener, OnBackStackChangedListener, LogoutDialog.Callback {

    private MenuItem selectedMenuItem;
    private ActionBarDrawerToggle drawerToggle;

    @BindView(R.id.appbar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navView;

    @OnLongClick(R.id.toolbar)
    boolean toggleFabGhost() {
        if (BuildConfig.DEBUG) {
            if (fabGhost.getVisibility() == View.VISIBLE) {
                fabGhost.hide();
            } else {
                fabGhost.show();
            }
        }
        return true;
    }

    @BindView(R.id.fab_ghost)
    FloatingActionButton fabGhost;

    @OnClick(R.id.fab_ghost)
    public void showGhostMenu(View view) {
        super.showGhostMenu(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((CustomApp) getApplication()).getHomeComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        SwitchCompat switchNight = (SwitchCompat) navView.getMenu().findItem(R.id.nav_menu_night).getActionView();
        switchNight.setChecked(userManager.isNightMode());
        switchNight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                userManager.setNightMode(false);
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
            } else {
                userManager.setNightMode(true);
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if (selectedMenuItem != null) {
                    switch (selectedMenuItem.getItemId()) {
                        case R.id.nav_menu_home:
                            showHomeScreen();
                            break;
                        case R.id.nav_menu_night:
                            if (switchNight.isChecked()) {
                                switchNight.setChecked(false);
                            } else {
                                switchNight.setChecked(true);
                            }
                            break;
                        case R.id.nav_menu_settings:
                            ActivityUtil.startActivity(HomeActivity.this, SettingActivity.class, false);
                            break;
                        case R.id.nav_menu_logout:
                            LogoutDialog.newInstance().show(getSupportFragmentManager(), LOGOUT_DIALOG);
                            break;
                    }
                    selectedMenuItem = null;
                }
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        navView.setItemIconTintList(null);

        toolbar.setNavigationOnClickListener(v -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.layout_main);

            if (currentFragment instanceof HomeFragment) {
                drawerLayout.openDrawer(GravityCompat.START);
            } else {
                onBackPressed();
            }
        });

        if (!TextUtils.isEmpty(userManager.getUserData())) {
            String jsonData = userManager.getUserData();
            UserData user = new Gson().fromJson(jsonData, UserData.class);
            if (user != null) {
                View headerLayout = navView.getHeaderView(0);
                headerLayout.setOnClickListener(v -> showProfileScreen());
                CircleImageView civProfile = headerLayout.findViewById(R.id.civ_profile);
                GlideApp.with(this).load(user.getProfile())
                        .error(R.drawable.ic_profile).into(civProfile);

                TextView tvName = headerLayout.findViewById(R.id.tv_name);
                TextView tvAddress = headerLayout.findViewById(R.id.tv_address);

                if (!TextUtils.isEmpty(user.getName())) {
                    tvName.setText(user.getName());
                }

                tvAddress.setText(user.getAddress());
            }
        }

        if (savedInstanceState == null) {
            showHomeScreen();
        }
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showToast(this, msg);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (selectedMenuItem != menuItem) {
            selectedMenuItem = menuItem;
            return true;
        }
        return false;
    }

    @Override
    public void onBackStackChanged() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.layout_main);

        if (currentFragment instanceof HomeFragment) {
            drawerToggle.syncState();
            navView.setCheckedItem(R.id.nav_menu_home);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.layout_main);

                if (currentFragment instanceof HomeFragment) {
                    return false;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHomeScreen() {
        fragmentTransition(HomeFragment.newInstance(), HOME_FRAGMENT);
    }

    public void showProfileScreen() {
//       ActivityUtil.startActivity(this, ProfileActivity.class, false);
    }

    @Override
    public void showLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityUtil.startActivity(this, intent, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (requestCode) {
            case LOCATION_SETTINGS_REQUEST:
            case BLUETOOTH_SETTINGS_REQUEST:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ((CustomApp) getApplication()).releaseHomeComponent();
    }
}