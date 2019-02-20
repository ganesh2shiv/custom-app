package com.custom.app.data.model.password.change;

public class ChangePasswordRequest {

    private String userId;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordRequest(String userId, String oldPassword, String newPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}