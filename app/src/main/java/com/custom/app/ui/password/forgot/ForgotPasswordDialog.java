package com.custom.app.ui.password.forgot;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.core.app.rule.Username;
import com.core.app.ui.base.BaseDialog;
import com.core.app.util.AlertUtil;
import com.custom.app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public class ForgotPasswordDialog extends BaseDialog implements ForgotPasswordView, ValidationListener {

    private View dialogView;
    private Unbinder unbinder;
    private Validator validator;

    @Inject
    ForgotPasswordPresenter presenter;

    @Order(1)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_username_msg)
    @Username(sequence = 2, messageResId = R.string.invalid_username_msg)
    @BindView(R.id.et_username)
    TextInputEditText etUsername;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @OnClick(R.id.btn_submit)
    void submit() {
        validator.validate();
    }

    public static ForgotPasswordDialog newInstance() {
        return new ForgotPasswordDialog();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
        validator.registerAnnotation(Username.class);
        validator.setValidationListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialogView = inflater.inflate(R.layout.dialog_forgot_pwd, container, false);
        unbinder = ButterKnife.bind(this, dialogView);

        return dialogView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
    }

    @Override
    public void onValidationSucceeded() {
        presenter.callForgotPassword(etUsername.getText().toString().trim());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String[] listMessage = error.getCollatedErrorMessage(context()).split("\n");
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
        btnSubmit.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        btnSubmit.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(dialogView, msg);
    }

    @Override
    public void showLoginScreen(String msg) {
        AlertUtil.showToast(context(), msg);

        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.destroy();
        unbinder.unbind();
    }
}