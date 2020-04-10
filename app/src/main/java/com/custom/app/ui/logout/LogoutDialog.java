package com.custom.app.ui.logout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.app.ui.base.BaseDialog;
import com.core.app.util.AlertUtil;
import com.custom.app.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public class LogoutDialog extends BaseDialog implements LogoutView {

    private Unbinder unbinder;
    private Callback callback;

    @Inject
    LogoutPresenter presenter;

    @OnClick(R.id.btn_yes)
    void logout() {
        presenter.callLogout();
    }

    @OnClick(R.id.btn_no)
    public void dismiss() {
        super.dismiss();
    }

    public static LogoutDialog newInstance() {
        return new LogoutDialog();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_logout, container, false);
        unbinder = ButterKnife.bind(this, dialogView);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialogView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showToast(context, msg);
    }

    @Override
    public void showLoginScreen() {
        if (callback != null) {
            callback.showLoginScreen();
        }

        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    public interface Callback {

        void showLoginScreen();

    }
}