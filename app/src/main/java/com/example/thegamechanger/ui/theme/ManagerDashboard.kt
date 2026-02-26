package com.example.thegamechanger.ui.theme

import android.webkit.WebSettings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.thegamechanger.model.DealerTable
import com.example.thegamechanger.model.TableMaster
import com.example.thegamechanger.viewmodel.ManagerViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ManagerDashboard(
    viewModel: ManagerViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val isLoading = viewModel.isLoading
    val dealers = viewModel.dealerList
    val tables = viewModel.tableMasters
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }

     LaunchedEffect(Unit) {
        viewModel.loadDashboard(2)
        while (true) {
            currentTime = System.currentTimeMillis()
            kotlinx.coroutines.delay(1000)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFC90707), Color(0xFF1A0000))))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "MANAGEMENT",
                        color = PokerGoldNeon.copy(alpha = 0.6f),
                        style = androidx.compose.ui.text.TextStyle(
                            letterSpacing = 4.sp,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                    Text(
                        "Live Floor",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // Modern Ghost Button for Exit
                OutlinedButton(
                    onClick = onLogout,
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text("EXIT", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(32.dp)),
                shape = RoundedCornerShape(32.dp),
                color = Color.Black.copy(alpha = 0.3f)
            ) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.05f))
                            .padding(16.dp)
                    ) {
                        Text(
                            "DEALER / STATUS",
                            Modifier.weight(1.5f),
                            color = PokerGoldNeon,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "SESSION",
                            Modifier.weight(1f),
                            color = PokerGoldNeon.copy(alpha = 0.9f),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "ACTION",
                            Modifier.weight(1f),
                            color = PokerGoldNeon,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End
                        )
                    }

                    dealers.forEachIndexed { index, dealer ->
                        DealerRowPremium(
                            dealer = dealer,
                            tables = tables,
                            currentTime = currentTime,
                            onAssign = { dealerId, table ->
                                viewModel.assignDealer(
                                    mId = 2,
                                    dealerId = dealerId,
                                    tableId = table.TbId
                                )

                            },
                            onStop = { dealerId, tableId, dtbId, coin ->

                                viewModel.removeDealer(
                                    mId = 2,
                                    dealerId = dealerId,
                                    tableId = tableId,
                                    coin = coin,
                                    dtbId = dtbId
                                )

                            }
                        )
                        if (index < dealers.lastIndex) {
                            HorizontalDivider(
                                color = Color.White.copy(alpha = 0.05f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),

                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        viewModel.loadDashboard(2)

                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PokerGoldNeon
                    )
                ) {
                    Text(
                        "REFRESH",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,

                        )
                }


            }
        }
    }
    if (isLoading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.CircularProgressIndicator(
                color = PokerGoldNeon
            )
        }

    }
}

@Composable
fun DealerRowPremium(
    dealer: DealerTable,
    tables: List<TableMaster>,
    currentTime: Long,
    onAssign: (Int, TableMaster) -> Unit,
    onStop: (Int, Int, Int, Int) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var showStopDialog by remember { mutableStateOf(false) }
    //var coinText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedTable by remember { mutableStateOf<TableMaster?>(null) }
    val entryTimeMillis = remember(dealer.EntryOnDate) {
        try {
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS",
                Locale.getDefault()
            ).parse(dealer.EntryOnDate)?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
    val timerText = if (dealer.EntryStatus == 1 && entryTimeMillis > 0L) {
        val diff = (currentTime - entryTimeMillis).coerceAtLeast(0L)
        val hours = diff / 3600000
        val minutes = (diff % 3600000) / 60000
        val seconds = (diff % 60000) / 1000
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        "--:--:--"
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1.5f)) {
            Text(dealer.DealerName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(if (dealer.EntryStatus == 1) PokerMint else Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (dealer.EntryStatus == 1) dealer.TableName else "Standby",

                    color = if (dealer.EntryStatus == 1) PokerMint else Color.White.copy(0.4f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Commission : ${dealer.commission}",
                color = PokerGoldNeon,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = timerText,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = if (dealer.EntryStatus == 1) PokerMint else Color.White.copy(0.2f),
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            if (dealer.EntryStatus == 1) {
                Text(
                    "STOP",
                    color = Color.White,
                    modifier = Modifier
                        .clickable { showStopDialog = true }
                        .background(Color(0xFFFF3D3D).copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFFF3D3D).copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black
                )
            } else {
                Box {
                    Text(
                        "ASSIGN",
                        color = Color.Black,
                        modifier = Modifier
                            .clickable { showDialog = true }
                            .background(PokerGoldNeon, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
    if (showDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = Color(0xFFBB1919),
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    "Assign Table",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    tables.filter { it.Status == 1 }.forEach { table ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTable = table }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(
                                        if (selectedTable == table)
                                            PokerMint
                                        else
                                            Color.Gray,
                                        CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = table.Name,
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectedTable?.let { table ->

                            onAssign(
                                dealer.DId,
                                table
                            )

                            showDialog = false
                            selectedTable = null
                        }


                    },
                    enabled = selectedTable != null,
                    /*colors = ButtonDefaults.buttonColors(
                        containerColor = PokerMint
                    )
                    */
                    colors= ButtonDefaults.buttonColors(
                        containerColor =PokerMint,
                        disabledContainerColor = Yellow,
                        contentColor = Color.Black,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Text("ADD", color = Color.Black)
                }
            },
            dismissButton = {
                Text(
                    "Cancel",
                    modifier = Modifier
                        .clickable {
                            showDialog = false
                            selectedTable = null
                        }
                        .padding(8.dp),
                    color = Color.Gray
                )
            }
        )
    }

   /* if (showStopDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showStopDialog = false },

            containerColor = Color(0xFFD90404),

            title = {
                Text(
                    "Remove Dealer",
                    color = Color.White
                )
            },

            text = {

                Column {

                    Text(
                        "Enter Coin Amount",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    androidx.compose.material3.OutlinedTextField(
                        value = coinText,
                        onValueChange = { coinText = it },
                        singleLine = true,

                        textStyle= TextStyle(
                            fontSize = 18.sp
                        )


                    )

                }

            },


            confirmButton = {

                Button(

                    onClick = {

                        if (coinText.isBlank()) {

                            android.widget.Toast
                                .makeText(
                                    context,
                                    "Please enter coin amount",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()

                            return@Button
                        }

                        val coinValue = coinText.toInt()

                        onStop(

                            dealer.DId,
                            dealer.TbId,
                            dealer.dtbId,
                            coinValue

                        )

                        showStopDialog = false
                        coinText = ""

                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Yellow
                    )

                ) {

                    Text("REMOVE")

                }

            },
            dismissButton = {
                Text(

                    "Cancel",

                    modifier = Modifier
                        .clickable {
                            showStopDialog = false
                            coinText = ""
                        }
                        .padding(8.dp),

                    color = Color.Black,
                    fontSize = 18.sp,

                )

            }

        )
    }

    */

    if (showStopDialog) {

        androidx.compose.material3.AlertDialog(

            onDismissRequest = { showStopDialog = false },

            containerColor = Color(0xFFD90404),

            title = {
                Text(
                    "Remove Dealer",
                    color = Color.White
                )
            },

            text = {

                Column {

                    Text(
                        "Commission Amount",
                        color = Color.White,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "₹ ${dealer.commission}",
                        color = PokerGoldNeon,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
            },

            confirmButton = {

                Button(

                    onClick = {

                        onStop(

                            dealer.DId,
                            dealer.TbId,
                            dealer.dtbId,
                            dealer.commission   // direct value

                        )

                        showStopDialog = false
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Yellow
                    )

                ) {

                    Text("REMOVE")

                }

            },

            dismissButton = {

                Text(

                    "Cancel",

                    modifier = Modifier
                        .clickable {
                            showStopDialog = false
                        }
                        .padding(8.dp),

                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
        )
    }

}
fun Color.collect(alpha: Float): Color = this.copy(alpha = alpha)





