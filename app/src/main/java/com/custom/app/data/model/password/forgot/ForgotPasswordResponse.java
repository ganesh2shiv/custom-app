package com.custom.app.data.model.password.forgot;

public class ForgotPasswordResponse {

    private String msg;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}