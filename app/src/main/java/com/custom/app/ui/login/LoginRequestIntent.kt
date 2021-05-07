package com.custom.app.ui.login

sealed class LoginRequestIntent {

    data class Login(val username: String, val password: String) : LoginRequestIntent()

}