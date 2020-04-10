package com.data.app.prefs;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class StringPreference {

    private final SharedPreferences preferences;
    private final String key;
    private final String defaultValue;

    public StringPreference(@NonNull SharedPreferences preferences, @NonNull String key) {
        this(preferences, key, "");
    }

    public StringPreference(@NonNull SharedPreferences preferences, @NonNull String key, @NonNull String defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @NonNull
    public String get() {
        return preferences.getString(key, defaultValue);
    }

    public boolean isSet() {
        return preferences.contains(key);
    }

    public void set(@NonNull String value) {
        preferences.edit().putString(key, value).apply();
    }

    public void delete() {
        preferences.edit().remove(key).apply();
    }
}