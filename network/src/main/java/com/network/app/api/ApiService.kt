package com.network.app.api

import com.network.app.http.NetworkResponse
import com.network.app.model.ErrorResponse
import com.network.app.model.TokenResponse
import com.network.app.model.login.LoginRequest
import com.network.app.model.login.LoginResponse
import com.network.app.model.logout.LogoutRequest
import com.network.app.model.logout.LogoutResponse
import com.network.app.model.password.change.ChangePasswordRequest
import com.network.app.model.password.change.ChangePasswordResponse
import com.network.app.model.password.forgot.ForgotPasswordRequest
import com.network.app.model.password.forgot.ForgotPasswordResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("/api/auth/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body request: LoginRequest)
            : NetworkResponse<LoginResponse, ErrorResponse>

    @POST("/api/auth/token")
    @Headers("Content-Type: application/json")
    suspend fun refreshToken(@Header("Authorization") token: String)
            : NetworkResponse<TokenResponse, ErrorResponse>

    @POST("/api/forgotPassword")
    @Headers("Content-Type: application/json")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest)
            : NetworkResponse<ForgotPasswordResponse, ErrorResponse>

    @POST("/api/changePassword")
    @Headers("Content-Type: application/json")
    suspend fun changePassword(@Body request: ChangePasswordRequest)
            : NetworkResponse<ChangePasswordResponse, ErrorResponse>

    @POST("/api/user/logout")
    @Headers("Content-Type: application/json")
    suspend fun logout(@Body request: LogoutRequest)
            : NetworkResponse<LogoutResponse, ErrorResponse>

}