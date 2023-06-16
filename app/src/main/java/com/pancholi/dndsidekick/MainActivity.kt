package com.pancholi.dndsidekick

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.pancholi.dndsidekick.navigation.Screen
import com.pancholi.dndsidekick.ui.theme.DnDSidekickTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

            DnDSidekickTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SidekickApp(
                        windowSizeClass = windowSizeClass,
                        items = listOf(
                            Screen.GRAB_BAG,
                            Screen.BATTLE_TRACKER
                        )
                    )
                }
            }
        }
    }
}