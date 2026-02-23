package com.example.thegamechanger.ui.theme

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
            .background(Brush.verticalGradient(listOf(Color(0xFF660000), Color(0xFF1A0000))))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
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
                        style = androidx.compose.ui.text.TextStyle(letterSpacing = 4.sp, fontWeight = FontWeight.Bold, fontSize = 10.sp)
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
                        Text("DEALER / STATUS", Modifier.weight(1.5f), color = Color.White.collect(0.4f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text("SESSION", Modifier.weight(1f), color = Color.White.collect(0.4f), fontSize = 10.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        Text("ACTION", Modifier.weight(1f), color = Color.White.collect(0.4f), fontSize = 10.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.End)
                    }

                    dealers.forEachIndexed { index, dealer ->
                        DealerRowPremium(
                            dealer = dealer,
                            tables = tables,
                            currentTime = currentTime,
                            onAssign = { /* viewModel.assign(it) */ },
                            onStop = { /* viewModel.stop(it) */ }
                        )
                        if (index < dealers.lastIndex) {
                            HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun DealerRowPremium(
    dealer: DealerTable,
    tables: List<TableMaster>,
    currentTime: Long,
    onAssign: (TableMaster) -> Unit,
    onStop: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    // Timer Logic
   /* val timerText = remember(currentTime) {
        val entryTime = try { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).parse(dealer.EntryOnDate)?.time ?: 0L } catch (e: Exception) { 0L }
        if (dealer.EntryStatus == 1 && entryTime > 0) {
            val diff = currentTime - entryTime
            val h = diff / 3600000; val m = (diff % 3600000) / 60000; val s = (diff % 60000) / 1000
            String.format("%02d:%02d:%02d", h, m, s)
        } else "--:--:--"
    }

    */
    // Parse API time ONLY once
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

// Calculate difference every second
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
        // Dealer Info
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
                        .clickable { onStop() }
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
                            .clickable { expanded = true }
                            .background(PokerGoldNeon, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        tables.filter { it.Status == 1 }.forEach { table ->
                            DropdownMenuItem(text = { Text(table.Name) }, onClick = { expanded = false; onAssign(table) })
                        }
                    }
                }
            }
        }
    }
}

fun Color.collect(alpha: Float): Color = this.copy(alpha = alpha)

/*
@Composable
fun ManagerDashboard(
    viewModel: ManagerViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.loadDashboard(2)
    }

    val dealers = viewModel.dealerList
    val tables = viewModel.tableMasters

    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(PokerCrimsonTop, PokerCrimsonBottom)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .statusBarsPadding()
        ) {

            // HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "MANAGER DASHBOARD",
                        color = PokerGoldNeon,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp,
                        letterSpacing = 3.sp
                    )
                    Text(
                        "Live Records",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Button(
                    onClick = onLogout,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(0.1f)
                    )
                ) {
                    Text("EXIT", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // TABLE HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("DEALER", color = Color.Gray, modifier = Modifier.weight(1.5f))
                Text("TABLE", color = Color.Gray, modifier = Modifier.weight(1.5f))
                Text(
                    "TIME",
                    color = Color.Gray,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
                Text(
                    "ACTION",
                    color = Color.Gray,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            dealers.forEach { dealer ->
                DealerRow(
                    dealer = dealer,
                    tables = tables,
                    currentTime = currentTime
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun DealerRow(
    dealer: DealerTable,
    tables: List<TableMaster>,
    currentTime: Long
) {

    var expanded by remember { mutableStateOf(false) }

    val entryTimeMillis = remember(dealer.EntryOnDate) {
        try {
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS",
                Locale.getDefault()
            ).parse(dealer.EntryOnDate)?.time ?: 0L
        } catch (e: Exception) { 0L }
    }

    val elapsedMillis =
        if (dealer.EntryStatus == 1)
            currentTime - entryTimeMillis
        else 0L

    val seconds = (elapsedMillis / 1000) % 60
    val minutes = (elapsedMillis / (1000 * 60)) % 60
    val hours = (elapsedMillis / (1000 * 60 * 60))

    val timerText =
        if (dealer.EntryStatus == 1)
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        else "--:--:--"

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3A0F0F)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // LEFT SECTION
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = dealer.DealerName,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (dealer.EntryStatus == 1)
                        dealer.TableName
                    else
                        "Not Assigned",
                    color = PokerGoldNeon,
                    fontSize = 14.sp
                )
            }

            // RIGHT SECTION
            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = timerText,
                    color = PokerMint,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (dealer.EntryStatus == 1) {

                    Button(
                        onClick = { /* STOP API */ },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 22.dp,
                            vertical = 8.dp
                        )
                    ) {
                        Text(
                            "STOP",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                } else {

                    Box {

                        Button(
                            onClick = { expanded = true },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PokerGoldNeon
                            ),
                            contentPadding = PaddingValues(
                                horizontal = 20.dp,
                                vertical = 8.dp
                            )
                        ) {
                            Text(
                                "ADD TABLE",
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            tables.filter { it.Status == 1 }.forEach { table ->
                                DropdownMenuItem(
                                    text = { Text(table.Name) },
                                    onClick = {
                                        expanded = false
                                        // Assign API
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


 */



