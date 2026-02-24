package com.example.thegamechanger.repository

import com.example.thegamechanger.UiState
import com.example.thegamechanger.model.AddPlayerTableRequest
import com.example.thegamechanger.model.AddPlayerTableResponse
import com.example.thegamechanger.model.GameStartRequest
import com.example.thegamechanger.model.GameStartResponse
import com.example.thegamechanger.model.LoginRequest
import com.example.thegamechanger.model.LoginResponse
import com.example.thegamechanger.model.PlayerDto
import com.example.thegamechanger.model.PlayerOnTableItem
import com.example.thegamechanger.model.PlayerOnTableRequest
import com.example.thegamechanger.remote.DealerApiLogin
import retrofit2.Response
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
    suspend fun managerLogin(request: LoginRequest): UiState<LoginResponse>{
        return try{
            val response=dealerApi.managerLogin(request)
            if(response.isSuccessful && response.body() != null){
                UiState.Success(response.body()!!)
            }else{
                UiState.Error("Manager Login Failed")
            }
        }catch(e:Exception){
            UiState.Error(e.message ?: "Unknown Error")
        }
    }
    suspend fun getPlayerList(): UiState<List<PlayerDto>> {
        return try {
            val response = dealerApi.getPlayerList()
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                if (body.Success) {
                    UiState.Success(body.Data.data)
                } else {
                    UiState.Error(body.Message)
                }
            } else {
                UiState.Error("Failed to load data")
            }
        } catch (e: Exception) {
            UiState.Error(e.localizedMessage ?: "Unknown Error")
        }

    }
    suspend fun getPlayerOnTable(dealerId: Int): UiState<List<PlayerOnTableItem>> {
        return try {
            val response = dealerApi.getPlayerOnTable(
                PlayerOnTableRequest(DId = dealerId)
            )
            if (response.isSuccessful && response.body()?.Success == true) {
                UiState.Success(response.body()?.Data?.data ?: emptyList())
            } else {
                UiState.Error(response.body()?.Message ?: "Something went wrong")
            }

        } catch (e: Exception) {
            UiState.Error(e.message ?: "Network Error")
        }
    }
    suspend fun addPlayerToTable(
        request: AddPlayerTableRequest
    ): Response<AddPlayerTableResponse> {
        return dealerApi.addPlayerToTable(request)
    }

    suspend fun gameStart(request: GameStartRequest): UiState<GameStartResponse> {
        return try {
            val response = dealerApi.gameStart(request)
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Game API Error")
        }
    }
}
