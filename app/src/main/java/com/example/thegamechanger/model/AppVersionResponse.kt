package com.example.thegamechanger.model


data class AppVersionResponse(
    val Success: Boolean,
    val Message: String,
    val Data: String
)

data class AppVersionWrapper(
    val data: List<AppVersionItem>
)

data class AppVersionItem(
    val result_status: Int,
    val message: String
)