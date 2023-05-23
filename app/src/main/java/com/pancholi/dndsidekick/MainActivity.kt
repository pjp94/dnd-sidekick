package com.pancholi.dndsidekick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.pancholi.dndsidekick.navigation.Screen
import com.pancholi.dndsidekick.ui.BottomNavBar
import com.pancholi.dndsidekick.ui.theme.DnDSidekickTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DnDSidekickTheme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavBar(
                        navController = navController,
                        items = listOf(Screen.GrabBag, Screen.BattleTracker)
                    )
                }
            }
        }
    }
}