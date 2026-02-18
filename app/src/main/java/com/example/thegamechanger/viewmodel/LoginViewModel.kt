package com.example.thegamechanger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thegamechanger.UiState
import com.example.thegamechanger.model.InnerLoginWrapper
import com.example.thegamechanger.model.LoginRequest
import com.example.thegamechanger.model.LoginResponse
import com.example.thegamechanger.repository.DealerRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DealerRepository
) : ViewModel() {

    private val _loginState =
        MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val loginState = _loginState.asStateFlow()

    private val _navigateToManager = MutableStateFlow<Boolean?>(null)
    val navigateToManager = _navigateToManager.asStateFlow()

    /*fun login(email: String, password: String, role: String) {
        viewModelScope.launch {

            _loginState.value = UiState.Loading

            val result = repository.login(
                LoginRequest(email, password)
            )

            _loginState.value = result

            if (result is UiState.Success) {

                if (result.data.Success) {

                    // 🔥 Parse the inner JSON string
                    val gson = Gson()
                    val parsedData = gson.fromJson(
                        result.data.Data,
                        InnerLoginWrapper::class.java
                    )

                    val loginResult = parsedData.data.firstOrNull()

                    if (loginResult?.result_status == 1) {

                        _loginState.value =
                            UiState.Success(result.data)

                        // ✅ Only now navigate
                        _navigateToManager.value = role == "Manager"

                    } else {
                        _loginState.value =
                            UiState.Error(loginResult?.msg ?: "Wrong details")
                        _navigateToManager.value = null
                    }
                }
            }
        }
    }


     */
    fun login(email: String, password: String, role: String) {
        viewModelScope.launch {

            _loginState.value = UiState.Loading

            val result = repository.login(
                LoginRequest(email, password)
            )

            if (result is UiState.Success) {

                if (result.data.Success) {

                    val gson = Gson()
                    val parsedData = gson.fromJson(
                        result.data.Data,
                        InnerLoginWrapper::class.java
                    )

                    val loginResult = parsedData.data.firstOrNull()

                    if (loginResult?.result_status == 1) {

                        // ✅ Show success message
                        _loginState.value =
                            UiState.Success(result.data)

                        // Navigate
                        _navigateToManager.value = role == "Manager"

                    } else {
                        // ❌ Wrong credentials
                        _loginState.value =
                            UiState.Error(loginResult?.msg ?: "Wrong details")
                    }

                } else {
                    _loginState.value =
                        UiState.Error(result.data.Message)
                }

            } else if (result is UiState.Error) {
                _loginState.value = result
            }
        }
    }

    fun resetNavigation() {
        _navigateToManager.value = null
    }
}


