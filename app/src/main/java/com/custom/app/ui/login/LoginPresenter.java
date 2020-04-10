package com.custom.app.ui.login;

import com.base.app.ui.base.BasePresenter;

public abstract class LoginPresenter extends BasePresenter<LoginView> {

    abstract void callLogin(String username, String password);

}