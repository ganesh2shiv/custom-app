package com.network.app.http;

import android.content.Context;
import android.content.SharedPreferences;

import com.chuckerteam.chucker.api.ChuckerCollector;
import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.chuckerteam.chucker.api.RetentionManager;
import com.core.app.BuildConfig;
import com.data.app.prefs.StringPreference;
import com.network.app.oauth.OAuthTokenManager;
import com.network.app.oauth.TokenAuthenticator;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import timber.log.Timber;

@Module
public class HttpModule {

    private static final int READ_TIME_OUT = 30;
    private static final int CONNECT_TIMEOUT = 10;

    @Provides
    @Named("baseUrl")
    @Singleton
    StringPreference provideApiEndpoint(SharedPreferences prefs) {
        return new StringPreference(prefs, "baseUrl", ApiEndpoint.RELEASE.url);
    }

    @Provides
    @Singleton
    ApiManager provideApiManager(@Named("baseUrl") StringPreference apiEndpointPref) {
        return new ApiManager(apiEndpointPref);
    }

    @Provides
    @Singleton
    ChuckerCollector provideChuckerCollector(Context context) {
        return new ChuckerCollector(context, true, RetentionManager.Period.FOREVER);
    }

    @Provides
    @Singleton
    ChuckerInterceptor provideChuckerInterceptor(Context context, ChuckerCollector collector) {
        return new ChuckerInterceptor(context, collector);
    }

    @Provides
    @Singleton
    ApiInterceptor provideApiInterceptor(ApiManager apiManager, OAuthTokenManager tokenManager) {
        return new ApiInterceptor(apiManager, tokenManager);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(ChuckerInterceptor chuckerInterceptor,
                                     TokenAuthenticator tokenAuthenticator, ApiInterceptor apiInterceptor) {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            BufferedInputStream bis = new BufferedInputStream(CertManager.getSelfSignedCert());
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            while (bis.available() > 0) {
                Certificate cert = certFactory.generateCertificate(bis);
                keyStore.setCertificateEntry("ca", cert);
            }
            String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(defaultAlgorithm);
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, null);

            return new OkHttpClient.Builder()
                    .retryOnConnectionFailure(false)
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                  .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
//                  .hostnameVerifier((hostname, session) -> {
//                      HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();
//                      return verifier.verify(hostname, session);
//                      return true;
//                  })
                    .addInterceptor(new HttpLoggingInterceptor(msg -> Timber.tag("OkHttp").d(msg))
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(chuckerInterceptor)
                    .authenticator(tokenAuthenticator)
                    .addInterceptor(apiInterceptor)
                    .cache(null)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            Timber.e(e);
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitClient(ApiManager apiManager, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.DEBUG ? apiManager.getApiEndpoint() : ApiEndpoint.RELEASE.url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
}