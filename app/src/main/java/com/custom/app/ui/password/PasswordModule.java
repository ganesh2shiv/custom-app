package com.custom.app.ui.password;

import com.core.app.data.user.UserManager;
import com.custom.app.network.RestService;
import com.custom.app.ui.logout.LogoutInteractor;
import com.custom.app.ui.password.change.ChangePasswordInteractor;
import com.custom.app.ui.password.change.ChangePasswordInteractorImpl;
import com.custom.app.ui.password.change.ChangePasswordPresenter;
import com.custom.app.ui.password.change.ChangePasswordPresenterImpl;
import com.custom.app.ui.password.forgot.ForgotPasswordInteractor;
import com.custom.app.ui.password.forgot.ForgotPasswordInteractorImpl;
import com.custom.app.ui.password.forgot.ForgotPasswordPresenter;
import com.custom.app.ui.password.forgot.ForgotPasswordPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class PasswordModule {

    @Provides
    ForgotPasswordInteractor provideForgotPasswordInteractor(RestService restService) {
        return new ForgotPasswordInteractorImpl(restService);
    }

    @Provides
    ForgotPasswordPresenter provideForgotPasswordPresenter(ForgotPasswordInteractor interactor) {
        return new ForgotPasswordPresenterImpl(interactor);
    }

    @Provides
    ChangePasswordInteractor provideChangePasswordInteractor(RestService restService, UserManager userManager) {
        return new ChangePasswordInteractorImpl(restService, userManager);
    }

    @Provides
    ChangePasswordPresenter provideChangePasswordPresenter(ChangePasswordInteractor changeInteractor,
                                                           LogoutInteractor logoutInteractor) {
        return new ChangePasswordPresenterImpl(changeInteractor, logoutInteractor);
    }
}