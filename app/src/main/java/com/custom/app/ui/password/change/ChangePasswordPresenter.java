package com.custom.app.ui.password.change;

import com.base.app.ui.base.BasePresenter;

public abstract class ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {

    abstract void changePassword(String oldPassword, String newPassword);

}