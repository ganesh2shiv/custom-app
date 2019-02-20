package com.custom.app.ui.password.forgot;

import com.custom.app.data.model.password.forgot.ForgotPasswordRequest;
import com.custom.app.network.RestService;

import io.reactivex.Single;

public class ForgotPasswordInteractorImpl implements ForgotPasswordInteractor {

    private RestService restService;

    public ForgotPasswordInteractorImpl(RestService restService) {
        this.restService = restService;
    }

    @Override
    public Single<String> forgotPassword(String username) {

        return restService.forgotPassword(new ForgotPasswordRequest(username))
                .map(ForgotPasswordParser::parse);
    }
}