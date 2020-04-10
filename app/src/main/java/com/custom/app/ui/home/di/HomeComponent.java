package com.custom.app.ui.home.di;

import com.custom.app.ui.home.HomeActivity;
import com.custom.app.ui.home.HomeFragment;

import dagger.Subcomponent;

@HomeScope
@Subcomponent()
public interface HomeComponent {

    void inject(HomeActivity activity);

    void inject(HomeFragment fragment);

}