package com.example.thegamechanger.model

data class AddDealerOnTableRequest (
    val MId: Int,
    val DId: Int ,
    val TbId: Int,
    val Coin: Int ,
    val IsAdd: Int,
    val dtbId: Int
)