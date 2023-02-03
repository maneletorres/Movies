<<<<<<<< HEAD:app/src/main/java/com/manishsputnikcorporation/movies/utils/extensions/ViewExtensions.kt
package com.manishsputnikcorporation.movies.utils.extensions
========
package com.manishsputnikcorporation.movies.utils
>>>>>>>> c0801610c5e22d55cd52b6d2df80e21916a62fd6:app/src/main/java/com/manishsputnikcorporation/movies/utils/ViewExtensions.kt

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