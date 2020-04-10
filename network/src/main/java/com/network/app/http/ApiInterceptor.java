package com.network.app.http;

import android.text.TextUtils;

import com.network.app.oauth.OAuthTokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class ApiInterceptor implements Interceptor {

    private ApiManager apiManager;
    private OAuthTokenManager tokenManager;

    ApiInterceptor(ApiManager apiManager, OAuthTokenManager tokenManager) {
        this.apiManager = apiManager;
        this.tokenManager = tokenManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String url = originalRequest.url().toString();

        String token = "";
        if (!url.endsWith("/api/auth/token") && !url.endsWith("/api/auth/login")) {
            token = tokenManager.getAccessToken();
        }

        Request versionedRequest = originalRequest.newBuilder()
                .url(originalRequest.url().newBuilder()
                        .addQueryParameter("v", "1")
                        .build())
                .build();

        if (TextUtils.isEmpty(token) || originalRequest.header("Authorization") != null) {
            return chain.proceed(versionedRequest);
        }

        Request authorisedRequest = versionedRequest.newBuilder()
                .header("Authorization", String.format("Bearer %s", token))
                .build();

        return chain.proceed(authorisedRequest);
    }
}