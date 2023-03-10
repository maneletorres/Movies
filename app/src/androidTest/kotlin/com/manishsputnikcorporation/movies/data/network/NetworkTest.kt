package com.manishsputnikcorporation.movies.data.network

import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.extensions.fromJson
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NetworkTest {

  // region Setup
  @get:Rule val hiltRule = HiltAndroidRule(this)

  private lateinit var server: MockWebServer

  @Inject lateinit var remoteDataSource: MovieRemoteDataSource

  @Before
  fun setup() {
    server = MockWebServer()
    server.start(port = 8080)

    // Approach 1:
    server.enqueue(MockResponse().fromJson("popular_movies.json"))

    // Approach 2:
    //    server.dispatcher = object : Dispatcher() {
    //        override fun dispatch(request: RecordedRequest): MockResponse {
    //            val path = request.path
    //            path?.let {
    //                when {
    //                    path.contains("/...1") -> MockResponse().fromJson("1")
    //                    path.contains("/...2") -> MockResponse().fromJson("2")
    //                    else -> {
    //                        // TODO:
    //                    }
    //                }
    //            }
    //        }
    //
    //    }

    hiltRule.inject()
  }

  @After
  fun tearDown() {
    server.shutdown()
  }
  // endregion

  // region Testing
  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun check_mock_server_is_working() = runTest {
    val movies = remoteDataSource.getMostPopularMovies()
    movies.mapSuccess { assertEquals("Cocaine Bear", it.items[0].title) }
  }
  // endregion
}
