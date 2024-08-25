package com.manishsputnikcorporation.movies.ui.screens.common

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
    Surface(contentColor = MaterialTheme.colorScheme.primary) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    }
}

@Composable
fun EmptyContent(@StringRes message: Int) {
    Surface(contentColor = MaterialTheme.colorScheme.primary) {
        Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
            Text(text = stringResource(id = message))
        }
    }
}

@Composable
fun Placeholder(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.height(200.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = R.drawable.ic_placeholder), contentDescription = null, modifier = modifier.size(50.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(
    @StringRes dialogTitle: Int,
    @StringRes dialogText: Int,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {}
) {
    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() },
        content = {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { onDismissRequest() }) { Text(text = stringResource(id = R.string.dialog_cancel)) }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { onConfirmation() }) { Text(text = stringResource(id = R.string.dialog_accept)) }
            }
        }
    )
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
    MoviesTheme { Placeholder(modifier = Modifier.size(50.dp)) }
}

@UiModePreviews
@Composable
private fun AlertDialogPreview() {
    MoviesTheme { AlertDialog(dialogTitle = R.string.movies_home_sign_out, dialogText = R.string.sign_out_dialog_description) }
}
// endregion
