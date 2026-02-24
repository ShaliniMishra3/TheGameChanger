package com.example.thegamechanger.model

data class GameStartRequest (
    val DId:Int,
    val tbId:Int,
    val IsStart:Int,
    val WinningCoin:String,
    val Twid:Int
)