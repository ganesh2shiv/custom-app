package com.custom.app.ui.login;

import com.core.app.data.user.UserData;
import com.core.app.data.user.UserManager;
import com.custom.app.data.model.login.LoginRequest;
import com.custom.app.data.model.login.LoginResponse;
import com.custom.app.network.RestService;
import com.google.gson.Gson;

import io.reactivex.Single;

public class LoginInteractorImpl implements LoginInteractor {

    private RestService restService;
    private UserManager userManager;

    public LoginInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<UserData> login(String username, String password) {

        return restService.login(new LoginRequest(userManager.getFcmToken(), username, password))
                .map(response -> {
                    LoginResponse loginResponse = LoginParser.parse(response);

                    UserData userData = loginResponse.getUserData();
                    userManager.login(new Gson().toJson(userData));
                    userManager.setUserId(userData.getUserId());
                    userManager.setProfilePath(userData.getProfile());

                    return userData;
                });
    }
}