package com.example.thegamechanger.model

data class AddPlayerTableRequest(
    val DId: Int,
    val PId: Int,
    val tbId: Int,
    val Coin: Double,
    val IsAdd: Int
)