package com.user.app.data;

import androidx.annotation.NonNull;

import com.data.app.prefs.BooleanPreference;
import com.data.app.prefs.IntPreference;
import com.data.app.prefs.StringPreference;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import timber.log.Timber;

public class UserManager {

    @Inject
    @Named("userId")
    @Singleton
    StringPreference userIdPref;

    @Inject
    @Named("userType")
    @Singleton
    IntPreference userTypePref;

    @Inject
    @Named("userName")
    @Singleton
    StringPreference userNamePref;

    @Inject
    @Named("fcmToken")
    @Singleton
    StringPreference fcmTokenPref;

    @Inject
    @Named("isLoggedIn")
    @Singleton
    BooleanPreference isLoggedInPref;

    @Inject
    @Named("userData")
    @Singleton
    StringPreference userDataPref;

    @Inject
    @Named("profilePath")
    @Singleton
    StringPreference profilePathPref;

    @Inject
    @Named("isNightMode")
    @Singleton
    BooleanPreference isNightModePref;

    UserManager(StringPreference userIdPref, IntPreference userTypePref,
                StringPreference userNamePref, StringPreference fcmTokenPref,
                BooleanPreference isLoggedInPref, StringPreference userDataPref,
                StringPreference profilePathPref, BooleanPreference isNightModePref) {
        this.userIdPref = userIdPref;
        this.userTypePref = userTypePref;
        this.userNamePref = userNamePref;
        this.fcmTokenPref = fcmTokenPref;
        this.isLoggedInPref = isLoggedInPref;
        this.userDataPref = userDataPref;
        this.profilePathPref = profilePathPref;
        this.isNightModePref = isNightModePref;
    }

    public String getUserId() {
        return userIdPref.get();
    }

    public void setUserId(String id) {
        userIdPref.set(id);
    }

    public int getUserType() {
        return userTypePref.get();
    }

    public void setUserType(int type) {
        userTypePref.set(type);
    }

    public String getUsername() {
        return userNamePref.get();
    }

    public void setUsername(String username) {
        userNamePref.set(username);
    }

    public String getFcmToken() {
        return fcmTokenPref.get();
    }

    public void setFcmToken(@NonNull String fcmToken) {
        fcmTokenPref.set(fcmToken);
    }

    public boolean isLoggedIn() {
        return isLoggedInPref != null && isLoggedInPref.get();
    }

    public void login(String userData) {
        isLoggedInPref.set(true);
        updateUserData(userData);
    }

    public void updateUserData(String userData) {
        userDataPref.set(userData);
    }

    public String getUserData() {
        return userDataPref.get();
    }

    public String getProfilePath() {
        return profilePathPref.get();
    }

    public void setProfilePath(String path) {
        profilePathPref.set(path);
    }

    public boolean isNightMode() {
        return isNightModePref.get();
    }

    public void setNightMode(boolean isDark) {
        Timber.d("Setting night mode to %b", isDark);
        isNightModePref.set(isDark);
    }

    public void clearData() {
        userIdPref.delete();
        userTypePref.delete();
        userNamePref.delete();
        userDataPref.delete();
        isLoggedInPref.delete();
        profilePathPref.delete();
        isNightModePref.delete();
    }
}