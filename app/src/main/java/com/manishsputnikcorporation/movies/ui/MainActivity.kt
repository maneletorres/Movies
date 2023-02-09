package com.manishsputnikcorporation.movies.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.manishsputnikcorporation.movies.ui.navigation.AppNavigation
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent { MoviesApp() }
  }
}

@Composable
fun MoviesApp() {
  MoviesTheme {
    val navController = rememberNavController()
    AppNavigation(navController)
  }
}
