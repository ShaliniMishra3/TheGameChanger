package com.example.thegamechanger.ui.theme

import android.widget.Space
import android.widget.TableRow
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thegamechanger.viewmodel.Player

@Composable
fun MainScreen() {
    //---- STATE ----
    var gameStarted by remember { mutableStateOf(false) }
    var winAmount by remember { mutableStateOf("") }
    val SubmitOrangeStart = Color(0xFFEF8F1F) // rich dark orange
    val SubmitOrangeEnd = Color(0xFFD97706)   // deep burnt orange

    val players = remember {
        mutableStateListOf(
            Player("Ram", 5000),
            Player("Shyam", 10000),
            Player("Rahul", 2500),
            Player("Neha", 8000),
            Player("Dealer", 15000)

        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PokerOrangeTop,
                        PokerOrangeBottom
                    )
                )
            )
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        /*
        Text(
            text = "Dealer:Roopesh",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PokerBlack
        )

         */
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Dealer Name (Left)
            Text(
                text = "Dealer: Roopesh",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = PokerBlack,
                modifier = Modifier.weight(1f)
            )

            // Plus Button (Right)
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        // TODO: plus button action
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }



        Spacer(modifier = Modifier.height(20.dp))

        // ---------- PREMIUM TABLE ----------
        PremiumTable(players)

        Spacer(modifier = Modifier.height(28.dp))

        // ---------- BUTTONS ----------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { gameStarted = true },
                enabled = !gameStarted,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PokerBlack,
                    disabledContainerColor = PokerGray,
                    contentColor = PokerWhite,
                    disabledContentColor = PokerWhite
                )
            ) {
                Text(
                    if (gameStarted) "Game Started" else "Game Start",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = { },
                //enabled = !gameStarted,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PokerDark,
                    contentColor = PokerWhite
                )
            ) {
                Text("Game Complete", fontSize = 16.sp)
            }
        }
        // ---------- WIN AMOUNT ----------
        if (gameStarted) {
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "Enter Win Amount",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))

            val isValidAmount = winAmount.isNotEmpty() && winAmount.toIntOrNull() != null

            OutlinedTextField(
                value = winAmount,
                onValueChange = { winAmount = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter amount", color = PokerGray) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = PokerWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    focusedIndicatorColor = PokerWhite,
                    unfocusedIndicatorColor = PokerGray,
                    cursorColor = PokerWhite,
                    focusedTextColor = PokerWhite,
                    unfocusedTextColor = PokerWhite
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    SubmitOrangeStart,
                                    SubmitOrangeEnd
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black.copy(alpha = 0.25f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable(
                            enabled = winAmount.isNotEmpty()
                        ) {
                            val enteredAmount = winAmount.toIntOrNull() ?: return@clickable
                            val dealerCommission = (enteredAmount * 0.10).toInt()
                            val dealerIndex = players.indexOfFirst { it.name == "Dealer" }
                            if (dealerIndex != -1) {
                                val dealer = players[dealerIndex]
                                players[dealerIndex] = dealer.copy(
                                    amount = dealer.amount + dealerCommission
                                )
                            }
                            winAmount = ""
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }
            }


        }
    }
}

/* --------------------------------------------------
   PREMIUM TABLE UI
-------------------------------------------------- */


@Composable
fun PremiumTable(players: List<Player>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // ---- ORANGE TABLE HEADER (FIXED) ----
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFEA8A1A), // dark orange
                                Color(0xFFD97706)  // burnt orange
                            )
                        )
                    )
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Player",
                        color = PokerBlack,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1.2f), // aligns with Name column
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start

                    )
                    Text(
                        text = "Token",
                        color = PokerBlack,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f) ,// aligns with Amount column
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center

                    )
                    Text(
                        text = "Action",
                        color = PokerBlack,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f) , // aligns with Exit column
                        textAlign = androidx.compose.ui.text.style.TextAlign.End

                    )
                }
            }
            // ---- TABLE ROWS ----
            players.forEachIndexed { index, player ->
                PremiumTableRow(
                    name = player.name,
                    amount = "₹${player.amount}",
                    isLast = index == players.lastIndex
                )
            }
        }
    }
}
@Composable
fun PremiumTableRow(
    name: String,
    amount: String,
    isLast: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            // Column 1: Name
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Start

            )

            // Column 2: Amount
            Text(
                text = amount,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5),
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center

            )

            // Column 3: Exit
            Text(
                text = "Exit",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.End

            )
        }

        if (!isLast) {
            Divider(
                color = Color(0xFFE0E0E0),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
