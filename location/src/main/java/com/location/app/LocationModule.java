package com.location.app;

import android.content.Context;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import pl.charmas.android.reactivelocation2.ReactiveLocationProviderConfiguration;

@Module
@InstallIn(ApplicationComponent.class)
public class LocationModule {

    private static final int LOCATION_UPDATE_INTERVAL = 10 * 1000;

    @Provides
    @Singleton
    LocationRequest provideLocationRequest() {
        return LocationRequest.create()
                .setInterval(LOCATION_UPDATE_INTERVAL)
                .setFastestInterval(LOCATION_UPDATE_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Provides
    @Singleton
    LocationSettingsRequest provideLocationSettingsRequest(LocationRequest locationRequest) {
        return new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build();
    }

    @Provides
    @Singleton
    ReactiveLocationProvider provideReactiveLocationProvider(Context context) {
        return new ReactiveLocationProvider(context, ReactiveLocationProviderConfiguration.builder()
                .setRetryOnConnectionSuspended(true)
                .build());
    }
}