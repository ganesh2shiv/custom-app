package com.custom.app.ui.logout;

import com.core.app.data.user.UserManager;
import com.custom.app.data.model.logout.LogoutRequest;
import com.custom.app.network.RestService;

import io.reactivex.Single;

public class LogoutInteractorImpl implements LogoutInteractor {

    private RestService restService;
    private UserManager userManager;

    public LogoutInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<String> logout() {

        String userId = userManager.getUserId();
        userManager.clearData();

        return restService.logout(new LogoutRequest(userId))
                .map(LogoutParser::parse);
    }
}