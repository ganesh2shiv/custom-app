package com.custom.app.ui.password;

import com.core.app.data.user.UserManager;
import com.custom.app.data.model.password.change.ChangePasswordRequest;
import com.custom.app.data.model.password.forgot.ForgotPasswordRequest;
import com.custom.app.network.RestService;

import io.reactivex.Single;

public class PasswordInteractorImpl implements PasswordInteractor {

    private RestService restService;
    private UserManager userManager;

    public PasswordInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<String> forgotPassword(String username) {
        return restService.forgotPassword(new ForgotPasswordRequest(username))
                .map(PasswordParser::forgot);
    }

    @Override
    public Single<String> changePassword(String oldPassword, String newPassword) {
        return restService.changePassword(new ChangePasswordRequest(userManager.getUserId(), oldPassword, newPassword))
                .map(PasswordParser::change);
    }
}