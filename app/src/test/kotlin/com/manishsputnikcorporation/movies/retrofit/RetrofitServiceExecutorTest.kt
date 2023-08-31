package com.manishsputnikcorporation.movies.retrofit

import com.manishsputnikcorporation.movies.domain.error.exception.NetworkException
import com.manishsputnikcorporation.movies.domain.error.exception.ServiceException
import com.manishsputnikcorporation.movies.network.NetworkManager
import com.manishsputnikcorporation.movies.network.services.executor.RetrofitServiceExecutor
import com.manishsputnikcorporation.movies.network.services.executor.RetrofitServiceRequest
import com.manishsputnikcorporation.movies.network.services.executor.ServiceError
import com.manishsputnikcorporation.movies.network.services.executor.ServiceResponse
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import com.squareup.moshi.JsonDataException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import java.io.IOException
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class RetrofitServiceExecutorTest {

  // region Setup
  @MockK lateinit var networkManager: NetworkManager
  @MockK lateinit var retrofitCall: Call<String>
  @MockK lateinit var apiResponse: Response<String>

  private lateinit var retrofitServiceExecutor: RetrofitServiceExecutor

  private val successResponseBody = "OK"
  private val successResponseCode = 200
  private val successResponseHeaders = mapOf("header" to listOf("success headers"))

  private val failureResponseBody = "Bad request"
  private val failureResponseCode = 400
  private val failureResponseHeaders = mapOf("header" to listOf("failure headers"))

  private val jsonExceptionMessage = "Generic parse JSON error."
  private val ioExceptionMessage = "Generic I/O error."
  private val networkExceptionMessage = "Generic network error."

  @Before
  fun setUp() {
    MockKAnnotations.init(this)
    retrofitServiceExecutor = RetrofitServiceExecutor(networkManager)

    every { networkManager.checkConnectivity() } returns Unit
    every { retrofitCall.request().url.toString() } returns "fake-URL"
    every { retrofitCall.execute() } returns apiResponse
  }
  // endregion

  // region Testing
  @Test
  fun execute_network_call_successfully() {
    // Given:
    every { apiResponse.isSuccessful } returns true
    every { apiResponse.body() } returns successResponseBody
    every { apiResponse.code() } returns successResponseCode
    every { apiResponse.headers().toMultimap() } returns successResponseHeaders

    // When:
    executeNetworkCall()

        // Then:
        .mapSuccess {
          assertEquals(it.data, successResponseBody)
          assertEquals(it.statusCode, successResponseCode)
          assertEquals(it.headers, successResponseHeaders)
        }
        .mapFailure { assert(false) }
  }

  @Test
  fun execute_network_call_unsuccessfully() {
    // Given:
    every { apiResponse.isSuccessful } returns false
    every { apiResponse.errorBody()?.string() } returns failureResponseBody
    every { apiResponse.code() } returns failureResponseCode
    every { apiResponse.headers().toMultimap() } returns failureResponseHeaders

    // When:
    executeNetworkCall()

        // Then:
        .mapSuccess { assert(false) }
        .mapFailure {
          assertEquals(it.description, failureResponseBody)
          assertEquals(it.statusCode, failureResponseCode)
          assertEquals(it.headers, failureResponseHeaders)
        }
  }

  @Test(expected = ServiceException::class)
  fun `GIVEN a network call WHEN throws a JsonDataException, THEN returns a ServiceException`() {
    // Given:
    every { retrofitCall.execute() } throws JsonDataException(jsonExceptionMessage)

    // When:
    executeNetworkCall()
  }

  @Test(expected = ServiceException::class)
  fun `GIVEN a network call WHEN throws a IOException, THEN returns a ServiceException`() {
    // Given:
    every { retrofitCall.execute() } throws IOException(ioExceptionMessage)

    // When:
    executeNetworkCall()
  }

  @Test(expected = NetworkException::class)
  fun `GIVEN a network call WHEN there is no connectivity, THEN returns a NetworkException`() {
    // Given:
    every { networkManager.checkConnectivity() } throws NetworkException(networkExceptionMessage)

    // When:
    executeNetworkCall()
  }
  // endregion

  // region Helper methods
  private fun executeNetworkCall(): Either<ServiceResponse<String>, ServiceError> {
    val request = RetrofitServiceRequest(retrofitCall)
    return retrofitServiceExecutor.execute(request)
  }
  // endregion
}
