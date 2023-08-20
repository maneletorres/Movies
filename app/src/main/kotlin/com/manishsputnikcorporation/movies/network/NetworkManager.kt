package com.manishsputnikcorporation.movies.network

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.NetworkInfo
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION_CODES.LOLLIPOP_MR1
import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

interface NetworkManager {

  /**
   * Returns if Internet is available or throws a [NetworkException].
   */
  @Throws(NetworkException::class) fun checkConnectivity()

  /**
   * Returns if the active network has [TRANSPORT_WIFI] from [M] or is [TYPE_WIFI] up to
   * [LOLLIPOP_MR1] or throws a [NetworkException] if there isn't Internet.
   */
  @Throws(NetworkException::class) fun checkWiFi(): Boolean
}

@Singleton
class NetworkManagerImpl @Inject constructor(private val context: Context) : NetworkManager {

  override fun checkConnectivity() {
    if (!isInternetAvailable(context)) throw NetworkException("Not available")
  }

  override fun checkWiFi(): Boolean {
    checkConnectivity()
    return isWiFiConnected(context)
  }

  companion object {

    /** Returns if there is any active network given a [context]. */
    private fun isInternetAvailable(context: Context): Boolean {
        return if (SDK_INT >= M) {
          with (getActiveNetwork(context)) {
            val activeNetwork = activeNetwork ?: return false
            val networkCapabilities = getNetworkCapabilities(activeNetwork) ?: return false
            when {
              networkCapabilities.hasTransport(TRANSPORT_WIFI) -> true
              networkCapabilities.hasTransport(TRANSPORT_CELLULAR) -> true
              networkCapabilities.hasTransport(TRANSPORT_CELLULAR) -> true
              else -> false
            }
          }
        } else {
          with (getActiveNetworkInfo(context)) {
            this != null && isConnectedOrConnecting
          }
        }
    }

    /**
     * Returns if the active network has [TRANSPORT_WIFI] from [M] or is [TYPE_WIFI] up to
     * [LOLLIPOP_MR1] given a [context].
     */
    private fun isWiFiConnected(context: Context): Boolean {
      if (SDK_INT >= M) {
        with (getActiveNetwork(context)) {
          val activeNetwork = activeNetwork ?: return false
          val networkCapabilities = getNetworkCapabilities(activeNetwork) ?: return false
          return networkCapabilities.hasTransport(TRANSPORT_WIFI)
        }
      } else {
        with (getActiveNetworkInfo(context)) {
          return this != null && type == TYPE_WIFI
        }
      }
    }

    // region Helper methods
    /**
     * Returns the [ConnectivityManager] given a [Context].
     */
    private fun getActiveNetwork(context: Context) =
      context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * Returns the active [NetworkInfo] given a [Context].
     */
    private fun getActiveNetworkInfo(context: Context) =
      (context.getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo
    // endregion
  }
}
