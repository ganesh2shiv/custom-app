package com.custom.app.ui.login;

import com.custom.app.data.model.login.LoginResponse;

import androidx.annotation.NonNull;
import retrofit2.Response;

class LoginParser {

    @NonNull
    static LoginResponse parse(Response<LoginResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            LoginResponse body = response.body();

            if (body.isStatus()) {
                if (body.getUserData() != null) {
                    return body;
                } else {
                    throw new RuntimeException("Response payload is empty!");
                }
            } else {
                throw new RuntimeException(body.getMsg());
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }
}