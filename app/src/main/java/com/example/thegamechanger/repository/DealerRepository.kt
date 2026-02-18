package com.example.thegamechanger.repository

import com.example.thegamechanger.UiState
import com.example.thegamechanger.model.LoginRequest
import com.example.thegamechanger.model.LoginResponse
import com.example.thegamechanger.remote.DealerApiLogin
import javax.inject.Inject

class DealerRepository @Inject constructor(
    private val dealerApi: DealerApiLogin
) {

    suspend fun login(request: LoginRequest): UiState<LoginResponse> {
        return try {
            val response = dealerApi.dealerLogin(request)

            if (response.isSuccessful && response.body() != null) {
                UiState.Success(response.body()!!)
            } else {
                UiState.Error("Login Failed")
            }

        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "Unknown Error")
        }
    }
}
