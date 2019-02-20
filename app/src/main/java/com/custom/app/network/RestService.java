package com.custom.app.network;

import com.custom.app.data.model.login.LoginRequest;
import com.custom.app.data.model.login.LoginResponse;
import com.custom.app.data.model.logout.LogoutRequest;
import com.custom.app.data.model.logout.LogoutResponse;
import com.custom.app.data.model.notification.NotificationRequest;
import com.custom.app.data.model.notification.NotificationResponse;
import com.custom.app.data.model.password.change.ChangePasswordRequest;
import com.custom.app.data.model.password.change.ChangePasswordResponse;
import com.custom.app.data.model.password.forgot.ForgotPasswordRequest;
import com.custom.app.data.model.password.forgot.ForgotPasswordResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestService {

    @Headers({"content-type: application/json"})
    @POST("/apis/login")
    Single<Response<LoginResponse>> login(@Body LoginRequest request);

    @Headers({"content-type: application/json"})
    @POST("/apis/forgotPassword")
    Single<Response<ForgotPasswordResponse>> forgotPassword(@Body ForgotPasswordRequest request);

    @Headers({"content-type: application/json"})
    @POST("/apis/notificationList")
    Single<Response<NotificationResponse>> notificationList(@Body NotificationRequest request);

    @Headers({"content-type: application/json"})
    @POST("/apis/clearNotification")
    Single<Response<NotificationResponse>> clearNotification(@Body NotificationRequest request);

    @Headers({"content-type: application/json"})
    @POST("/apis/changePassword")
    Single<Response<ChangePasswordResponse>> changePassword(@Body ChangePasswordRequest request);

    @Headers({"content-type: application/json"})
    @POST("/apis/logout")
    Single<Response<LogoutResponse>> logout(@Body LogoutRequest request);

}