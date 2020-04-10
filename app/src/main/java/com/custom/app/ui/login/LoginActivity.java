package com.custom.app.ui.login;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.base.app.ui.base.BaseActivity;
import com.core.app.BuildConfig;
import com.core.app.util.ActivityUtil;
import com.core.app.util.AlertUtil;
import com.core.app.util.Util;
import com.custom.app.R;
import com.custom.app.data.model.login.LoginResponse;
import com.custom.app.ui.home.HomeActivity;
import com.custom.app.ui.password.forgot.ForgotPasswordDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.user.app.data.UserData;

import org.threeten.bp.Instant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

import static android.accounts.AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE;
import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static com.custom.app.util.Constants.FORGOT_PWD_DIALOG;
import static com.network.app.oauth.AccountConstants.KEY_REFRESH_TOKEN;
import static com.network.app.oauth.AccountConstants.KEY_TOKEN_EXPIRE_IN;
import static com.network.app.oauth.AccountConstants.KEY_TOKEN_TYPE;
import static com.network.app.oauth.AccountConstants.TOKEN_EXPIRE_IN_MILLIS;
import static com.network.app.oauth.AccountConstants.TYPE_FULL_ACCESS;

public class LoginActivity extends BaseActivity implements LoginView, ValidationListener {

    private Validator validator;
    private Bundle resultBundle;
    private String authTokenType;
    private AccountAuthenticatorResponse authenticatorResponse;

    @Inject
    LoginPresenter presenter;

    @Inject
    AccountManager accountManager;

    @BindView(R.id.layout_main)
    ConstraintLayout layoutMain;

    @Order(1)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_username_msg)
//  @Username(sequence = 2, messageResId = R.string.invalid_username_msg)
    @BindView(R.id.et_username)
    EditText etUsername;

    @Order(2)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_password_msg)
//  @Password(sequence = 2, min = 8, scheme = ALPHA_NUMERIC, messageResId = R.string.invalid_password_msg)
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @OnClick(R.id.btn_login)
    void login(View view) {
        Util.hideSoftKeyboard(view);
        validator.validate();
    }

    @OnClick(R.id.tv_forgot_pwd)
    void forgotPassword() {
        ForgotPasswordDialog.newInstance().show(getSupportFragmentManager(), FORGOT_PWD_DIALOG);
    }

    @OnClick(R.id.tv_signup)
    void contactAdmin() {
        Util.handleUrl(this, "mailto:admin@custom.com");
    }

    @BindView(R.id.fab_ghost)
    FloatingActionButton fabGhost;

    @OnLongClick(R.id.title)
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

    @OnClick(R.id.fab_ghost)
    public void showGhostMenu(View view) {
        super.showGhostMenu(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        authenticatorResponse = getIntent().getParcelableExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

        if (authenticatorResponse != null) {
            authenticatorResponse.onRequestContinued();
        }

        authTokenType = getIntent().getStringExtra(KEY_TOKEN_TYPE);

        if (authTokenType == null) {
            authTokenType = TYPE_FULL_ACCESS;
        }

        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
//      validator.registerAnnotation(Username.class);
        validator.setValidationListener(this);

        presenter.setView(this);
    }

    @Override
    public void onValidationSucceeded() {
        presenter.callLogin(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String[] listMessage = error.getCollatedErrorMessage(this).split("\n");
            String msg = listMessage[0];

            if (view instanceof EditText) {
                ((EditText) view).setError(msg);
                view.requestFocus();
            } else {
                showMessage(msg);
            }
        }
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(layoutMain, msg);
    }

    @Override
    public void showHomeScreen(LoginResponse response) {
        addOrUpdateAccount(response);

        if (!userManager.isLoggedIn()) {
            UserData user = response.getUserData();
            userManager.login(new Gson().toJson(user));
            userManager.setUserId(user.getId());
            userManager.setUsername(user.getUsername());
            userManager.setProfilePath(user.getProfile());

            ActivityUtil.startActivity(this, HomeActivity.class, true);
        } else {
            finish();
        }
    }

    public void addOrUpdateAccount(LoginResponse response) {
        Timber.d("Add or update account...");

        String accountName = response.getUserData().getUsername();

        final Account account = new Account(accountName, getString(R.string.account_type));

        long tokenExpireInMillis = (Instant.now().toEpochMilli()) + TOKEN_EXPIRE_IN_MILLIS;

        Bundle data = new Bundle();
        data.putString(KEY_ACCOUNT_NAME, accountName);
        data.putString(KEY_ACCOUNT_TYPE, getString(R.string.account_type));
        data.putString(KEY_REFRESH_TOKEN, response.getRefreshToken());
        data.putString(KEY_TOKEN_EXPIRE_IN, String.valueOf(tokenExpireInMillis));

        accountManager.addAccountExplicitly(account, null, data);
        accountManager.setAuthToken(account, authTokenType, response.getToken());

        resultBundle = data;
    }

    @Override
    public void finish() {
        if (authenticatorResponse != null) {
            if (resultBundle != null) {
                authenticatorResponse.onResult(resultBundle);
            } else {
                authenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED, "Canceled");
            }
            authenticatorResponse = null;
        }
        super.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.destroy();
    }
}