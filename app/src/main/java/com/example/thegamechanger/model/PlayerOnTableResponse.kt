package com.example.thegamechanger.model

data class PlayerOnTableResponse(
    val Success: Boolean,
    val Message: String,
    val Data: PlayerOnTableData?
)

data class PlayerOnTableData(
    val DealerTables: List<PlayerOnTableItem>,
    val DealerDashboard: List<DealerDashboardItem>

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
    val PlayerName: String?,
    val DealerName: String?,
    val TableName: String?

)
data class DealerDashboardItem(
    val DId: Int,
    val DealerName: String,
    val tbId: Int,
    val TableName: String,
    val EntryOnDate: String

)