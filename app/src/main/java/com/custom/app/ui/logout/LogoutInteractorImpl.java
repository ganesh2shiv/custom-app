package com.custom.app.ui.logout;

import com.custom.app.data.model.logout.LogoutRequest;
import com.custom.app.network.RestService;
import com.network.app.oauth.OAuthTokenManager;
import com.user.app.data.UserManager;

import io.reactivex.Single;

public class LogoutInteractorImpl implements LogoutInteractor {

    private RestService restService;
    private UserManager userManager;
    private OAuthTokenManager tokenManager;

    public LogoutInteractorImpl(RestService restService, UserManager userManager,
                                OAuthTokenManager tokenManager) {
        this.restService = restService;
        this.userManager = userManager;
        this.tokenManager = tokenManager;
    }

    @Override
    public Single<String> logout() {

        String userId = userManager.getUserId();
        userManager.clearData();

        tokenManager.revokeAccessToken();

        return restService.logout(new LogoutRequest(userId))
                .map(LogoutParser::parse);
    }
}