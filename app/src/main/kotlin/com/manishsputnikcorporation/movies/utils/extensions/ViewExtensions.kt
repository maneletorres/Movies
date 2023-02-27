package com.manishsputnikcorporation.movies.utils.extensions

import android.view.View

inline fun View.visible() {
  visibility = View.VISIBLE
}

inline fun View.invisible() {
  visibility = View.INVISIBLE
}

inline fun View.gone() {
  visibility = View.GONE
}
