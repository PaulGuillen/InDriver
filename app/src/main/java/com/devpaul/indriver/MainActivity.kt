package com.devpaul.indriver

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.indriver.presentation.navigation.graph.root.RootNavGraph
import com.devpaul.indriver.ui.theme.InDriverTheme
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_api_key))
        }
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