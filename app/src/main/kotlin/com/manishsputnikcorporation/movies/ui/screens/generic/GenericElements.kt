package com.manishsputnikcorporation.movies.ui.screens.generic

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
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme

@Composable
fun GenericLoading(modifier: Modifier = Modifier) {
  Surface(contentColor = MaterialTheme.colors.primary) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
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
fun GenericPlaceholder(modifier: Modifier = Modifier) {
  Box(modifier = Modifier.height(200.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
    Image(
        painter = painterResource(id = R.drawable.ic_placeholder),
        contentDescription = "",
        modifier = modifier.width(50.dp).height(50.dp))
  }
}

// region Previews
@Preview
@Composable
private fun MoviesHomeLoadingPreview() {
  MoviesTheme { GenericLoading() }
}

@Preview
@Composable
private fun MoviesHomeErrorPreview() {
  MoviesTheme { EmptyContent(message = R.string.generic_error_message) }
}

@Preview
@Composable
private fun MoviePlaceholderPreview() {
  MoviesTheme { GenericPlaceholder(modifier = Modifier.width(50.dp).height(50.dp)) }
}
// endregion
