package com.example.thegamechanger.model

data class PlayerListResponse(
    val Success: Boolean,
    val Message: String,
    val Data: PlayerDataWrapper
)

data class PlayerDataWrapper(
    val data: List<PlayerDto>
)

data class PlayerDto(
    val PId: Int,
    val UserId: String,
    val Name: String,
    val Mobile: String,
    val DId: Int,   // ✅ MUST EXIST
    val TbId: Int
)
