package com.example.thegamechanger.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thegamechanger.ui.theme.AddPersonScreen
import com.example.thegamechanger.ui.theme.IntroScreen
import com.example.thegamechanger.ui.theme.LoginScreen
import com.example.thegamechanger.ui.theme.MainScreen


@Composable
fun AppNavGraph(){
    val navController = rememberNavController()
    NavHost(
        navController=navController,
        startDestination = "intro"
    ){
        composable("intro") {
            IntroScreen(
                onNavigateToLogin = {
                    navController.navigate("login"){
                        popUpTo("intro"){inclusive=true}
                    }
                }
            )
        }
        composable("login"){
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("main") {
            MainScreen(
                onAddPersonClick = {
                    navController.navigate("add_person")
                }
            )
        }

        // 🔥 THIS WAS MISSING
        composable("add_person") {
            AddPersonScreen(
                onBack = { navController.popBackStack() }
            )
        }

    }
}