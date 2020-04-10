package com.custom.app.ui.logout.di;

import com.custom.app.network.RestService;
import com.custom.app.ui.logout.LogoutInteractor;
import com.custom.app.ui.logout.LogoutInteractorImpl;
import com.custom.app.ui.logout.LogoutPresenter;
import com.custom.app.ui.logout.LogoutPresenterImpl;
import com.network.app.oauth.OAuthTokenManager;
import com.user.app.data.UserManager;

import dagger.Module;
import dagger.Provides;

@Module
public class LogoutModule {

    @Provides
    LogoutInteractor provideLogoutInteractor(RestService restService, UserManager userManager,
                                             OAuthTokenManager tokenManager) {
        return new LogoutInteractorImpl(restService, userManager, tokenManager);
    }

    @Provides
    LogoutPresenter provideLogoutPresenter(LogoutInteractor interactor) {
        return new LogoutPresenterImpl(interactor);
    }
}