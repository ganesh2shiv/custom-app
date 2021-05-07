package com.network.app.api

import com.network.app.model.login.LoginRequest
import com.network.app.model.logout.LogoutRequest
import com.network.app.model.password.change.ChangePasswordRequest
import com.network.app.model.password.forgot.ForgotPasswordRequest
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun login(request: LoginRequest) = apiHelper.login(request)

    suspend fun refreshToken(token: String) = apiHelper.refreshToken(token)

    suspend fun forgotPassword(request: ForgotPasswordRequest) = apiHelper.forgotPassword(request)

    suspend fun changePassword(request: ChangePasswordRequest) = apiHelper.changePassword(request)

    suspend fun logout(request: LogoutRequest) = apiHelper.logout(request)

}