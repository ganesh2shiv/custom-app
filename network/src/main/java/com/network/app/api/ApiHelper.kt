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

interface ApiHelper {

    suspend fun login(request: LoginRequest) : NetworkResponse<LoginResponse, ErrorResponse>

    suspend fun refreshToken(token: String) : NetworkResponse<TokenResponse, ErrorResponse>

    suspend fun forgotPassword(request: ForgotPasswordRequest) : NetworkResponse<ForgotPasswordResponse, ErrorResponse>

    suspend fun changePassword(request: ChangePasswordRequest) : NetworkResponse<ChangePasswordResponse, ErrorResponse>

    suspend fun logout(request: LogoutRequest) : NetworkResponse<LogoutResponse, ErrorResponse>

}