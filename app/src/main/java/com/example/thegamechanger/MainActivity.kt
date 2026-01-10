package com.example.thegamechanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.thegamechanger.navigation.AppNavGraph
import com.example.thegamechanger.ui.theme.PokerDTTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokerDTTheme {
                AppNavGraph()
            }
        }
    }
}

