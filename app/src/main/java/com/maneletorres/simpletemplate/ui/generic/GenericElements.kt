package com.maneletorres.simpletemplate.ui.generic

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maneletorres.simpletemplate.R
import com.maneletorres.simpletemplate.ui.theme.SimpleTemplateTheme

@Composable
fun GenericLoading(modifier: Modifier = Modifier) {
    Surface(
        contentColor = MaterialTheme.colors.primary
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun GenericError(
    @StringRes errorMessage: Int
) {
    Surface(
        contentColor = MaterialTheme.colors.primary
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = errorMessage)
            )
        }
    }
}

@Composable
fun GenericPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_placeholder),
            contentDescription = "",
            modifier = modifier
                .width(50.dp)
                .height(50.dp)
        )
    }
}

// region Previews
@Preview
@Composable
private fun MoviesHomeLoadingPreview() {
    SimpleTemplateTheme {
        GenericLoading()
    }
}

@Preview
@Composable
private fun MoviesHomeErrorPreview() {
    SimpleTemplateTheme {
        GenericError(errorMessage = R.string.generic_error_message)
    }
}

@Preview
@Composable
private fun MoviePlaceholderPreview() {
    SimpleTemplateTheme {
        GenericPlaceholder(modifier = Modifier
            .width(50.dp)
            .height(50.dp)
        )
    }
}
// endregion