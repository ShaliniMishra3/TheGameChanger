package com.example.thegamechanger.model

class PlayerOnTableResponse(
    val Success: Boolean,
    val Message: String,
    val Data: PlayerOnTableData?
)
data class PlayerOnTableData(
    val data: List<PlayerOnTableItem>
)

data class PlayerOnTableItem(
    val ptbId: Int,
    val DId: Int,
    val tbId: Int,
    val PId: Int,
    val OnDate: String,
    val CoinEntry: Double,
    val EntryStatus: Int,
    val CoinExit: Double,
    val ExitDate: String?,
    val ExitStatus: Int,
    val PlayerName: String,
    val DealerName: String,
    val TableName: String
)