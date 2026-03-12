package com.example.thegamechanger.model

data class ChangePasswordApiResponse(
    val Success: Boolean,
    val Message: String,
    val Data: String
)

data class ChangePasswordDataWrapper(
    val data: List<ChangePasswordResult>
)

data class ChangePasswordResult(
    val result_status: Int,
    val msg: String
)