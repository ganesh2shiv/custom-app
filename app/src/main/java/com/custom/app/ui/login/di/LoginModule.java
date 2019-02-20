package com.custom.app.ui.login.di;

import com.core.app.data.user.UserManager;
import com.custom.app.network.RestService;
import com.custom.app.ui.login.LoginInteractor;
import com.custom.app.ui.login.LoginInteractorImpl;
import com.custom.app.ui.login.LoginPresenter;
import com.custom.app.ui.login.LoginPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    LoginInteractor provideLoginInteractor(RestService restService, UserManager userManager) {
        return new LoginInteractorImpl(restService, userManager);
    }

    @Provides
    LoginPresenter provideLoginPresenter(LoginInteractor interactor) {
        return new LoginPresenterImpl(interactor);
    }
}