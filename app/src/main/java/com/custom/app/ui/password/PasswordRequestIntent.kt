package com.custom.app.ui.password

sealed class PasswordRequestIntent {

    data class Forgot(val username: String) : PasswordRequestIntent()

    data class Change(val oldPassword: String, val newPassword: String) : PasswordRequestIntent()

}