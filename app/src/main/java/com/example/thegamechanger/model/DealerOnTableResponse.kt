package com.example.thegamechanger.model

class DealerOnTableResponse(
    val Success: Boolean,
    val Message: String,
    val Data: DealerOnTableData
)
data class DealerOnTableData(
    val DealerTables: List<DealerTable>,
    val TableMasters: List<TableMaster>
)

data class DealerTable(
    val dtbId: Int,
    val DId: Int,
    val DealerName: String,
    val TbId: Int,
    val TableName: String,
    val commission:Int,
    val EntryOnDate: String,
    val EntryStatus: Int
)

data class TableMaster(
    val TbId: Int,
    val Name: String,
    val Status: Int
)