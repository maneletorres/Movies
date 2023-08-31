package com.manishsputnikcorporation.movies.utils.extensions

import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*

fun Type.toggleSearchType(): Type =
    when (this) {
      MOVIE -> TV_SHOW
      TV_SHOW -> MOVIE
    }
