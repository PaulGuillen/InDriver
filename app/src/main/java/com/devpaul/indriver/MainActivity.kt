package com.devpaul.indriver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.indriver.presentation.navigation.graph.root.RootNavGraph
import com.devpaul.indriver.ui.theme.InDriverTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InDriverTheme {
                navHostController = rememberNavController()
                RootNavGraph(navHostController = navHostController)
            }
        }
        Timber.plant(Timber.DebugTree())
    }
}