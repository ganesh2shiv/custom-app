package com.custom.app.ui.login;

import com.custom.app.data.model.login.LoginResponse;

import io.reactivex.Single;

public interface LoginInteractor {

    Single<LoginResponse> login(String username, String password);

}