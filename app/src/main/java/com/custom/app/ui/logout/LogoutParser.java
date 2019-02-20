package com.custom.app.ui.logout;

import android.text.TextUtils;

import com.custom.app.data.model.logout.LogoutResponse;

import androidx.annotation.NonNull;
import retrofit2.Response;

class LogoutParser {

    @NonNull
    static String parse(Response<LogoutResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            LogoutResponse body = response.body();

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