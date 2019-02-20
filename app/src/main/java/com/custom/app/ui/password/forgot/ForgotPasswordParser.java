package com.custom.app.ui.password.forgot;

import android.text.TextUtils;

import com.custom.app.data.model.password.forgot.ForgotPasswordResponse;

import androidx.annotation.NonNull;
import retrofit2.Response;

class ForgotPasswordParser {

    @NonNull
    static String parse(Response<ForgotPasswordResponse> response) throws NullPointerException {

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
}