package com.custom.app;

import com.base.app.BaseComponent;
import com.custom.app.ui.home.di.HomeComponent;

import dagger.Component;

@AppScope
@Component(modules = {AppInjector.class, AppModule.class}, dependencies = BaseComponent.class)
public interface AppComponent {

    void inject(CustomApp application);

    HomeComponent plus();
}