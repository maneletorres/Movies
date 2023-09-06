package com.manishsputnikcorporation.movies.ui.screens.common

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Surface(contentColor = MaterialTheme.colors.primary) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    }
}

@Composable
fun EmptyContent(@StringRes message: Int) {
    Surface(contentColor = MaterialTheme.colors.primary) {
        Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
            Text(text = stringResource(id = message))
        }
    }
}

@Composable
fun Placeholder(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.height(200.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_placeholder),
            contentDescription = null,
            modifier = modifier.width(50.dp).height(50.dp)
        )
    }
}

// region Previews
@UiModePreviews
@Composable
private fun LoadingPreview() {
    MoviesTheme { Loading() }
}

@UiModePreviews
@Composable
private fun EmptyContentPreview() {
    MoviesTheme { EmptyContent(message = R.string.dummy_error_message) }
}

@UiModePreviews
@Composable
private fun PlaceholderPreview() {
    MoviesTheme { Placeholder(modifier = Modifier.width(50.dp).height(50.dp)) }
}
// endregion
