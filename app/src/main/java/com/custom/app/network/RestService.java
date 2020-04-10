package com.custom.app.network;

import com.custom.app.data.model.login.LoginRequest;
import com.custom.app.data.model.login.LoginResponse;
import com.custom.app.data.model.logout.LogoutRequest;
import com.custom.app.data.model.logout.LogoutResponse;
import com.custom.app.data.model.password.change.ChangePasswordRequest;
import com.custom.app.data.model.password.change.ChangePasswordResponse;
import com.custom.app.data.model.password.forgot.ForgotPasswordRequest;
import com.custom.app.data.model.password.forgot.ForgotPasswordResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestService {

    @POST("/api/auth/login")
    Single<LoginResponse> login(@Body LoginRequest request);

    @POST("/api/forgotPassword")
    Single<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest request);

    @POST("/api/changePassword")
    Single<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest request);

    @POST("/api/user/logout")
    Single<LogoutResponse> logout(@Body LogoutRequest request);

}