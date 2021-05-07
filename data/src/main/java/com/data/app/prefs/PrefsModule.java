package com.data.app.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

import static android.content.Context.MODE_PRIVATE;

@Module
@InstallIn(ApplicationComponent.class)
public class PrefsModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("preferences", MODE_PRIVATE);
    }
}