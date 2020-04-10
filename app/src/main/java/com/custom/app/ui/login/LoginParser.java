package com.custom.app.ui.login;

import androidx.annotation.NonNull;

import com.custom.app.data.model.login.LoginResponse;

class LoginParser {

    @NonNull
    static LoginResponse parse(LoginResponse body) throws NullPointerException {
        if (body.isStatus()) {
            if (body.getUserData() != null) {
                return body;
            } else {
                throw new RuntimeException("Response payload is empty!");
            }
        } else {
            throw new RuntimeException(body.getMessage());
        }
    }
}