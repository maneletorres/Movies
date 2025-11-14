package com.manishsputnikcorporation.movies.utils.extensions

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.MOVIE
import com.manishsputnikcorporation.movies.utils.enums.Type.TV

fun Type.toggleSearchType(): Type =
    when (this) {
        MOVIE -> TV
        TV -> MOVIE
    }

suspend fun SnackbarHostState.showSnackBar(context: Context, @StringRes message: Int) =
    showSnackBar(context = context, message = context.getString(message))

suspend fun SnackbarHostState.showSnackBar(context: Context, message: String) =
    showSnackbar(message = message, actionLabel = context.resources.getString(R.string.close_message), duration = SnackbarDuration.Long)
