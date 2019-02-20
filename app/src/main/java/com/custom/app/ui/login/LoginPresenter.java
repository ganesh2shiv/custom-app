package com.custom.app.ui.login;

import com.core.app.ui.base.BasePresenter;

public abstract class LoginPresenter extends BasePresenter<LoginView> {

    abstract void callLogin(String username, String password);

}