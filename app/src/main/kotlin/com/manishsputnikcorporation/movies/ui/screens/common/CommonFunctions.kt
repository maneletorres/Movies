package com.manishsputnikcorporation.movies.ui.screens.common

import android.content.Context
import android.widget.Toast
import com.manishsputnikcorporation.movies.R

fun comingSoon(context: Context) =
    Toast.makeText(context, R.string.coming_soon, Toast.LENGTH_SHORT).show()