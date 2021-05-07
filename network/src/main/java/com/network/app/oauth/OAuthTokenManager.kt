package com.network.app.oauth

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.text.TextUtils
import com.network.app.R
import com.network.app.api.ApiRepository
import com.network.app.http.NetworkResponse
import com.user.app.data.UserManager
import kotlinx.coroutines.runBlocking
import org.threeten.bp.Instant
import timber.log.Timber

class OAuthTokenManager internal constructor(
        private val context: Context,
        private val userManager: UserManager,
        private val accountManager: AccountManager) {

    private lateinit var apiRepository: ApiRepository

    fun setApiRepository(apiRepository: ApiRepository) {
        this.apiRepository = apiRepository
    }

    @Synchronized
    fun getAccessToken(): String? {
        val accounts = accountManager.getAccountsByType(context.getString(R.string.account_type))
        val account = getUserAccount(accounts)
        if (account != null) {
            val tokenExpireIn = accountManager.getUserData(account, AccountConstants.KEY_TOKEN_EXPIRE_IN)
            if (!TextUtils.isEmpty(tokenExpireIn)) {
                try {
                    val expireTimestamp = tokenExpireIn.toLong()
                    val currentTimestamp = Instant.now().toEpochMilli()
                    return if (currentTimestamp < expireTimestamp || expireTimestamp == 0L) {
                        accountManager.peekAuthToken(account, AccountConstants.TYPE_FULL_ACCESS)
                    } else {
                        getRefreshToken()
                    }
                } catch (e: NumberFormatException) {
                    Timber.e(e)
                } catch (e: NullPointerException) {
                    Timber.e(e)
                }
            }
        }

        return null
    }

    @Throws(NullPointerException::class)
    @Synchronized
    private fun getRefreshToken(): String? {
        val accounts = accountManager.getAccountsByType(context.getString(R.string.account_type))
        val account = getUserAccount(accounts)
        if (account != null) {
            val refreshToken = accountManager.getUserData(account, AccountConstants.KEY_REFRESH_TOKEN)
            val header = String.format("Bearer %s", refreshToken)
            val response = runBlocking {
                apiRepository.refreshToken(header)
            }

            when (response) {
                is NetworkResponse.Success -> {
                    val accessToken = response.body.token
                    accountManager.setAuthToken(account, AccountConstants.TYPE_FULL_ACCESS, accessToken)
                    val tokenExpireInMillis = (Instant.now().toEpochMilli() + AccountConstants.TOKEN_EXPIRE_IN_MILLIS).toString()
                    accountManager.setUserData(account, AccountConstants.KEY_TOKEN_EXPIRE_IN, tokenExpireInMillis)
                    return accessToken
                }
            }
        }

        return null
    }

    @Synchronized
    fun revokeAccessToken() {
        val accounts = accountManager.getAccountsByType(context.getString(R.string.account_type))
        val account = getUserAccount(accounts)

        if (account != null) {
            val expiredToken = accountManager.peekAuthToken(account, AccountConstants.TYPE_FULL_ACCESS)

            if (!TextUtils.isEmpty(expiredToken)) {
                accountManager.invalidateAuthToken(context.getString(R.string.account_type), expiredToken)

                accountManager.removeAccount(account, null, null)
            }
        }
    }

    private fun getUserAccount(accounts: Array<Account>?): Account? {
        if (accounts != null && accounts.isNotEmpty()) {
            for (item in accounts) {
                if (item.name == userManager.username) {
                    return item
                }
            }
        }
        return null
    }
}