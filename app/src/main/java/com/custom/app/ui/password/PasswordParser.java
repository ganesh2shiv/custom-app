package com.custom.app.ui.password;

import android.text.TextUtils;

import com.custom.app.data.model.password.change.ChangePasswordResponse;
import com.custom.app.data.model.password.forgot.ForgotPasswordResponse;

import androidx.annotation.NonNull;
import retrofit2.Response;

class PasswordParser {

    @NonNull
    static String forgot(Response<ForgotPasswordResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            ForgotPasswordResponse body = response.body();

            if (body.isStatus()) {
                if (!TextUtils.isEmpty(body.getMsg())) {
                    return body.getMsg();
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

    @NonNull
    static String change(Response<ChangePasswordResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            ChangePasswordResponse body = response.body();

            if (body.isStatus()) {
                if (!TextUtils.isEmpty(body.getMsg())) {
                    return body.getMsg();
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