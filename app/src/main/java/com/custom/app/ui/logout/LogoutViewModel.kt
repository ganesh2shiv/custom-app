package com.custom.app.ui.logout

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.app.api.ApiRepository
import com.network.app.http.NetworkResponse
import com.network.app.model.logout.LogoutRequest
import com.network.app.oauth.OAuthTokenManager
import com.user.app.data.UserManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class LogoutViewModel @ViewModelInject constructor(
        private val userManager: UserManager,
        private val apiRepository: ApiRepository,
        private var tokenManager: OAuthTokenManager
) : ViewModel() {

    val requestIntent = Channel<LogoutRequestIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val state: StateFlow<LogoutState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            requestIntent.consumeAsFlow().collect() {
                when (it) {
                    is LogoutRequestIntent.Logout -> logout()
                }
            }
        }
    }

    suspend fun logout() {
        viewModelScope.launch {
            _state.value = LogoutState.Loading
            val userId = userManager.userId
            userManager.clearData()

            tokenManager.revokeAccessToken()

            val request = LogoutRequest(userId)
            val response = apiRepository.logout(request)

            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    LogoutState.Success(response.body.message)
                }
                is NetworkResponse.ApiError -> {
                    LogoutState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    LogoutState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    LogoutState.Error(response.error?.message)
                }
            }
        }
    }
}