package com.manishsputnikcorporation.movies.utils.extensions

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.MOVIE
import com.manishsputnikcorporation.movies.utils.enums.Type.TV_SHOW

fun Type.toggleSearchType(): Type =
    when (this) {
        MOVIE -> TV_SHOW
        TV_SHOW -> MOVIE
    }

suspend fun SnackbarHostState.showSnackBar(context: Context, @StringRes message: Int) =
    showSnackbar(
        message = context.getString(message),
        actionLabel = context.resources.getString(R.string.close_message),
        duration = SnackbarDuration.Long,
    )
