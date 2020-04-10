package com.custom.app.ui.logout;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.custom.app.data.model.logout.LogoutResponse;

class LogoutParser {

    @NonNull
    static String parse(LogoutResponse body) throws NullPointerException {

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