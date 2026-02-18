package com.example.thegamechanger.model

data class InnerLoginWrapper(
    val data: List<DealerData>
)

class DealerData (
    val result_status: Int,
    val msg: String,
    val DId: Int,
    val Email: String,
    val Password: String
)