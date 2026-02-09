package com.example.thegamechanger.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thegamechanger.ui.theme.AddPersonScreen
import com.example.thegamechanger.ui.theme.IntroScreen
import com.example.thegamechanger.ui.theme.LoginScreen
import com.example.thegamechanger.ui.theme.MainScreen
import com.example.thegamechanger.viewmodel.GameViewModel


@Composable
fun AppNavGraph(){
    val gameViewModel: GameViewModel= viewModel()
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
           /* MainScreen(
                onAddPersonClick = {
                    navController.navigate("add_person")
                },
                onBack = {
                    navController.navigate("intro"){
                        popUpTo("main"){inclusive=true}
                    }
                }
            )
        }
        composable("add_person") {
            AddPersonScreen(
                onBack = { navController.popBackStack() }
            )

            */
            MainScreen(
                viewModel = gameViewModel,
                onAddPersonClick = { navController.navigate("add_person") },
                onBack = { /* handle back */ }
            )
        }
        composable("add_person") {
            AddPersonScreen(
                viewModel = gameViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}






























































