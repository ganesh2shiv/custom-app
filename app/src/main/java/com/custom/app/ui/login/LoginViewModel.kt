package com.custom.app.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.app.api.ApiRepository
import com.network.app.http.NetworkResponse
import com.network.app.model.login.LoginRequest
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
class LoginViewModel @ViewModelInject constructor(
        private val userManager: UserManager,
        private val apiRepository: ApiRepository
) : ViewModel() {

    val requestIntent = Channel<LoginRequestIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            requestIntent.consumeAsFlow().collect {
                when (it) {
                    is LoginRequestIntent.Login -> login(it.username, it.password)
                }
            }
        }
    }

    suspend fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading

            val request = LoginRequest(userManager.fcmToken, username, password)
            val response = apiRepository.login(request)

            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    LoginState.Success(response.body)
                }
                is NetworkResponse.ApiError -> {
                    LoginState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    LoginState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    LoginState.Error(response.error?.message)
                }
            }
        }
    }
}