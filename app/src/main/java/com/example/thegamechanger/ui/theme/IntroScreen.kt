package com.example.thegamechanger.ui.theme

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

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thegamechanger.R
import com.example.thegamechanger.viewmodel.IntroViewModel

@Composable
fun IntroScreen (
    viewModel: IntroViewModel= viewModel(),
    onNavigateToLogin: () -> Unit
)   {
    val navigate by viewModel.navigateToLogin.observeAsState(false)

    LaunchedEffect(navigate) {
        if(navigate){
            onNavigateToLogin()
        }
    }
    /*
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(PokerOrangeTop,PokerOrangeBottom)
                )
            )
    ){
        Column(
            modifier=Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.weight(1f))
            //Title
            Text(
                text="Poker DT",
                fontSize=24.sp,
                fontWeight= FontWeight.Bold
            )
            //Spacer(modifier=Modifier.height(170.dp))
            Spacer(modifier = Modifier.weight(0.2f))
            //Logo
            Image(
                painter= painterResource(id=R.drawable.logo_final),
                contentDescription="Poker Logo",
                //modifier = Modifier.size(160.dp)
                modifier = Modifier
                    .fillMaxWidth(0.8f)   // 60% of screen width
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally),
                contentScale = androidx.compose.ui.layout.ContentScale.Fit
            )
            Spacer(modifier = Modifier.weight(0.5f))
            // Spacer(modifier= Modifier.height(190.dp))
            Text(
                text="Enjoy Playing with\nPoker DT",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color=androidx.compose.ui.graphics.Color.White
            )

            //Button
            Button(
                onClick={viewModel.onLetStartClicked()},
                modifier= Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape= RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Black
                )

            ){
                Text(
                    text="Let's Start",
                    color=androidx.compose.ui.graphics.Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

     */
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(PokerOrangeTop, PokerOrangeBottom)
                )
            )
    ) {
        // 🔹 TOP TITLE
        Text(
            text = "Poker DT",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
        )
        // 🔹 CENTER LOGO (PERFECTLY CENTERED)
        Image(
            painter = painterResource(id = R.drawable.logo_final),
            contentDescription = "Poker Logo",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.75f)
                .aspectRatio(1f),
            contentScale = androidx.compose.ui.layout.ContentScale.Fit
        )
        // 🔹BOTTOM SECTION
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enjoy Playing with\nPoker DT",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onLetStartClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Black
                )
            ) {
                Text(
                    text = "Let's Start",
                    color = androidx.compose.ui.graphics.Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

}