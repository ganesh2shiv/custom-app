package com.custom.app.ui.logout.di;

import com.core.app.data.user.UserManager;
import com.custom.app.network.RestService;
import com.custom.app.ui.logout.LogoutInteractor;
import com.custom.app.ui.logout.LogoutInteractorImpl;
import com.custom.app.ui.logout.LogoutPresenter;
import com.custom.app.ui.logout.LogoutPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class LogoutModule {

    @Provides
    LogoutInteractor provideLogoutInteractor(RestService restService, UserManager userManager) {
        return new LogoutInteractorImpl(restService, userManager);
    }

    @Provides
    LogoutPresenter provideLogoutPresenter(LogoutInteractor interactor) {
        return new LogoutPresenterImpl(interactor);
    }
}