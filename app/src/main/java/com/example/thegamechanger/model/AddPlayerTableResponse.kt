package com.example.thegamechanger.model


data class AddPlayerTableResponse(
    val Success: Boolean,
    val Message: String,
    val Data: AddPlayerData?
)

data class AddPlayerData(
    val data: AddPlayerResult?
)

data class AddPlayerResult(
    val PId: Int,
    val DId: Int,
    val TbId: Int,
    val IsAdd: Int,
    val Coin: Double,
    val Result_status: Int,
    val Msg: String
)
