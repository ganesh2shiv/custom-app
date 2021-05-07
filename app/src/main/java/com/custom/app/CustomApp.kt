package com.custom.app

import com.base.app.BaseApplication
import com.network.app.api.ApiRepository
import com.network.app.oauth.OAuthTokenManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CustomApp : BaseApplication() {

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var tokenManager: OAuthTokenManager

    override fun onCreate() {
        super.onCreate()

        tokenManager.setApiRepository(apiRepository)
    }
}