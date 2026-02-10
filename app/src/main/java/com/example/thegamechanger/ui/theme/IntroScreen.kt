package com.example.thegamechanger.ui.theme

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thegamechanger.R
import com.example.thegamechanger.viewmodel.IntroViewModel


@Composable
fun IntroScreen(
    viewModel: IntroViewModel = viewModel(),
    onNavigateToLogin: () -> Unit
) {
    val navigate by viewModel.navigateToLogin.observeAsState(false)

    LaunchedEffect(navigate) {
        if (navigate) {
            onNavigateToLogin()
        }
    }

    // --- ANIMATION ENGINE (Matches Login Screen) ---
    val infiniteTransition = rememberInfiniteTransition(label = "Pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "PulseScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.0f to Color(0xFFE30B0B), // PokerCrimsonTop
                    0.6f to Color(0xFF660000), // PokerDeepBlood
                    1.0f to Color(0xFF1A0000)  // Near Black
                )
            )
    ) {
        // --- LUXURY AMBIENT GLOW ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.07f), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(500f, 1000f),
                        radius = 1500f
                    )
                )
        )
        Image(
            painter = painterResource(id = R.drawable.logo_final),
            contentDescription = "Poker Logo",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
                .scale(pulseScale) // Subtle breathing effect
                .shadow(
                    elevation = 40.dp,
                    shape = CircleShape,
                    spotColor = Color.Red.copy(alpha = 0.5f)
                ),
            contentScale = androidx.compose.ui.layout.ContentScale.Fit
        )

        // 🔹 BOTTOM SECTION
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Experience High Stakes",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.5f),
                letterSpacing = 2.sp
            )
            Text(
                text = "ENJOY PLAYING WITH\nPOKER DT",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                lineHeight = 32.sp,
                letterSpacing = (-1).sp
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = { viewModel.onLetStartClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .shadow(30.dp, RoundedCornerShape(20.dp), spotColor = Color.Black),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Pure White for max contrast
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 12.dp)
            ) {
                Text(
                    text = "GET STARTED",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}
