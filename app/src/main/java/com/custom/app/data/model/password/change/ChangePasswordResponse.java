package com.custom.app.data.model.password.change;

public class ChangePasswordResponse {

    private String msg;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}