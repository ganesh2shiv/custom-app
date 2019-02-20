package com.custom.app;

import com.custom.app.network.RestService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
class AppModule {

    @Provides
    @AppScope
    RestService provideRestService(Retrofit retrofit) {
        return retrofit.create(RestService.class);
    }
}