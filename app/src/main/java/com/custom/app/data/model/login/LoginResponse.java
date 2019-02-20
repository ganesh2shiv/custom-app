package com.custom.app.data.model.login;

import com.core.app.data.user.UserData;

public class LoginResponse {

    private boolean status;
    private UserData userData;
    private String msg;

    public boolean isStatus() {
        return status;
    }

    public UserData getUserData() {
        return userData;
    }

    public String getMsg() {
        return msg;
    }
}