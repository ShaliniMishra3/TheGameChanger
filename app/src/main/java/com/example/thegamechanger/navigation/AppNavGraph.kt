package com.example.thegamechanger.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.thegamechanger.ui.theme.AddPersonScreen
import com.example.thegamechanger.ui.theme.IntroScreen
import com.example.thegamechanger.ui.theme.LoginScreen
import com.example.thegamechanger.ui.theme.MainScreen
import com.example.thegamechanger.viewmodel.GameViewModel
import com.example.thegamechanger.ui.theme.ManagerDashboard

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

                onLoginSuccess = { dealerId, dealerName, isDealer ->

                    if (isDealer) {

                        navController.navigate("main/$dealerId/$dealerName") {
                            popUpTo("login") { inclusive = true }
                        }

                    } else {

                        navController.navigate("manager_dashboard") {
                            popUpTo("login") { inclusive = true }
                        }

                    }

                }

            )


        }
        composable("manager_dashboard") {
            // 🔥 Inject the Hilt ViewModel here or let the screen do it
            ManagerDashboard(
                viewModel = hiltViewModel(),
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("manager_dashboard") { inclusive = true }
                    }
                }
            )
        }
       /* composable("main") {
            MainScreen(
                viewModel = gameViewModel,
                onAddPersonClick = { navController.navigate("add_person") },
                onBack = { navController.navigate("login"){
                    popUpTo("main"){inclusive=true}
                } }
            )
        }*/
        composable(
            "main/{dealerId}/{dealerName}",
            arguments = listOf(
                navArgument("dealerId") { type = NavType.IntType },
                navArgument("dealerName") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val dealerId = backStackEntry.arguments?.getInt("dealerId") ?: 0
            val dealerName = backStackEntry.arguments?.getString("dealerName") ?: ""

            MainScreen(
                dealerId = dealerId,
                dealerName = dealerName,
                viewModel = gameViewModel,
                onAddPersonClick = { navController.navigate("add_person") },
                onBack = {
                    navController.navigate("login") {
                        popUpTo("main/$dealerId/$dealerName") { inclusive = true }
                    }
                }
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






























































