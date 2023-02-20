package com.manishsputnikcorporation.movies.utils.extensions

import androidx.viewbinding.ViewBinding
import com.manishsputnikcorporation.movies.delegate.FragmentViewBindingDelegate

inline fun <reified T : ViewBinding> viewBinding(): FragmentViewBindingDelegate<T> =
    FragmentViewBindingDelegate(T::class.java)
