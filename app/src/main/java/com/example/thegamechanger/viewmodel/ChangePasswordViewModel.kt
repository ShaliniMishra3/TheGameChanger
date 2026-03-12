package com.example.thegamechanger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thegamechanger.UiState
import com.example.thegamechanger.model.ChangePasswordDataWrapper
import com.example.thegamechanger.model.ChangePasswordRequest

import com.example.thegamechanger.repository.DealerRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: DealerRepository
) : ViewModel() {

    private val _changePasswordState =
        MutableStateFlow<UiState<String>>(UiState.Idle)

    val changePasswordState: StateFlow<UiState<String>> =
        _changePasswordState.asStateFlow()

    fun changePassword(
        email: String,
        oldPassword: String,
        newPassword: String,
        tp: String
    ) {

        viewModelScope.launch {

            _changePasswordState.value = UiState.Loading

            val result = repository.changePassword(
                ChangePasswordRequest(
                    Email = email,
                    OldPassword = oldPassword,
                    NewPassword = newPassword,
                    Tp = tp
                )
            )
            when (result) {

                is UiState.Success -> {
                    try {
                        val gson = Gson()
                        val parsed = gson.fromJson(
                            result.data.Data,
                            ChangePasswordDataWrapper::class.java
                        )
                        val response = parsed.data.firstOrNull()

                        if (response != null) {

                            _changePasswordState.value =
                                UiState.Success(response.msg)

                        } else {

                            _changePasswordState.value =
                                UiState.Error("Invalid response")

                        }

                    } catch (e: Exception) {

                        _changePasswordState.value =
                            UiState.Error("Parsing error")

                    }
                }

                is UiState.Error -> {

                    _changePasswordState.value =
                        UiState.Error(result.message ?: "Network error")

                }

                else -> {}
            }
        }
    }
}