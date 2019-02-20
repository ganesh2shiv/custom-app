package com.core.app.location;

import android.content.Context;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;

import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import pl.charmas.android.reactivelocation2.ReactiveLocationProviderConfiguration;

import static com.core.app.util.Constants.LOCATION_UPDATE_INTERVAL;

@Module
public class LocationModule {

    @Provides
    LocationRequest provideLocationRequest() {
        return LocationRequest.create()
                .setInterval(LOCATION_UPDATE_INTERVAL)
                .setFastestInterval(LOCATION_UPDATE_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Provides
    LocationSettingsRequest provideLocationSettingsRequest(LocationRequest locationRequest) {
        return new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build();
    }

    @Provides
    ReactiveLocationProvider provideReactiveLocationProvider(Context context) {
        return new ReactiveLocationProvider(context, ReactiveLocationProviderConfiguration.builder()
                .setRetryOnConnectionSuspended(true)
                .build());
    }
}