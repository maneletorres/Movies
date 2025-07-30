package com.manishsputnikcorporation.movies.ui.screens.auth.signUp

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews
import com.manishsputnikcorporation.movies.utils.extensions.isValidPassword
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel, onNavigation: () -> Unit = {}) {
    signUpViewModel.trackScreen()

    val scope = rememberCoroutineScope()

    var email by rememberSaveable { mutableStateOf("") }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    val enabled = !isEmailError && !isPasswordError

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val emailText = stringResource(id = R.string.sign_in_email)
            val passwordText = stringResource(id = R.string.sign_in_password)

            SignUpTextField(
                value = email,
                isError = isEmailError,
                placeholder = emailText,
                errorText = stringResource(id = R.string.sign_in_incorrect_email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            ) {
                email = it
                // isEmailError = !isValidEmail(email)
            }
            Spacer(modifier = Modifier.height(8.dp))
            SignUpTextField(
                value = password,
                isError = isPasswordError,
                placeholder = passwordText,
                errorText = stringResource(id = R.string.sign_in_incorrect_password),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            ) {
                password = it
                // isPasswordError = !isValidPassword(password)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.padding(horizontal = 40.dp)) {
                Button(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(50.dp), onClick = { /*TODO*/ }) {
                    Text(text = "Sign up")
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            CompositionLocalProvider(LocalContentAlpha provides if (enabled) ContentAlpha.high else ContentAlpha.disabled) {
                ClickableText(
                    text = AnnotatedString(text = "Do you have an account yet?"),
                    onClick = {
                        if (enabled) {
                            scope.launch { signUpViewModel.createUser(email, password, onNavigation) }
                        }
                    },
                    style = TextStyle(fontSize = 14.sp, fontFamily = FontFamily.Default, textDecoration = TextDecoration.Underline),
                )
            }
        }
    }
}

@Composable
fun SignUpTextField(
    value: String,
    isError: Boolean,
    placeholder: String,
    errorText: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder) },
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (isError) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null, tint = MaterialTheme.colors.error)
            }
        },
        label = {
            if (isError) {
                Text(text = errorText, modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.error)
            }
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions { onValueChange(value) },
    )
}

// region Preview
@UiModePreviews
@Composable
fun SignUpScreenPreview() {
    MoviesTheme { Surface { SignUpScreen(signUpViewModel = hiltViewModel()) } }
}

@UiModePreviews
@Composable
fun SignUpTextFieldPreview() {
    MoviesTheme {
        Surface {
            var password by rememberSaveable { mutableStateOf("") }
            var isPasswordError by rememberSaveable { mutableStateOf(false) }
            SignUpTextField(
                value = password,
                isError = false,
                placeholder = stringResource(id = R.string.sign_in_email),
                errorText = stringResource(id = R.string.sign_in_incorrect_password),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            ) {
                password = it
                isPasswordError = !isValidPassword(password)
            }
        }
    }
}
// endregion
