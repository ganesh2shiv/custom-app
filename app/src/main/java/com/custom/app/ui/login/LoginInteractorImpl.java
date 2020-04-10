package com.custom.app.ui.login;

import com.custom.app.data.model.login.LoginRequest;
import com.custom.app.data.model.login.LoginResponse;
import com.custom.app.network.RestService;
import com.squareup.sqlbrite3.BriteDatabase;
import com.user.app.data.UserManager;

import io.reactivex.Single;

public class LoginInteractorImpl implements LoginInteractor {

    private RestService restService;
    private UserManager userManager;
    private BriteDatabase database;

    public LoginInteractorImpl(RestService restService, UserManager userManager, BriteDatabase database) {
        this.restService = restService;
        this.userManager = userManager;
        this.database = database;
    }

    @Override
    public Single<LoginResponse> login(String username, String password) {
        return restService.login(new LoginRequest(userManager.getFcmToken(), username, password))
                .map(LoginParser::parse);
    }
}