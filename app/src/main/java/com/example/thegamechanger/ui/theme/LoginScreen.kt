package com.example.thegamechanger.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thegamechanger.viewmodel.LoginViewModel
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.thegamechanger.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton


import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess:()-> Unit
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val navigate by viewModel.navigateToHome.observeAsState(false)
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(navigate) {
        if(navigate){
            onLoginSuccess()
        }
    }
    val focusManager= LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PokerOrangeTop, PokerOrangeBottom)
                )
            )
            .pointerInput(Unit){
                detectTapGestures (
                    onTap={
                        focusManager.clearFocus()
                    }
                )
            }
    ) {

        Column(
        modifier= Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally

      )
    {
            Spacer(modifier=Modifier.height(80.dp))
        Image(
            painter=painterResource(id=R.drawable.logo_final),
            contentDescription = "App logo",
            modifier=Modifier.height(120.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text="Login",
            fontSize=24.sp,
            style= MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(modifier=Modifier.height(24.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email=it},
            modifier = Modifier.fillMaxWidth(),
            label={Text("Email")},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.6f),
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
        Spacer(modifier= Modifier.height(16.dp))
        OutlinedTextField(
            value=password,
            onValueChange = {password=it},
            modifier = Modifier.fillMaxWidth(),
            label={Text("password")},
          //  visualTransformation = PasswordVisualTransformation(),
            visualTransformation = if (passwordVisible)
                androidx.compose.ui.text.input.VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions= KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),

            trailingIcon = {
                val icon = if (passwordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Toggle password visibility",
                        tint = Color.White
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.6f),
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
     )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick={viewModel.onLoginClicked(email,password)},
            modifier= Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape= RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
            ){
            Text(
            text="Login",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
            )
         }

      }

    }
}