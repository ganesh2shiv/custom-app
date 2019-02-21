package com.custom.app.ui.password;

import com.core.app.data.user.UserManager;
import com.custom.app.network.RestService;
import com.custom.app.ui.logout.LogoutInteractor;
import com.custom.app.ui.password.change.ChangePasswordPresenter;
import com.custom.app.ui.password.change.ChangePasswordPresenterImpl;
import com.custom.app.ui.password.forgot.ForgotPasswordPresenter;
import com.custom.app.ui.password.forgot.ForgotPasswordPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class PasswordModule {

    @Provides
    PasswordInteractor providePasswordInteractor(RestService restService, UserManager userManager) {
        return new PasswordInteractorImpl(restService, userManager);
    }

    @Provides
    ForgotPasswordPresenter provideForgotPasswordPresenter(PasswordInteractor interactor) {
        return new ForgotPasswordPresenterImpl(interactor);
    }

    @Provides
    ChangePasswordPresenter provideChangePasswordPresenter(PasswordInteractor passwordInteractor,
                                                           LogoutInteractor logoutInteractor) {
        return new ChangePasswordPresenterImpl(passwordInteractor, logoutInteractor);
    }
}