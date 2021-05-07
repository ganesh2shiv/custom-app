package com.custom.app.ui.logout

sealed class LogoutRequestIntent {

    object Logout : LogoutRequestIntent()

}