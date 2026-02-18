package com.example.thegamechanger.remote

import com.example.thegamechanger.model.LoginRequest
import com.example.thegamechanger.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DealerApiLogin {

    @POST("api/DealerApi/DealerLogin")
    suspend fun dealerLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}