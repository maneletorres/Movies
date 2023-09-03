package com.manishsputnikcorporation.movies.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.ui.navigation.SignInScreen
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager

@Composable
fun SignInScreen(analyticsManager: AnalyticsManager, onNavigation: () -> Unit = {}) {
    analyticsManager.trackScreen(screenName = SignInScreen.route)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val emailText = stringResource(id = R.string.sign_in_email)
        val passwordText = stringResource(id = R.string.sign_in_password)

        SignInTextField(
            value = email,
            label = emailText,
            placeholder = emailText,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
        ) {
            email = it
        }
        Spacer(modifier = Modifier.height(8.dp))
        SignInTextField(
            value = password,
            label = passwordText,
            placeholder = passwordText,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) }
        ) {
            password = it
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.padding(horizontal = 40.dp)) {
            Button(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(50.dp), onClick = { /*TODO*/}) { Text(text = "Sign up") }
        }
        Spacer(modifier = Modifier.height(40.dp))
        ClickableText(
            text = AnnotatedString(text = "Do you have an account yet?"),
            onClick = { onNavigation.invoke() },
            style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Default, textDecoration = TextDecoration.Underline)
        )
    }
}

@Composable
fun SignInTextField(
    value: String,
    label: String,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun LogoutDialog(onConfirmLogOut: () -> Unit = {}, onDismiss: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Close session") },
        text = { Text(text = "Are you sure you want to close session?") },
        confirmButton = { Button(onClick = onConfirmLogOut) { Text(text = "Accept") } },
        dismissButton = { Button(onClick = onDismiss) { Text(text = "Cancel") } }
    )
}

// region Preview
@UiModePreviews
@Composable
fun SignInScreenPreview() {
    MoviesTheme {
        Surface {
            val context = LocalContext.current
            SignInScreen(analyticsManager = AnalyticsManager(context))
        }
    }
}

@UiModePreviews
@Composable
fun SignInTextFieldPreview() {
    MoviesTheme {
        Surface {
            var password by remember { mutableStateOf("") }
            SignInTextField(
                value = password,
                label = stringResource(id = R.string.sign_in_password),
                placeholder = stringResource(id = R.string.sign_in_email),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) }
            ) {
                password = it
            }
        }
    }
}
// endregion
