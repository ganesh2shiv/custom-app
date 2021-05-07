package com.base.app.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.base.app.R;
import com.core.app.util.Util;
import com.google.android.material.textfield.TextInputLayout;
import com.network.app.http.ApiEndpoint;
import com.network.app.http.ApiManager;
import com.network.app.oauth.TokenAuthenticator;
import com.network.app.ui.NetworkErrorFragment;
import com.user.app.data.UserManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.InternalCoroutinesApi;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

@AndroidEntryPoint
@InternalCoroutinesApi
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Inject
    protected ApiManager apiManager;

    @Inject
    protected UserManager userManager;

    @Inject
    protected TokenAuthenticator tokenAuthenticator;

    private ProgressDialog progressDialog;
    private NetworkErrorFragment networkErrorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (userManager.isNightMode()) {
            if (AppCompatDelegate.getDefaultNightMode() != MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
            }
        } else {
            if (AppCompatDelegate.getDefaultNightMode() != MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
            }
        }

        super.onCreate(savedInstanceState);

        tokenAuthenticator.setCallingActivity(this);

        if (networkErrorFragment == null) {
            networkErrorFragment = NetworkErrorFragment.newInstance();
        }
    }

    public void showGhostMenu(View view) {
        Context wrapper = new ContextThemeWrapper(this, R.style.CustomPopupMenu);
        PopupMenu popup = new PopupMenu(wrapper, view);
        popup.inflate(R.menu.menu_ghost);

        switch (ApiEndpoint.from(apiManager.getApiEndpoint())) {
            case RELEASE:
                popup.getMenu().findItem(R.id.menu_release).setChecked(true);
                break;
            case CUSTOM:
                popup.getMenu().findItem(R.id.menu_custom).setChecked(true);
                break;
        }

        if (userManager.isNightMode()) {
            popup.getMenu().findItem(R.id.menu_dark).setChecked(true);
        }

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_mock) {
                setEndpointAndRelaunch(ApiEndpoint.MOCK.url);
            } else if (item.getItemId() == R.id.menu_release) {
                setEndpointAndRelaunch(ApiEndpoint.RELEASE.url);
            } else if (item.getItemId() == R.id.menu_custom) {
                apiManager.showCustomEndpointDialog(this);
            }

            if (item.getItemId() == R.id.menu_light) {
                setNightModeAndRelaunch(false);
            } else if (item.getItemId() == R.id.menu_dark) {
                setNightModeAndRelaunch(true);
            }

            return true;
        });
        popup.show();
    }

    public void setNightModeAndRelaunch(boolean isDark) {
        userManager.setNightMode(isDark);
        Util.showRelaunchApplicationDialog(this);
    }

    public void setEndpointAndRelaunch(String apiEndpoint) {
        apiManager.setApiEndpoint(apiEndpoint);
        Util.showRelaunchApplicationDialog(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.processing_msg));
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(@Nullable String msg) {
    }

    public TextWatcher AutoErrorTextWatcher(final TextInputLayout til) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                til.setError("");
            }
        };
    }

    protected void fragmentTransition(Fragment fragment, String tag) {
        if (fragment != null && !fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout_main, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }
    }
}