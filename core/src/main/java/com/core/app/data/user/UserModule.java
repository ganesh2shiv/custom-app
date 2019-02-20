package com.core.app.data.user;

import android.content.SharedPreferences;

import com.core.app.data.prefs.BooleanPreference;
import com.core.app.data.prefs.IntPreference;
import com.core.app.data.prefs.StringPreference;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    @Singleton
    UserManager provideUserManager(@Named("userId") StringPreference userIdPref,
                                   @Named("fcmToken") StringPreference fcmTokenPref,
                                   @Named("isLoggedIn") BooleanPreference isLoggedInPref,
                                   @Named("userType") IntPreference userTypePref,
                                   @Named("userData") StringPreference userDataPref,
                                   @Named("profilePath") StringPreference profilePathPref) {
        return new UserManager(userIdPref, fcmTokenPref, isLoggedInPref, userTypePref, userDataPref, profilePathPref);
    }

    @Provides
    @Named("userId")
    @Singleton
    StringPreference provideUserId(SharedPreferences prefs) {
        return new StringPreference(prefs, "userId");
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
    @Named("userType")
    @Singleton
    IntPreference provideUserType(SharedPreferences prefs) {
        return new IntPreference(prefs,  "userType", UserType.NORMAL.code);
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
}