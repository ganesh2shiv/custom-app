package com.base.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.core.app.util.FileCacher;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class BaseModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Resources provideResources(Context context) {
        return context.getResources();
    }

    @Provides
    @Singleton
    FileCacher<File> provideFileCacher(Context context) {
        return new FileCacher<>(context);
    }
}