package com.custom.app.ui.password

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.app.api.ApiRepository
import com.network.app.http.NetworkResponse
import com.network.app.model.password.change.ChangePasswordRequest
import com.network.app.model.password.forgot.ForgotPasswordRequest
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
class PasswordViewModel @ViewModelInject constructor(
        private val userManager: UserManager,
        private val apiRepository: ApiRepository
) : ViewModel() {

    val requestIntent = Channel<PasswordRequestIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<PasswordState>(PasswordState.Idle)
    val state: StateFlow<PasswordState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            requestIntent.consumeAsFlow().collect {
                when (it) {
                    is PasswordRequestIntent.Forgot -> forgotPassword(it.username)
                    is PasswordRequestIntent.Change -> changePassword(it.oldPassword, it.newPassword)
                }
            }
        }
    }

    suspend fun forgotPassword(username: String) {
        viewModelScope.launch {
            _state.value = PasswordState.Loading
            val request = ForgotPasswordRequest(username)
            val response = apiRepository.forgotPassword(request)

            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    PasswordState.Success(response.body.message)
                }
                is NetworkResponse.ApiError -> {
                    PasswordState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    PasswordState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    PasswordState.Error(response.error?.message)
                }
            }
        }
    }

    suspend fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            _state.value = PasswordState.Loading

            val request = ChangePasswordRequest(userManager.userId, oldPassword, newPassword)
            val response = apiRepository.changePassword(request)

            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    PasswordState.Success(response.body.message)
                }
                is NetworkResponse.ApiError -> {
                    PasswordState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    PasswordState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    PasswordState.Error(response.error?.message)
                }
            }
        }
    }
}