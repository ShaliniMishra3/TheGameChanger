package com.example.thegamechanger.viewmodel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thegamechanger.ui.theme.PokerGoldNeon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.thegamechanger.R
import com.example.thegamechanger.ui.theme.PokerCrimsonBottom
import com.example.thegamechanger.ui.theme.PokerCrimsonTop

@Composable
fun ChangePasswordScreen(
    onBack: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var tpID by remember { mutableStateOf("") }
    var oldPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PokerCrimsonTop, PokerCrimsonBottom)
                )
            )
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.08f), Color.Transparent),
                        radius = 1200f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { onBack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Back",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_final),
                contentDescription = "App logo",
                modifier = Modifier
                    .height(130.dp)
                    .width(250.dp)
                    .shadow(30.dp, CircleShape)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.Black.copy(alpha = 0.2f),
                        RoundedCornerShape(32.dp)
                    )
                    .border(
                        1.dp,
                        Brush.verticalGradient(
                            listOf(Color.White.copy(alpha = 0.2f), Color.Transparent)
                        ),
                        RoundedCornerShape(32.dp)
                    )
                    .padding(24.dp)
            ) {

                Column {

                    Text(
                        text = "THE GAME CHANGER",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PokerGoldNeon,
                        letterSpacing = 3.sp
                    )
                    Text(
                        text = "Change Password",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email", color = Color.White.copy(alpha = 0.5f)) },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PokerGoldNeon,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                            focusedContainerColor = Color.Black.copy(alpha = 0.2f),
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = PokerGoldNeon,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = oldPassword,
                        onValueChange = { oldPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Old Password", color = Color.White.copy(alpha = 0.5f)) },
                       // visualTransformation = PasswordVisualTransformation(),
                        visualTransformation =
                            if (oldPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon =
                                if (oldPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { oldPasswordVisible = !oldPasswordVisible }) {
                                Icon(icon, contentDescription = null, tint = Color.White.copy(alpha = 0.5f))
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PokerGoldNeon,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                            focusedContainerColor = Color.Black.copy(alpha = 0.2f),
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = PokerGoldNeon,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("New Password", color = Color.White.copy(alpha = 0.5f)) },
                        //visualTransformation = PasswordVisualTransformation(),
                        visualTransformation =
                            if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon =
                                if (newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                            IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                                Icon(icon, contentDescription = null, tint = Color.White.copy(alpha = 0.5f))
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PokerGoldNeon,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                            focusedContainerColor = Color.Black.copy(alpha = 0.2f),
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = PokerGoldNeon,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = tpID,
                        onValueChange = { tpID = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("TpID", color = Color.White.copy(alpha = 0.5f)) },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PokerGoldNeon,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                            focusedContainerColor = Color.Black.copy(alpha = 0.2f),
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = PokerGoldNeon,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .shadow(20.dp, RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = "UPDATE PASSWORD",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }
}



/*@Composable
fun ChangePasswordScreen(
    onBack:()->Unit
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var emailId by remember {mutableStateOf("") }
    var tpID by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value= emailId,
            onValueChange = { oldPassword=it },
            label = { Text ("Email ID")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value=oldPassword,
            onValueChange = {newPassword=it},
            label={Text("Old Password")},
            modifier=Modifier.fillMaxWidth()
        )
        Spacer(modifier= Modifier.height(20.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword=it },
            label = {Text("New Password")},
            modifier= Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value=tpID,
            onValueChange = {tpID=it},
            label={Text("TpID")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick={ },
            modifier= Modifier.fillMaxWidth()
        ){
            Text("Update Password")
        }
    }
}

 */