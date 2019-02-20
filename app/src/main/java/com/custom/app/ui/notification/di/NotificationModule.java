package com.custom.app.ui.notification.di;

import com.core.app.data.user.UserManager;
import com.custom.app.network.RestService;
import com.custom.app.ui.notification.NotificationInteractor;
import com.custom.app.ui.notification.NotificationInteractorImpl;
import com.custom.app.ui.notification.NotificationPresenter;
import com.custom.app.ui.notification.NotificationPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationModule {

    @Provides
    NotificationInteractor provideNotificationInteractor(RestService restService, UserManager userManager) {
        return new NotificationInteractorImpl(restService, userManager);
    }

    @Provides
    NotificationPresenter provideNotificationPresenter(NotificationInteractor interactor) {
        return new NotificationPresenterImpl(interactor);
    }
}