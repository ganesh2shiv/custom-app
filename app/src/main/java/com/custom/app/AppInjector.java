package com.custom.app;

import com.custom.app.ui.login.LoginActivity;
import com.custom.app.ui.login.di.LoginModule;
import com.custom.app.ui.login.di.LoginScope;
import com.custom.app.ui.logout.LogoutDialog;
import com.custom.app.ui.logout.di.LogoutModule;
import com.custom.app.ui.logout.di.LogoutScope;
import com.custom.app.ui.password.PasswordModule;
import com.custom.app.ui.password.PasswordScope;
import com.custom.app.ui.password.change.ChangePasswordFragment;
import com.custom.app.ui.password.forgot.ForgotPasswordDialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = AndroidSupportInjectionModule.class)
abstract class AppInjector {

    @LoginScope
    @ContributesAndroidInjector(modules = {LoginModule.class})
    abstract LoginActivity contributeLoginInjector();

    @PasswordScope
    @ContributesAndroidInjector(modules = {PasswordModule.class})
    abstract ForgotPasswordDialog contributeForgotPasswordInjector();

    @PasswordScope
    @ContributesAndroidInjector(modules = {PasswordModule.class, LogoutModule.class})
    abstract ChangePasswordFragment contributeChangePasswordInjector();

    @LogoutScope
    @ContributesAndroidInjector(modules = {LogoutModule.class})
    abstract LogoutDialog contributeLogoutInjector();

}