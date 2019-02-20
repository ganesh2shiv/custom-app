package com.custom.app.ui.password.change;

import io.reactivex.Single;

public interface ChangePasswordInteractor {

    Single<String> changePassword(String oldPassword, String newPassword);

}