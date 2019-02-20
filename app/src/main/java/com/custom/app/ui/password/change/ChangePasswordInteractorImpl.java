package com.custom.app.ui.password.change;

import com.core.app.data.user.UserManager;
import com.custom.app.data.model.password.change.ChangePasswordRequest;
import com.custom.app.network.RestService;

import io.reactivex.Single;

public class ChangePasswordInteractorImpl implements ChangePasswordInteractor {

    private RestService restService;
    private UserManager userManager;

    public ChangePasswordInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<String> changePassword(String oldPassword, String newPassword) {
        return restService.changePassword(new ChangePasswordRequest(userManager.getUserId(), oldPassword, newPassword))
                .map(ChangePasswordParser::parse);
    }
}