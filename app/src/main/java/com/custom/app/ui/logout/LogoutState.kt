package com.custom.app.ui.logout

sealed class LogoutState {
    object Idle : LogoutState()
    object Loading : LogoutState()
    data class Success(val msg: String?) : LogoutState()
    data class Error(val msg: String?) : LogoutState()
}