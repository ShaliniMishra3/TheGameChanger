package com.example.thegamechanger.ui.theme



import android.view.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thegamechanger.viewmodel.GameViewModel
import com.example.thegamechanger.viewmodel.Player




// --- REFINED BLOOD RED PALETTE ---
val PokerNightBlack = Color(0xFF050505)
val PokerBloodRed = Color(0xFF8B0000) // Deep Blood Red
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
    var gameStarted by remember { mutableStateOf(false) }
    var gameCompleted by remember { mutableStateOf(false) }
    val players = viewModel.players
    var winAmount by remember { mutableStateOf("") }
    var showExitDialog by remember { mutableStateOf(false) }
    var selectedPlayer by remember { mutableStateOf<Player?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PokerCrimsonTop, PokerCrimsonBottom)
                )
           /* .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PokerDeepBlood,
                       // Color(0xFF4A0404), // Deep Blood Red Top
                       // Color(0xFF2A0505), // Mid Blood Red
                        Color(0xFF220000),
                        PokerNightBlack     // Fades into Black at the bottom
                    )
                )

            */
            )
    ) {
        // --- AMBIENT BLOOD RED GLOW (Top Left) ---
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
               /* .size(400.dp)
                .offset(x = (-150).dp, y = (-150).dp)
                .background(PokerBloodRed.copy(alpha = 0.25f), CircleShape)
                .blur(100.dp)

                */
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // ---- LUXURY HEADER ----
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = PokerBloodRedBright,
                            shape = CircleShape,
                            modifier = Modifier.size(8.dp).shadow(10.dp, CircleShape, spotColor = PokerBloodRedBright)
                        ) {}
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "LIVE TABLE",
                            fontSize = 11.sp,
                            letterSpacing = 2.5.sp,
                            color = Color.White.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Roopesh",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = (-1).sp
                    )
                }

                // Add Player Button - Gold & Red Shadow
                IconButton(
                    onClick = onAddPersonClick,
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            Brush.verticalGradient(listOf(PokerGoldNeon, PokerGoldDark)),
                            RoundedCornerShape(18.dp)
                        )
                        .shadow(15.dp, RoundedCornerShape(18.dp), spotColor = PokerBloodRedBright)
                ) {
                    Text("+", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Black)
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
                onStart = { gameStarted = true },
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
            onSubmit = { viewModel.removePlayer(selectedPlayer!!); showExitDialog = false }
        )
    }
}

@Composable
fun PremiumTable(players: List<Player>, onExitClick: (Player) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.15f), Color.Transparent)),
                shape = RoundedCornerShape(30.dp)
            ),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = PokerGlass),
        elevation = CardDefaults.cardElevation(25.dp)
    ) {
        Column(modifier = Modifier.padding(bottom = 10.dp)) {
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.03f))
                    .padding(vertical = 20.dp, horizontal = 24.dp)
            ) {
                Text("PLAYER", Modifier.weight(1.2f), color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.2.sp)
                Text("BANKROLL", Modifier.weight(1f), color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
                Text("STATUS", Modifier.weight(0.8f), color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.End)
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
            .padding(horizontal = 24.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(player.name, Modifier.weight(1.2f), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Text(
            "₹${player.amount}",
            Modifier.weight(1f),
            color = PokerGoldNeon,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        Text(
            "EXIT",
            Modifier.weight(0.8f).clickable { onExit() },
            color = PokerBloodRedBright,
            fontWeight = FontWeight.Black,
            fontSize = 13.sp,
            textAlign = TextAlign.End
        )
    }
    if (!isLast) HorizontalDivider(color = Color.White.copy(alpha = 0.07f), modifier = Modifier.padding(horizontal = 24.dp))
}

@Composable
fun ActionButtons(gameStarted: Boolean, onStart: () -> Unit, onComplete: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(
            onClick = onStart,
            enabled = !gameStarted,
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
            modifier = Modifier.weight(1f).height(65.dp),
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PokerMint),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 12.dp)
        ) {
            Text("FINISH", fontWeight = FontWeight.Black, color = Color.Black, fontSize = 16.sp)
        }
    }
}

/*
@Composable
fun EarningsInput(winAmount: String, onAmountChange: (String) -> Unit, onSubmit: () -> Unit) {
    Spacer(modifier = Modifier.height(24.dp))
    OutlinedTextField(
        value = winAmount,
        onValueChange = onAmountChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Enter Total Winning Pot", color = PokerBloodRedBright) },
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PokerBloodRedBright,
            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
            focusedTextColor = Color.White,
            cursorColor = PokerBloodRedBright
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = onSubmit,
        modifier = Modifier.fillMaxWidth().height(60.dp).shadow(20.dp, RoundedCornerShape(20.dp), spotColor = PokerBloodRedBright),
        colors = ButtonDefaults.buttonColors(containerColor = PokerBloodRedBright),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text("CONFIRM POT", color = Color.White, fontWeight = FontWeight.Black)
    }
}


 */
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

fun ExitDialog(player: Player, onDismiss: () -> Unit, onSubmit: (Int) -> Unit) {

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







