package com.manishsputnikcorporation.movies.data.remote

import com.manishsputnikcorporation.movies.BuildConfig
import com.manishsputnikcorporation.movies.data.remote.base.NetworkBaseTest
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class NetworkTest : NetworkBaseTest() {

  // region Setup
  @get:Rule val hiltRule = HiltAndroidRule(this)

  @Inject lateinit var remoteDataSource: MovieRemoteDataSource

  @Before
  override fun setup() {
    super.setup()
    hiltRule.inject()
  }
  // endregion

  // region Testing
  @Test
  fun get_most_popular_movies() = runTest {
    // Given:
    configServerUrl("MostPopularMovies/${BuildConfig.API_KEY}")

    // When:
    val movies = remoteDataSource.getMostPopularMovies()

    // Then:
    assertNotNull(movies)
    assert(movies.isSuccess)
    movies.mapSuccess {
      assert(it.items.isNotEmpty())
      assertEquals("Cocaine Bear", it.items[0].title)
    }
  }

  @Test
  fun get_most_popular_series() = runTest {
    // Given:
    configServerUrl("MostPopularTVs/${BuildConfig.API_KEY}")

    // When:
    val series = remoteDataSource.getMostPopularTVs()

    // Then:
    assertNotNull(series)
    assert(series.isSuccess)
    series.mapSuccess {
      assert(it.items.isNotEmpty())
      assertEquals("The Last of Us", it.items[0].title)
    }
  }

  @Test
  fun get_movie() = runTest {
    // Given:
    val movieId = "tt14209916"
    configServerUrl("Title/${BuildConfig.API_KEY}/$movieId")

    // When:
    val movie = remoteDataSource.getMovie(movieId)

    // Then:
    assertNotNull(movie)
    assert(movie.isSuccess)
    movie.mapSuccess { assertEquals("Cocaine Bear", it.title) }
  }
  // endregion
}
