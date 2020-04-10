package com.network.app.oauth;

import com.network.app.auth.TokenResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TokenService {

    @GET("/api/auth/token")
    Single<TokenResponse> refreshToken(@Header("Authorization") String token);

}