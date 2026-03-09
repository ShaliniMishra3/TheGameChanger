package com.example.thegamechanger.ui.theme

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thegamechanger.UiState
import com.example.thegamechanger.model.PlayerDto
import com.example.thegamechanger.viewmodel.GameViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPersonScreen(
    viewModel: GameViewModel,
    gameStarted: Boolean,
    onBack: () -> Unit
) {
    var snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val playerState by viewModel.availablePlayers.collectAsState()
    val addState by viewModel.addPlayerState.collectAsState()
    var selectedPlayerId by remember { mutableStateOf<Int?>(null) }
    val dealerId by viewModel.dealerId
    val context = LocalContext.current
    val tableId by viewModel.tableId
    var showDialog by remember { mutableStateOf(false) }
    var selectedPlayerName by remember { mutableStateOf<String?>(null) }
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    var showWarningDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.fetchPlayerOnTable(dealerId)
        viewModel.loadPlayers()
    }

    LaunchedEffect(addState) {
        when (addState) {
            is UiState.Success -> {
                val msg = (addState as UiState.Success).data.Msg
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                viewModel.fetchPlayerOnTable(dealerId) // ⭐ IMPORTANT
                viewModel.clearAddPlayerState()
            }
            is UiState.Error -> {
                Toast.makeText(
                    context,
                    (addState as UiState.Error).message,
                    Toast.LENGTH_LONG
                ).show()

                viewModel.clearAddPlayerState()   // ✅ RESET
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(PokerCrimsonTop, PokerCrimsonBottom))
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.05f), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(100f, 100f),
                        radius = 1000f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .statusBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            // --- HEADER ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "INVITE TO TABLE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PokerGoldNeon,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "Players",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
                Text(
                    text = "CLOSE",
                    color = Color.White.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .clickable { onBack() }
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            // --- PLAYER LIST ---
            // Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                when (playerState) {
                    is UiState.Loading -> {
                        item {
                            Text("Loading...", color = Color.White)
                        }
                    }

                    is UiState.Error -> {
                        item {
                            Text(
                                text = (playerState as UiState.Error).message,
                                color = Color.Red
                            )
                        }
                    }
                    is UiState.Success<*> -> {
                        val players =
                            (playerState as UiState.Success<List<PlayerDto>>).data
                        items(players) { player ->
                            DealerRow(
                                dealerName = player.Name,
                                mobile = player.Mobile,
                                gameStarted=gameStarted,
                                onAddClick = {
                                    if(gameStarted){
                                        Toast.makeText(
                                            context,
                                            "Cannot add player while game is running",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@DealerRow
                                    }
                                    if (tableId > 0) {
                                        haptic.performHapticFeedback(
                                            androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove
                                        )
                                        selectedPlayerName = player.Name
                                        selectedPlayerId = player.PId
                                        showDialog = true
                                    }else{

                                        showWarningDialog=true
                                    }
                                }
                            )
                        }
                        if (players.isEmpty()) {
                            item {
                                Text(
                                    text = "No players found.",
                                    color = Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 40.dp)
                                )
                            }
                        }
                    }
                    else -> {}
                }
            }

        }
    }
    if (showDialog && selectedPlayerName != null) {
        AddPlayerDialog(
            playerName = selectedPlayerName!!,
            onDismiss = { showDialog = false },
            onAdd = { amountString ->
                val amount = amountString.toIntOrNull() ?: 0
                selectedPlayerId?.let { pId ->
                    viewModel.addPlayerToTable(
                        dId = dealerId,
                        pId = pId,
                        tbId = tableId,
                        coin = amount.toDouble(),
                        isAdd = 1
                    )
                }
                showDialog = false
            }
        )
    }
    if(showWarningDialog){

        BasicAlertDialog(
            onDismissRequest = { showWarningDialog = false }
        ) {

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2B1B1B)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "⚠ WARNING",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFC107)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Please assign a table to the dealer before adding players.",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { showWarningDialog = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFC107),
                            contentColor = Color.Black
                        )
                    ) {
                        Text("OK", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

}
@Composable
fun DealerRow(
    dealerName: String,
    mobile: String,
    gameStarted: Boolean,
    onAddClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(20.dp))
           .clickable {
             onAddClick()
           }

            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(dealerName.take(1), color = PokerGoldNeon, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dealerName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = mobile,
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(PokerGoldNeon, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("+", color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlayerDialog(
    playerName: String,
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit
) {
    var amountText by remember { mutableStateOf("") }

    BasicAlertDialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = PokerDialogGlass),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Column(modifier = Modifier.padding(28.dp)) {
                Text(
                    text = "BUY-IN AMOUNT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PokerGoldNeon,
                    letterSpacing = 2.sp
                )
                Text(
                    text = playerName,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    placeholder = { Text("0.00", color = Color.White.copy(alpha = 0.3f)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PokerGoldNeon,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                        cursorColor = PokerGoldNeon
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("CANCEL", color = Color.White.copy(alpha = 0.5f), fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { if (amountText.isNotBlank()) onAdd(amountText) },
                        modifier = Modifier.weight(1.5f).height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                    ) {
                        Text("JOIN TABLE", fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}
