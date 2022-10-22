package com.maneletorres.simpletemplate.utils

import androidx.viewbinding.ViewBinding
import com.maneletorres.simpletemplate.delegate.FragmentViewBindingDelegate

inline fun <reified T : ViewBinding> viewBinding(): FragmentViewBindingDelegate<T> = FragmentViewBindingDelegate(T::class.java)
