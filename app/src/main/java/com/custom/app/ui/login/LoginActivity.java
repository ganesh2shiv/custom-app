package com.custom.app.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.core.app.data.user.UserData;
import com.core.app.rule.Username;
import com.core.app.ui.base.BaseActivity;
import com.core.app.util.ActivityUtil;
import com.core.app.util.AlertUtil;
import com.core.app.util.Util;
import com.custom.app.BuildConfig;
import com.custom.app.R;
import com.custom.app.ui.home.HomeActivity;
import com.custom.app.ui.password.forgot.ForgotPasswordDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import javax.inject.Inject;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import dagger.android.AndroidInjection;

import static com.custom.app.util.Constants.FORGOT_PWD_DIALOG;
import static com.mobsandgeeks.saripaar.annotation.Password.Scheme.ALPHA_NUMERIC;

public class LoginActivity extends BaseActivity implements LoginView, ValidationListener {

    private Validator validator;

    @Inject
    LoginPresenter presenter;

    @BindView(R.id.layout_main)
    ConstraintLayout layoutMain;

    @Order(1)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_username_msg)
    @Username(sequence = 2, messageResId = R.string.invalid_username_msg)
    @BindView(R.id.et_username)
    TextInputEditText etUsername;

    @Order(2)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_password_msg)
    @Password(sequence = 2, min = 8, scheme = ALPHA_NUMERIC, messageResId = R.string.invalid_password_msg)
    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @OnClick(R.id.btn_login)
    void login() {
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

    @BindView(R.id.ghost_fab)
    FloatingActionButton ghostFab;

    @OnLongClick(R.id.title)
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

    @OnClick(R.id.ghost_fab)
    public void showGhostMenu(View view) {
        super.showGhostMenu(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
        validator.registerAnnotation(Username.class);
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
    public void showHomeScreen(UserData userData) {
        ActivityUtil.startActivity(this, HomeActivity.class, true);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(layoutMain, msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.destroy();
    }
}