package com.example.thegamechanger.model

data class GameStartResponse (
    val Success: Boolean,
    val Message: String,
    val Data: GameStartData
)

data class GameStartData(
    val Twid: Int,
    val DId: Int,
    val tbId: Int,
    val IsStart: Int,
    val WinningCoin: Double,
    val Result_status: Int,
    val Msg: String
)