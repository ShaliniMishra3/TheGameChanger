package com.example.thegamechanger.remote

import com.example.thegamechanger.model.AddPlayerTableRequest
import com.example.thegamechanger.model.AddPlayerTableResponse
import com.example.thegamechanger.model.DealerOnTableRequest
import com.example.thegamechanger.model.DealerOnTableResponse
import com.example.thegamechanger.model.LoginRequest
import com.example.thegamechanger.model.LoginResponse
import com.example.thegamechanger.model.PlayerListResponse
import com.example.thegamechanger.model.PlayerOnTableRequest
import com.example.thegamechanger.model.PlayerOnTableResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DealerApiLogin {
    @POST("api/Dealer/DealerLogin")
    suspend fun dealerLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("api/Manager/ManagerLogin")
    suspend fun managerLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("api/Dealer/PlayerList")
    suspend fun getPlayerList(): Response<PlayerListResponse>

    @POST("api/Dealer/PlayerOnTable")
    suspend fun getPlayerOnTable(
        @Body request: PlayerOnTableRequest
    ): Response<PlayerOnTableResponse>

    @POST("api/Dealer/AddPlayerTable")
    suspend fun addPlayerToTable(
        @Body request: AddPlayerTableRequest
    ): Response<AddPlayerTableResponse>

    @POST("api/Manager/DealerOnTable")
    suspend fun getDealerOnTable(
        @Body request: DealerOnTableRequest
    ): DealerOnTableResponse

}