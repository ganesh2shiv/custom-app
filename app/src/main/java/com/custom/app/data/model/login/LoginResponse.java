package com.custom.app.data.model.login;

import com.user.app.data.UserData;

public class LoginResponse {

    private boolean status;
    private String message;
    private String token;
    private UserData user;
    private String refreshToken;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserData getUserData() {
        return user;
    }
}