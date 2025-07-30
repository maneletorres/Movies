package com.manishsputnikcorporation.movies.ui.screens.auth.login

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.ui.navigation.LoginScreen
import com.manishsputnikcorporation.movies.ui.navigation.MoviesHome
import com.manishsputnikcorporation.movies.ui.navigation.RecoverPasswordScreen
import com.manishsputnikcorporation.movies.ui.screens.splash.MoviesLogo
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import com.manishsputnikcorporation.movies.utils.manager.AuthManager
import com.manishsputnikcorporation.movies.utils.manager.AuthRes
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, onNavigation: (String, String) -> Unit = { _, _ -> }) {
    loginViewModel.trackScreen()

    val scope = rememberCoroutineScope()
    LoginScreenContent(
        onIncognitoSignUp = { scope.launch { loginViewModel.incognitoSignIn(onNavigation = onNavigation) } },
        onEmailSignUpClick = { loginViewModel.navigateToSignUpScreen(onNavigation) },
        onForgottenPasswordClick = { loginViewModel.navigateToPasswordRecoveryScreen(onNavigation) },
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    onIncognitoSignUp: () -> Unit = {},
    onGoogleSignUpClick: () -> Unit = {},
    onEmailSignUpClick: () -> Unit = {},
    onForgottenPasswordClick: () -> Unit = {},
) {
    Surface {
        Column(modifier = modifier) {
            MoviesLogo(textRes = R.string.app_name, modifier = Modifier.fillMaxWidth().weight(1f))
            BottomContent(
                onIncognitoSignUpClick = { onIncognitoSignUp() },
                onGoogleSignUpClick = { onGoogleSignUpClick() },
                onEmailSignUpClick = { onEmailSignUpClick() },
                onForgottenPasswordClick = { onForgottenPasswordClick() },
            )
        }
    }
}

@Composable
fun BottomContent(
    modifier: Modifier = Modifier,
    onIncognitoSignUpClick: () -> Unit = {},
    onGoogleSignUpClick: () -> Unit = {},
    onEmailSignUpClick: () -> Unit = {},
    onForgottenPasswordClick: () -> Unit = {},
) {
    Column(modifier = modifier) {
        ProviderButtonList(
            onIncognitoSignInClick = onIncognitoSignUpClick,
            onGoogleSignInClick = onGoogleSignUpClick,
            onEmailSignInClick = onEmailSignUpClick,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = AnnotatedString(text = stringResource(id = R.string.login_forgotten_password)),
            modifier = Modifier.align(Alignment.CenterHorizontally).clickable(onClick = { onForgottenPasswordClick() }),
            style =
                TextStyle(
                    // TODO: to improve!
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline,
                ),
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProviderButtonList(
    modifier: Modifier = Modifier,
    onIncognitoSignInClick: () -> Unit = {},
    onGoogleSignInClick: () -> Unit = {},
    onEmailSignInClick: () -> Unit = {},
) {
    Column(modifier = modifier, verticalArrangement = spacedBy(8.dp)) {
        ProviderButton(
            textRes = R.string.login_incognito_sign_in,
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_incognito), contentDescription = null) },
        ) {
            onIncognitoSignInClick()
        }
        ProviderButton(
            textRes = R.string.login_google_sign_in,
            leadingIcon = { Image(painter = painterResource(id = R.drawable.ic_logo_google), contentDescription = null) },
        ) {
            onGoogleSignInClick()
        }
        ProviderButton(
            textRes = R.string.login_email_sign_in,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
        ) {
            onEmailSignInClick()
        }
    }
}

@Composable
fun ProviderButton(@StringRes textRes: Int, leadingIcon: @Composable (() -> Unit) = {}, onClick: () -> Unit = {}) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White),
    ) {
        leadingIcon.invoke()
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = textRes))
    }
}

// region Helper methods
private suspend fun incognitoSignIn(authManager: AuthManager, analyticsManager: AnalyticsManager, onNavigation: (String, String) -> Unit) {
    when (val result = authManager.signInAnonymously()) {
        is AuthRes.Success -> {
            analyticsManager.trackButtonClick("incognito_sign_in")
            onNavigation.invoke(MoviesHome.route, LoginScreen.route)
        }
        is AuthRes.Error -> {
            analyticsManager.trackError(result.errorMessage)
        }
    }
}

private fun onForgottenPasswordClick(analyticsManager: AnalyticsManager, onNavigation: (String, String) -> Unit = { _, _ -> }) {
    analyticsManager.trackButtonClick("forgotten_password")
    onNavigation.invoke(RecoverPasswordScreen.route, LoginScreen.route)
}

// endregion

// region Preview
@UiModePreviews
@Composable
fun LoginScreenContentPreview() {
    MoviesTheme { Surface { LoginScreenContent() } }
}

@Preview
@Composable
fun BottomContentPreview() {
    MoviesTheme { Surface { BottomContent() } }
}
// endregion
