package com.manishsputnikcorporation.movies.ui.screens.splash

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.ui.navigation.SplashScreen
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(analyticsManager: AnalyticsManager, onNavigation: () -> Unit = {}) {
    LaunchedEffect(key1 = true) {
        // TODO: to implement resource load
        delay(3000)
        onNavigation()
    }

    analyticsManager.trackScreen(screenName = SplashScreen.route)
    Surface { MoviesLogo(textRes = R.string.welcome_message, modifier = Modifier.fillMaxSize()) }
}

@Composable
fun MoviesLogo(@StringRes textRes: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.logo_description),
            modifier = Modifier.size(150.dp, 150.dp).clip(CircleShape).border(2.dp, Color.Black, CircleShape).background(Color.Gray),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(textRes), fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}

@UiModePreviews
@Composable
fun SplashScreenPreview() {
    val context = LocalContext.current
    MoviesTheme { SplashScreen(AnalyticsManager(context)) }
}
