package com.custom.app;

import com.core.app.CoreComponent;
import com.custom.app.ui.home.di.HomeComponent;
import com.custom.app.ui.notification.di.NotificationModule;

import dagger.Component;

@AppScope
@Component(modules = {AppInjector.class, AppModule.class}, dependencies = CoreComponent.class)
public interface AppComponent {

    void inject(CustomApp application);

    HomeComponent plus(NotificationModule notificationModule);

}