package com.custom.app.ui.home.di;

import com.custom.app.ui.home.HomeActivity;
import com.custom.app.ui.home.HomeFragment;
import com.custom.app.ui.notification.NotificationActivity;
import com.custom.app.ui.notification.di.NotificationModule;

import dagger.Subcomponent;

@HomeScope
@Subcomponent(modules = {NotificationModule.class})
public interface HomeComponent {

    void inject(HomeActivity activity);

    void inject(HomeFragment fragment);

    void inject(NotificationActivity activity);

}