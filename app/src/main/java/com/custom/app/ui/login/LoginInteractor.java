package com.custom.app.ui.login;

import com.core.app.data.user.UserData;

import io.reactivex.Single;

public interface LoginInteractor {

    Single<UserData> login(String username, String password);

}