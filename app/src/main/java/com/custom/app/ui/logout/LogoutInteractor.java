package com.custom.app.ui.logout;

import io.reactivex.Single;

public interface LogoutInteractor {

    Single<String> logout();

}