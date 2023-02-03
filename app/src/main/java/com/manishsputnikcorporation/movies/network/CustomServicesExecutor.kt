package com.manishsputnikcorporation.movies.network

import android.util.Log
import com.google.gson.JsonParseException
import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.network.rest.exceptions.TimeOutException
import com.manishsputnikcorporation.movies.utils.Either
import okhttp3.Headers
import okhttp3.Request
import retrofit2.Call
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

interface CustomServicesExecutor {

    @Throws(NetworkException::class, ServiceException::class) fun <T> execute(request: Call<T>): Either<CustomResponse<T>, CustomError>

    class Default @Inject constructor(
        private val networkManager: NetworkManager
    ) : CustomServicesExecutor {

        private inline val logTag: String
            get() = javaClass.simpleName

        override fun <T> execute(request: Call<T>): Either<CustomResponse<T>, CustomError> =
            try {
                networkManager.checkInternetConnection()

                val response = request.execute()
                if (response.isSuccessful) Either.Success(CustomResponse(response.body(), response.code(), response.headers()))
                else {
                    val raw: String = response.errorBody()?.string().orEmpty()
                    Either.Failure(CustomError(raw, response.code(), response.headers()))
                }
            } catch (ex: JsonParseException) {
                Log.e(logTag, ex.toString()) // TODO: to review!
                throw ServiceException(ex.message ?: "", (request.request() as Request).url.toString())
            } catch (ex: SocketTimeoutException) {
                Log.e(logTag, ex.toString()) // TODO: to review!
                throw TimeOutException(ex.message ?: "", (request.request() as Request).url.toString())
            } catch (ex: IOException) {
                Log.e(logTag, ex.toString()) // TODO: to review!
                throw ServiceException(ex.message ?: "", (request.request() as Request).url.toString())
            }
    }
}

data class CustomResponse<T>(
    val optData: T?,
    val statusCode: Int,
    val headers: Headers
) {
    val data: T
    get() = optData!!
}

data class CustomError(
    val raw: String,
    val statusCode: Int,
    val headers: Headers
)

