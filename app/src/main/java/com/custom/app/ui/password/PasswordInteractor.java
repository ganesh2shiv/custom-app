package com.custom.app.ui.password;

import io.reactivex.Single;

public interface PasswordInteractor {

    Single<String> forgotPassword(String username);

    Single<String> changePassword(String oldPassword, String newPassword);

}