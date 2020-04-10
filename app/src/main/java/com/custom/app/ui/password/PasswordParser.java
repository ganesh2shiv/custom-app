package com.custom.app.ui.password;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.custom.app.data.model.password.change.ChangePasswordResponse;
import com.custom.app.data.model.password.forgot.ForgotPasswordResponse;

class PasswordParser {

    @NonNull
    static String forgot(ForgotPasswordResponse body) throws NullPointerException {

        if (body.isStatus()) {
            if (!TextUtils.isEmpty(body.getMessage())) {
                return body.getMessage();
            } else {
                throw new RuntimeException("Response payload is empty!");
            }
        } else {
            throw new RuntimeException(body.getMessage());
        }
    }

    @NonNull
    static String change(ChangePasswordResponse body) throws NullPointerException {

        if (body.isStatus()) {
            if (!TextUtils.isEmpty(body.getMessage())) {
                return body.getMessage();
            } else {
                throw new RuntimeException("Response payload is empty!");
            }
        } else {
            throw new RuntimeException(body.getMessage());
        }
    }
}