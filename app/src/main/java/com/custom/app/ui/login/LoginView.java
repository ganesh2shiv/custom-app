package com.custom.app.ui.login;

import com.core.app.data.user.UserData;
import com.core.app.ui.base.BaseView;

interface LoginView extends BaseView {

    void showHomeScreen(UserData userData);

}