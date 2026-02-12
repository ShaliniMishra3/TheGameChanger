package com.example.thegamechanger.viewmodel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thegamechanger.model.DealerAssignment
import com.example.thegamechanger.ui.theme.PokerCrimsonBottom
import com.example.thegamechanger.ui.theme.PokerCrimsonTop
import com.example.thegamechanger.ui.theme.PokerDialogGlass
import com.example.thegamechanger.ui.theme.PokerGlass
import com.example.thegamechanger.ui.theme.PokerGoldNeon
import com.example.thegamechanger.ui.theme.PokerMint
import kotlinx.coroutines.delay

@Composable
fun ManagerDashboard(onLogout: () -> Unit) {
    val dealers = remember {
        mutableStateListOf(
            DealerAssignment("Roopesh Pandey", "Table Alpha", System.currentTimeMillis()),
            DealerAssignment("Aman Verma", "Table Bravo", System.currentTimeMillis()),
            DealerAssignment("Arpit Pandey", "Table Charlie", System.currentTimeMillis())
        )
    }
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    val tables = listOf("Table Alpha", "Table Bravo", "Table Charlie", "Table Delta")
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(1000)
        }
    }
    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(PokerCrimsonTop, PokerCrimsonBottom)))) {
        Column(modifier = Modifier.padding(16.dp).statusBarsPadding()) {
            // Header
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("MANAGER DASHBOARD", color = PokerGoldNeon, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp, letterSpacing = 3.sp)
                    Text("Live Records", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                }
                Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(0.1f))) {
                    Text("EXIT", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            // DEALER TABLE HEADER
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("DEALER", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.weight(1.5f))
                Text("TABLE", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.weight(1.5f))
                Text("TIME", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.End)
            }
            Spacer(modifier = Modifier.height(8.dp))
            // RENDER MULTIPLE DEALER ROWS
            dealers.forEachIndexed { index, dealer ->
                DealerRow(
                    dealer = dealer,
                    currentTime = currentTime,
                    tables = tables,
                    onTableChange = { newTable ->
                        // Update the specific dealer and reset their specific timer
                        dealers[index] = dealer.copy(
                            selectedTable = newTable,
                            assignmentTime = System.currentTimeMillis()
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
@Composable
fun DealerRow(
    dealer: DealerAssignment,
    currentTime: Long,
    tables: List<String>,
    onTableChange: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    // Calculate time for THIS specific dealer
    val elapsedMillis = currentTime - dealer.assignmentTime
    val seconds = (elapsedMillis / 1000) % 60
    val minutes = (elapsedMillis / (1000 * 60)) % 60
    val hours = (elapsedMillis / (1000 * 60 * 60))
    val timerText = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PokerGlass),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            // 1. Dealer Name
            Text(
                text = dealer.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.weight(1.5f)
            )

            Box(modifier = Modifier.weight(1.5f)) {
                Row(
                    modifier = Modifier
                        .background(Color.Black.copy(0.2f), RoundedCornerShape(8.dp))
                        .border(1.dp, Color.White.copy(0.1f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .clickable { isExpanded = true },
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text(dealer.selectedTable, color = PokerGoldNeon, fontSize = 13.sp, modifier = Modifier.weight(1f))
                    Text("▼", color = PokerGoldNeon, fontSize = 10.sp)
                }
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier.background(PokerDialogGlass)
                ) {
                    tables.forEach { table ->
                        DropdownMenuItem(
                            text = { Text(table, color = Color.White) },
                            onClick = {
                                isExpanded = false
                                onTableChange(table)
                            }
                        )
                    }
                }
            }
            // 3. Independent Timer
            Text(
                text = timerText,
                color = PokerMint,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }
}


/*
@Composable
fun ManagerDashboard(onLogout: () -> Unit) {
    var selectedTable by remember { mutableStateOf("Table Alpha") }
    var assignmentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    var isExpanded by remember { mutableStateOf(false) } // Added missing variable
    val tables = listOf("Table Alpha", "Table Bravo", "Table Charlie", "Table Delta")

    // Fixed Timer logic: Update 'currentTime' every second
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(1000)
        }
    }

    // Calculate elapsed time since assignment
    val elapsedMillis = currentTime - assignmentTime
    val seconds = (elapsedMillis / 1000) % 60
    val minutes = (elapsedMillis / (1000 * 60)) % 60
    val hours = (elapsedMillis / (1000 * 60 * 60))
    val timerText = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(PokerCrimsonTop, PokerCrimsonBottom)))) {
        Column(modifier = Modifier.padding(24.dp).statusBarsPadding()) {

            // Header
            Text("MANAGER DASHBOARD", color = PokerGoldNeon, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp, letterSpacing = 3.sp)
            Text("Assignment Control", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)

            Spacer(modifier = Modifier.height(30.dp))

            // DEAL NAME CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PokerGlass),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("CURRENT DEALER", color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text("Roopesh Saini", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(20.dp))

                    // TIMER DISPLAY
                    Text("SESSION DURATION", color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(timerText, color = PokerMint, fontSize = 48.sp, fontWeight = FontWeight.Black, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // TABLE SELECTION DROPDOWN
            Text("ASSIGN TABLE", color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))

            Box {
                Button(
                    onClick = { isExpanded = true },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(selectedTable, color = Color.White)
                        Text("▼", color = PokerGoldNeon)
                    }
                }

                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier.background(PokerDialogGlass)
                ) {
                    tables.forEach { table ->
                        DropdownMenuItem(
                            text = { Text(table, color = Color.White) },
                            onClick = {
                                selectedTable = table
                                isExpanded = false
                                // RESET TIMER ON ASSIGNMENT
                                assignmentTime = System.currentTimeMillis()
                            }
                        )
                    }
                }
            }
        }
    }
}
*/