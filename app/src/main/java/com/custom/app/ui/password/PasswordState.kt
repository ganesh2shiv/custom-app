package com.custom.app.ui.password

sealed class PasswordState {
    object Idle : PasswordState()
    object Loading : PasswordState()
    data class Success(val msg: String?) : PasswordState()
    data class Error(val msg: String?) : PasswordState()
}