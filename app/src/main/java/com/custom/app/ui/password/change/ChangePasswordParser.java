package com.custom.app.ui.password.change;

import android.text.TextUtils;

import com.custom.app.data.model.password.change.ChangePasswordResponse;

import androidx.annotation.NonNull;
import retrofit2.Response;

class ChangePasswordParser {

    @NonNull
    static String parse(Response<ChangePasswordResponse> response) throws NullPointerException {

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