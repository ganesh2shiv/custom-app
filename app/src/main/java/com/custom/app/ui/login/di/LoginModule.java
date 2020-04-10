package com.custom.app.ui.login.di;

import com.custom.app.network.RestService;
import com.custom.app.ui.login.LoginInteractor;
import com.custom.app.ui.login.LoginInteractorImpl;
import com.custom.app.ui.login.LoginPresenter;
import com.custom.app.ui.login.LoginPresenterImpl;
import com.squareup.sqlbrite3.BriteDatabase;
import com.user.app.data.UserManager;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    LoginInteractor provideLoginInteractor(RestService restService, UserManager userManager,
                                           BriteDatabase database) {
        return new LoginInteractorImpl(restService, userManager, database);
    }

    @Provides
    LoginPresenter provideLoginPresenter(LoginInteractor interactor) {
        return new LoginPresenterImpl(interactor);
    }
}