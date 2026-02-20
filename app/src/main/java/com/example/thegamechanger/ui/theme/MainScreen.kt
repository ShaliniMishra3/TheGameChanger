package com.example.thegamechanger.ui.theme



import android.view.Surface
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thegamechanger.UiState
import com.example.thegamechanger.viewmodel.GameViewModel
import com.example.thegamechanger.viewmodel.Player





val PokerBloodRedBright = Color(0xFFFF0000) // Vibrant Red for glow
val PokerGlass = Color(0xFF151515).copy(alpha = 0.9f)
val PokerGoldNeon = Color(0xFFFFD700)
val PokerGoldDark = Color(0xFFB8860B)

// --- INTENSE BLOOD RED PALETTE ---

val PokerCrimsonTop = Color(0xFFE30B0B)    // Rich Blood Red
val PokerCrimsonBottom = Color(0xFFC40505) // Deep Bruised Red (Replaces Black)
  // The "Loud" Accent
val PokerGold = Color(0xFFFFD700)
val PokerCardSurface = Color(0xFF420000).copy(alpha = 0.6f) // Dark Red Glass
val PokerMint = Color(0xFF00FFC8)
val PokerDeepBlood = Color(0xFF660000)   // Darker base
val PokerVibrantRed = Color(0xFFFF0000)  // Loudest Red
val PokerGlassDark = Color(0xFF0D0D0D).copy(alpha = 0.95f) // Less transparent for contrast

// New Color for Dialog Surface (Deep Maroon with Transparency)
val PokerDialogGlass = Color(0xFF420000).copy(alpha = 0.92f)

@Composable
fun MainScreen(
    viewModel: GameViewModel,
    onAddPersonClick: () -> Unit,
    onBack: () -> Unit
) {
    val addState by viewModel.addPlayerState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(addState) {
        if (addState is UiState.Success) {
            Toast.makeText(
                context,
                (addState as UiState.Success).data.Msg,
                Toast.LENGTH_LONG
            ).show()
            viewModel.clearAddPlayerState()
        }
    }
    var gameStarted by remember { mutableStateOf(false) }
    var isSettling by remember { mutableStateOf(false) }  // Controls "FINISH" visibility
    var gameCompleted by remember { mutableStateOf(false) }
   val players = viewModel.players
    var winAmount by remember { mutableStateOf("") }
    var showExitDialog by remember { mutableStateOf(false) }
    var selectedPlayer by remember { mutableStateOf<Player?>(null) }
    val dealerName by viewModel.dealerName
    val tableName by viewModel.tableName
    LaunchedEffect(Unit) {
        viewModel.fetchPlayerOnTable(dealerId = 2) // dynamic id
    }
    BackHandler {
        onBack()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PokerCrimsonTop, PokerCrimsonBottom)
                )

            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.05f), Color.Transparent),
                       // colors = listOf(PokerVibrantRed.copy(alpha = 0.2f), Color.Transparent),
                        radius = 800f
                    )
                )

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // ---- LUXURY HEADER ----

// ---- PREMIUM HEADER CARD ----
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(25.dp, RoundedCornerShape(32.dp)),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2A0000) // Deep rich maroon (less transparent)
                ),
                border = BorderStroke(
                    1.dp,
                    Brush.horizontalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.25f),
                            PokerGoldNeon.copy(alpha = 0.4f),
                            Color.Transparent
                        )
                    )
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp, vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .background(PokerGoldNeon, CircleShape)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "LIVE TABLE",
                                fontSize = 11.sp,
                                letterSpacing = 2.sp,
                                color = Color.White.copy(alpha = 0.6f),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = dealerName,
                            fontSize = 24.sp,     // smaller = more elegant
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = tableName,
                            fontSize = 14.sp,
                            color = PokerGoldNeon,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Premium Gold Circle Button
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .shadow(12.dp, CircleShape)
                            .background(
                                Brush.verticalGradient(
                                    listOf(PokerGoldNeon, PokerGoldDark)
                                ),
                                CircleShape
                            )
                            .clickable { onAddPersonClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            // ---- THE GLASS TABLE ----
            if (players.size > 1) {
                PremiumTable(
                    players = players,
                    onExitClick = { player ->
                        selectedPlayer = player
                        showExitDialog = true
                    }
                )
            } else {
                EmptyTablePlaceholder()
            }

            Spacer(modifier = Modifier.height(35.dp))

            // ---- ACTION AREA ----
            ActionButtons(
                gameStarted = gameStarted,
                isSettling = isSettling,
                onStart = { gameStarted = true
                          isSettling=false},
                onComplete = { gameCompleted = true }
            )

            if (gameCompleted) {
                EarningsInput(
                    winAmount = winAmount,
                    onAmountChange = { winAmount = it },
                    onSubmit = {
                        val amount = winAmount.toIntOrNull() ?: 0
                        viewModel.updateDealerCommission(amount)
                        winAmount = ""
                        gameCompleted = false
                        gameStarted = false
                    }
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }

    if (showExitDialog && selectedPlayer != null) {
        ExitDialog(
            player = selectedPlayer!!,
            onDismiss = { showExitDialog = false },
            onSubmit = { finalAmount ->

                viewModel.addPlayerToTable(
                    dId = viewModel.dealerId.value,
                    pId = selectedPlayer!!.pId,   // ⚠ make sure Player has pId
                    tbId = viewModel.tableId.value,
                    coin = finalAmount.toDouble(),
                    isAdd = 0   // 🔥 EXIT CASE
                )

                showExitDialog = false
            }
        )

    }
}

@Composable
fun PremiumTable(players: List<Player>, onExitClick: (Player) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(20.dp, RoundedCornerShape(30.dp), spotColor = Color.Black)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    listOf(Color.White.copy(alpha = 0.3f), Color.Transparent, PokerGold.copy(alpha = 0.2f))
                ),
                shape = RoundedCornerShape(30.dp)
            ),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = PokerGlass),
        elevation = CardDefaults.cardElevation(0.dp) // Use shadow modifier instead for better control
    ) {
        Column(modifier = Modifier.padding(bottom = 10.dp)) {
            // --- HEADER WITH UNDERLINE ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.05f))
                    .padding(vertical = 18.dp, horizontal = 24.dp)
            ) {
                Text("PLAYER", Modifier.weight(1.2f), color = PokerGold.copy(alpha = 0.7f), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.5.sp)
                Text("BANKROLL", Modifier.weight(1f), color = PokerGold.copy(alpha = 0.7f), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
                Text("ACTION", Modifier.weight(0.8f), color = PokerGold.copy(alpha = 0.7f), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.End)
            }

            players.forEachIndexed { index, player ->
                PremiumTableRow(player, index == players.lastIndex) { onExitClick(player) }
            }
        }
    }
}



@Composable
fun PremiumTableRow(player: Player, isLast: Boolean, onExit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Player Name in clean White
        Text(
            text = player.name,
            modifier = Modifier.weight(1.2f),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )

        // Amount in Glow Gold
        Text(
            text = "₹${player.amount}",
            modifier = Modifier.weight(1f),
            color = PokerGoldNeon,
            fontWeight = FontWeight.Black,
            fontSize = 19.sp,
            textAlign = TextAlign.Center
        )

        // Exit button styled as a "Red Button" or clean text
        Box(
            modifier = Modifier
                .weight(0.8f)
                .clickable { onExit() },
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "EXIT",
                color = PokerVibrantRed,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .border(1.dp, PokerVibrantRed.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
    if (!isLast) {
        HorizontalDivider(
            color = Color.White.copy(alpha = 0.05f),
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Composable
fun ActionButtons(gameStarted: Boolean,
                  isSettling:Boolean,
                  onStart: () -> Unit, onComplete: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(
            onClick = onStart,
            enabled = !gameStarted && !isSettling ,
            modifier = Modifier.weight(1f).height(65.dp),
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (gameStarted) Color(0xFF1A1A1A) else Color.White,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 12.dp)
        ) {
            Text(if (gameStarted) "IN PLAY" else "DEAL", fontWeight = FontWeight.Black, fontSize = 16.sp)
        }

        Button(
            onClick = onComplete,
            enabled = gameStarted && !isSettling,
            modifier = Modifier.weight(1f).height(65.dp),
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PokerMint,
                contentColor = Color.Black,
                disabledContainerColor = Color(0xFFFFFF00), // Dark Mint when disabled
                disabledContentColor = Color.DarkGray
            ),

            elevation = ButtonDefaults.buttonElevation(defaultElevation = 12.dp)
        ) {
            Text("FINISH", fontWeight = FontWeight.Black, color = Color.Black, fontSize = 16.sp)
        }
    }
}


@Composable
fun EarningsInput(winAmount: String, onAmountChange: (String) -> Unit, onSubmit: () -> Unit) {
    Spacer(modifier = Modifier.height(24.dp))

    // Glass Card Container to make the input visible against the red
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f)), // Dark tint
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)) // Subtle outline
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "SETTLE POT",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PokerGoldNeon,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = winAmount,
                onValueChange = onAmountChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter Total Winning Pot", color = Color.White.copy(alpha = 0.3f)) },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PokerGoldNeon,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                    focusedContainerColor = Color.Black.copy(alpha = 0.2f),
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = PokerGoldNeon
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(15.dp, RoundedCornerShape(16.dp), spotColor = Color.Black),
                // Pure white button is the "loudest" contrast against red background
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("CONFIRM POT", fontWeight = FontWeight.Black, fontSize = 16.sp)
            }
        }
    }
}
@Composable
fun EmptyTablePlaceholder() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("THE TABLE IS DARK", color = Color.DarkGray, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
        Text("Add players to light it up", color = Color.Gray, fontSize = 13.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun ExitDialog(player: Player,
               onDismiss: () -> Unit,
               onSubmit: (Int) -> Unit) {

    var remaining by remember { mutableStateOf(player.amount.toString()) }



    BasicAlertDialog(onDismissRequest = onDismiss) {

        Card(

            shape = RoundedCornerShape(32.dp),

            // Changed from Black to a Deep Crimson Glass

            colors = CardDefaults.cardColors(containerColor = PokerDialogGlass),

            // Vibrant Gold/White border to make it pop against the red background

            border = BorderStroke(

                width = 2.dp,

                brush = Brush.verticalGradient(

                    listOf(Color.White.copy(alpha = 0.5f), Color.Transparent)

                )

            ),

            modifier = Modifier

                .fillMaxWidth()

                .padding(horizontal = 10.dp)

                // Red shadow to blend with the background glow

                .shadow(30.dp, RoundedCornerShape(32.dp), spotColor = Color.Red)

        ) {

            Column(

                modifier = Modifier.padding(30.dp),

                horizontalAlignment = Alignment.Start

            ) {

                Text(

                    text = "SETTLEMENT",

                    fontSize = 11.sp,

                    fontWeight = FontWeight.ExtraBold,

                    color = PokerGoldNeon,

                    letterSpacing = 3.sp

                )



                Text(

                    text = player.name,

                    fontSize = 34.sp,

                    fontWeight = FontWeight.Black,

                    color = Color.White,

                    modifier = Modifier.padding(vertical = 4.dp)

                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(

                    value = remaining,

                    onValueChange = { remaining = it },

                    label = { Text("Final Chip Count", color = Color.White.copy(alpha = 0.6f)) },

                    textStyle = androidx.compose.ui.text.TextStyle(

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color.White

                    ),

                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(

                        focusedBorderColor = Color.White,

                        unfocusedBorderColor = Color.White.copy(alpha = 0.2f),

                        focusedContainerColor = Color.Black.copy(alpha = 0.2f),

                        unfocusedContainerColor = Color.Black.copy(alpha = 0.1f)

                    ),

                    modifier = Modifier.fillMaxWidth()

                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.spacedBy(12.dp),

                    verticalAlignment = Alignment.CenterVertically

                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)

                    ) {
                        Text("CANCEL", color = Color.White.copy(alpha = 0.7f), fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { onSubmit(remaining.toIntOrNull() ?: 0) },
                        modifier = Modifier
                            .weight(1.8f)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFFC40505)

                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)

                    ) {
                        Text("CONFIRM EXIT", fontWeight = FontWeight.Black, fontSize = 15.sp)
                    }
                }
            }
        }
    }

}







