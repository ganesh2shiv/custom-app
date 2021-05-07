package com.custom.app.ui.login

import com.network.app.model.login.LoginResponse

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val response: LoginResponse?) : LoginState()
    data class Error(val msg: String?) : LoginState()
}