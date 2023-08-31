package com.manishsputnikcorporation.movies.network.services.executor

import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.network.NetworkManager
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.squareup.moshi.JsonDataException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Call
import timber.log.Timber

@Singleton
class RetrofitServiceExecutor @Inject constructor(private val networkManager: NetworkManager) :
    ServiceExecutor {

  private inline val logTag: String
    get() = javaClass.simpleName

  override fun <T> execute(request: ServiceRequest<T>): Either<ServiceResponse<T>, ServiceError> {
    if (request !is RetrofitServiceRequest)
        throw IllegalArgumentException(
            "RetrofitServiceExecutor only accepts RetrofitServiceRequest.")

    val retrofitCall = request.retrofitCall
    val url = retrofitCall.request().url.toString()
    return try {
      Timber.e(logTag, "Execute request $url")

      networkManager.checkConnectivity()

      with(retrofitCall.execute()) {
        val code = code()
        val headers = headers().toMultimap()

        if (isSuccessful) eitherSuccess(ServiceResponse(body(), code, headers))
        else eitherFailure(ServiceError(errorBody()?.string(), code, headers))
      }
    } catch (ex: Exception) {
      when (ex) {
        is JsonDataException,
        is IOException -> {
          Timber.e(logTag, ex.toString())
          throw ServiceException(ex.message ?: "", url)
        }
        else -> throw ex
      }
    }
  }
}

class RetrofitServiceRequest<T>(val retrofitCall: Call<T>) : ServiceRequest<T>
