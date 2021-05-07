package com.user.app.data;

import android.content.SharedPreferences;

import com.data.app.prefs.BooleanPreference;
import com.data.app.prefs.IntPreference;
import com.data.app.prefs.StringPreference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class UserModule {

    @Provides
    @Singleton
    UserManager provideUserManager(@Named("userId") StringPreference userIdPref,
                                   @Named("userType") IntPreference userTypePref,
                                   @Named("userName") StringPreference userNamePref,
                                   @Named("fcmToken") StringPreference fcmTokenPref,
                                   @Named("isLoggedIn") BooleanPreference isLoggedInPref,
                                   @Named("userData") StringPreference userDataPref,
                                   @Named("profilePath") StringPreference profilePathPref,
                                   @Named("isNightMode") BooleanPreference isNightModePref) {
        return new UserManager(userIdPref, userTypePref, userNamePref, fcmTokenPref,
                isLoggedInPref, userDataPref, profilePathPref, isNightModePref);
    }

    @Provides
    @Named("userId")
    @Singleton
    StringPreference provideUserId(SharedPreferences prefs) {
        return new StringPreference(prefs, "userId");
    }

    @Provides
    @Named("userType")
    @Singleton
    IntPreference provideUserType(SharedPreferences prefs) {
        return new IntPreference(prefs, "userType");
    }

    @Provides
    @Named("userName")
    @Singleton
    StringPreference provideUserName(SharedPreferences prefs) {
        return new StringPreference(prefs, "userName");
    }

    @Provides
    @Named("fcmToken")
    @Singleton
    StringPreference provideFcmToken(SharedPreferences prefs) {
        return new StringPreference(prefs, "fcmToken");
    }

    @Provides
    @Named("isLoggedIn")
    @Singleton
    BooleanPreference provideIsLoggedIn(SharedPreferences prefs) {
        return new BooleanPreference(prefs, "isLoggedIn");
    }

    @Provides
    @Named("userData")
    @Singleton
    StringPreference provideUserData(SharedPreferences prefs) {
        return new StringPreference(prefs, "userData");
    }

    @Provides
    @Named("profilePath")
    @Singleton
    StringPreference provideProfilePath(SharedPreferences prefs) {
        return new StringPreference(prefs, "profilePath");
    }

    @Provides
    @Named("isNightMode")
    @Singleton
    BooleanPreference provideIsNightMode(SharedPreferences prefs) {
        return new BooleanPreference(prefs, "isNightMode");
    }
}