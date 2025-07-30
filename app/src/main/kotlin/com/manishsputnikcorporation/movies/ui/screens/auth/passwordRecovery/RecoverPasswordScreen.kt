package com.manishsputnikcorporation.movies.ui.screens.auth.passwordRecovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews

@Composable
fun RecoverPasswordScreen() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        var email by remember { mutableStateOf("") }

        Text(text = "Forgot your password", style = TextStyle(fontSize = 40.sp))
        Spacer(modifier = Modifier.height(50.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.padding(horizontal = 40.dp)) {
            Button(onClick = {}, shape = RoundedCornerShape(50.dp), modifier = Modifier.fillMaxWidth().height(50.dp)) {
                Text(text = "Recover password")
            }
        }
    }
}

// region Preview
@UiModePreviews
@Composable
fun ForgotPasswordScreenPreview() {
    MoviesTheme { Surface { RecoverPasswordScreen() } }
}
// endregion
