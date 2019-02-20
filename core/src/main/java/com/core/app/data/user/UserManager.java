package com.core.app.data.user;

import com.core.app.data.prefs.BooleanPreference;
import com.core.app.data.prefs.IntPreference;
import com.core.app.data.prefs.StringPreference;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class UserManager {

    @Inject
    @Named("userId")
    @Singleton
    StringPreference userIdPref;

    @Inject
    @Named("fcmToken")
    @Singleton
    StringPreference fcmTokenPref;

    @Inject
    @Named("isLoggedIn")
    @Singleton
    BooleanPreference isLoggedInPref;

    @Inject
    @Named("userType")
    @Singleton
    IntPreference userTypePref;

    @Inject
    @Named("userData")
    @Singleton
    StringPreference userDataPref;

    @Inject
    @Named("profilePath")
    @Singleton
    StringPreference profilePathPref;

    UserManager(StringPreference userIdPref,
                StringPreference fcmTokenPref,
                BooleanPreference isLoggedInPref,
                IntPreference userTypePref,
                StringPreference userDataPref,
                StringPreference profilePathPref) {
        this.userIdPref = userIdPref;
        this.fcmTokenPref = fcmTokenPref;
        this.isLoggedInPref = isLoggedInPref;
        this.userTypePref = userTypePref;
        this.userDataPref = userDataPref;
        this.profilePathPref = profilePathPref;
    }

    public String getUserId() {
        return userIdPref.get();
    }

    public void setUserId(String id) {
        userIdPref.set(id);
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

    public UserType getUserType() {
        return UserType.from(userTypePref.get());
    }

    public void setUserType(int userType) {
        Timber.d("Setting user type to %d", userType);
        userTypePref.set(userType);
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

    public void clearData() {
        userIdPref.delete();
        userTypePref.delete();
        userDataPref.delete();
        isLoggedInPref.delete();
        profilePathPref.delete();
    }
}