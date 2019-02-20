package com.core.app.http;

import android.content.Context;
import android.content.SharedPreferences;

import com.core.app.BuildConfig;
import com.core.app.data.prefs.StringPreference;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class HttpModule {

    private static final int READ_TIME_OUT = 30;
    private static final int CONNECT_TIMEOUT = 10;

    @Provides
    @Named("baseUrl")
    @Singleton
    StringPreference provideApiEndpoint(SharedPreferences prefs) {
        return new StringPreference(prefs, "baseUrl", ApiEndpoint.MOCK.url);
    }

    @Provides
    @Singleton
    ApiManager provideApiManager(@Named("baseUrl") StringPreference apiEndpointPref) {
        return new ApiManager(apiEndpointPref);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message))
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new ChuckInterceptor(context))
                .cache(null)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitClient(ApiManager apiManager, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.DEBUG ? apiManager.getApiEndpoint().url : ApiEndpoint.RELEASE.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
}