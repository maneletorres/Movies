package com.maneletorres.simpletemplate.utils

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes

inline fun View.visible() {
    visibility = View.VISIBLE
}

inline fun View.invisible() {
    visibility = View.INVISIBLE
}

inline fun View.gone() {
    visibility = View.GONE
}

inline fun ImageView.setTintCompat(@ColorRes color: Int) {
    val tintMode = PorterDuff.Mode.SRC_IN
    imageTintList = ColorStateList.valueOf(color)
    imageTintMode = tintMode
}