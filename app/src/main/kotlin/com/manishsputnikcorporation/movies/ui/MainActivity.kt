package com.manishsputnikcorporation.movies.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.manishsputnikcorporation.movies.ui.navigation.AppNavigation
import com.manishsputnikcorporation.movies.ui.theme.MediaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MoviesApp() }
    }
}

@Composable
fun MoviesApp() {
    MediaTheme {
        val navController = rememberNavController()
        AppNavigation(mainViewModel = hiltViewModel(), navController = navController)
    }
}
