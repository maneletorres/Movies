package com.manishsputnikcorporation.movies.data.remote.base

import com.manishsputnikcorporation.movies.extensions.fromJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before

open class MovieRemoteDataSourceImplBaseTest {

  // region Setup
  private lateinit var server: MockWebServer

  @Before
  open fun setup() {
    server = MockWebServer()
    server.start(port = 8080)
    configureServerDispatcher()
  }

  @After
  open fun tearDown() {
    server.shutdown()
  }
  // endregion

  // region Helper methods
  /** Approach 1: Call this method on each test to config the server URL accordingly. */
  fun enqueue(fileName: String) = server.enqueue(MockResponse().fromJson(fileName))

  /**
   * Approach 2: Call this method only once in the server setup to config the server URL casting.
   */
  private fun configureServerDispatcher() {
    server.dispatcher =
        object : Dispatcher() {
          override fun dispatch(request: RecordedRequest): MockResponse {
            var mockResponse = MockResponse()
            request.path?.let {
              mockResponse =
                  mockResponse.fromJson(
                      when {
                        it.contains("MostPopularMovies") -> "popular_movies.json"
                        it.contains("MostPopularTVs") -> "popular_series.json"
                        it.contains("Title") -> "movie_detail.json"
                        else -> "popular_movies.json"
                      })
            }
            return mockResponse
          }
        }
  }

  fun configServerUrl(path: String) = server.url(path)
  // endregion
}
