package com.manishsputnikcorporation.movies.ui.screens.common

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.ui.theme.MediaTheme
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

@Composable
fun AlertDialog(
    @StringRes dialogTitle: Int,
    @StringRes dialogText: Int,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = { Text(text = stringResource(id = R.string.dialog_accept), modifier = Modifier.clickable { onConfirmation() }) },
        modifier = modifier,
        dismissButton = { Text(text = stringResource(id = R.string.dialog_cancel), modifier = Modifier.clickable { onDismissRequest() }) },
        title = { Text(text = stringResource(id = dialogTitle)) },
        text = { Text(text = stringResource(id = dialogText)) },
    )
}

// region Previews
@UiModePreviews
@Composable
private fun LoadingPreview() {
    MediaTheme { Loading() }
}

@UiModePreviews
@Composable
private fun EmptyContentPreview() {
    MediaTheme { EmptyContent(message = R.string.dummy_error_message) }
}

@UiModePreviews
@Composable
private fun PlaceholderPreview() {
    MediaTheme { Placeholder(modifier = Modifier.size(50.dp)) }
}

@UiModePreviews
@Composable
private fun AlertDialogPreview() {
    MediaTheme { AlertDialog(dialogTitle = R.string.movies_home_sign_out, dialogText = R.string.sign_out_dialog_description) }
}
// endregion
