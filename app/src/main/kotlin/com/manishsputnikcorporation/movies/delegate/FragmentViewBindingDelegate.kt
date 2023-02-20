package com.manishsputnikcorporation.movies.delegate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(klazz: Class<T>) :
    ReadOnlyProperty<Fragment, T> {

  private var binding: T? = null
  private val bindMethod: Method = klazz.getMethod("bind", View::class.java)

  override fun getValue(fragment: Fragment, property: KProperty<*>): T =
      binding ?: (bindMethod.invoke(null, fragment.requireView()) as T).also { binding = it }
}
