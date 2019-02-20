package com.core.app.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.core.app.CoreApplication;
import com.core.app.R;
import com.core.app.data.event.EventMessage;
import com.core.app.data.user.UserManager;
import com.core.app.data.user.UserType;
import com.core.app.http.ApiEndpoint;
import com.core.app.http.ApiManager;
import com.core.app.ui.network.NetworkErrorFragment;
import com.core.app.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import static com.core.app.util.Constants.EVENT_CONNECTIVITY_CONNECTED;
import static com.core.app.util.Constants.EVENT_CONNECTIVITY_LOST;
import static com.core.app.util.Constants.NETWORK_ERROR_FRAGMENT;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Inject
    protected ApiManager apiManager;

    @Inject
    protected UserManager userManager;

    private ProgressDialog progressDialog;
    private NetworkErrorFragment networkErrorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((CoreApplication) getApplication()).getCoreComponent().inject(this);

        if (networkErrorFragment == null) {
            networkErrorFragment = NetworkErrorFragment.newInstance();
        }
    }

    public void showGhostMenu(View view) {
        Context wrapper = new ContextThemeWrapper(this, R.style.CustomPopupMenu);
        PopupMenu popup = new PopupMenu(wrapper, view);
        popup.inflate(R.menu.menu_ghost);

        switch (userManager.getUserType()) {
            case SUPER:
                popup.getMenu().findItem(R.id.menu_super).setChecked(true);
                break;
        }

        switch (apiManager.getApiEndpoint()) {
            case RELEASE:
                popup.getMenu().findItem(R.id.menu_release).setChecked(true);
                break;
            case CUSTOM:
                popup.getMenu().findItem(R.id.menu_custom).setChecked(true);
                break;
        }

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_normal) {
                setUserTypeAndRelaunch(UserType.NORMAL.code);
            } else if (item.getItemId() == R.id.menu_super) {
                setUserTypeAndRelaunch(UserType.SUPER.code);
            }

            if (item.getItemId() == R.id.menu_mock) {
                setEndpointAndRelaunch(ApiEndpoint.MOCK.url);
            } else if (item.getItemId() == R.id.menu_release) {
                setEndpointAndRelaunch(ApiEndpoint.RELEASE.url);
            } else if (item.getItemId() == R.id.menu_custom) {
                apiManager.showCustomEndpointDialog(this);
            }

            return true;
        });
        popup.show();
    }

    public void setUserTypeAndRelaunch(int userType) {
        userManager.setUserType(userType);
        Util.showRelaunchApplicationDialog(this);
    }

    public void setEndpointAndRelaunch(String apiEndpoint) {
        apiManager.setApiEndpoint(apiEndpoint);
        Util.showRelaunchApplicationDialog(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventMessage event) {
        switch (event.getResultCode()) {
            case EVENT_CONNECTIVITY_LOST:
                showNetworkErrorFragmentIfNotShowing();
                break;
            case EVENT_CONNECTIVITY_CONNECTED:
                dismissNetworkErrorFragmentIfShowing();
                break;
        }
    }

    protected void showNetworkErrorFragmentIfNotShowing() {
        if (!isNetworkErrorFragmentShowing()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, networkErrorFragment, NETWORK_ERROR_FRAGMENT)
                    .commit();
        }
    }

    protected void dismissNetworkErrorFragmentIfShowing() {
        if (isNetworkErrorFragmentShowing()) {
            getSupportFragmentManager().beginTransaction().remove(networkErrorFragment).commit();
        }
    }

    private boolean isNetworkErrorFragmentShowing() {
        return networkErrorFragment != null && networkErrorFragment.isVisible();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
            progressDialog.setCancelable(false);
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
    public void showMessage(String msg) {
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