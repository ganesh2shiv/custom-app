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
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun login(request: LoginRequest): NetworkResponse<LoginResponse, ErrorResponse> {
        return apiService.login(request)
    }

    override suspend fun refreshToken(token: String): NetworkResponse<TokenResponse, ErrorResponse> {
        return apiService.refreshToken(token)
    }

    override suspend fun forgotPassword(request: ForgotPasswordRequest): NetworkResponse<ForgotPasswordResponse, ErrorResponse> {
        return apiService.forgotPassword(request)
    }

    override suspend fun changePassword(request: ChangePasswordRequest): NetworkResponse<ChangePasswordResponse, ErrorResponse> {
        return apiService.changePassword(request)
    }

    override suspend fun logout(request: LogoutRequest): NetworkResponse<LogoutResponse, ErrorResponse> {
        return apiService.logout(request)
    }
}