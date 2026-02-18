package com.example.thegamechanger.di

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "9DFD7FDA-RI568-45-8966")
            .build()
        return chain.proceed(newRequest)
    }

}