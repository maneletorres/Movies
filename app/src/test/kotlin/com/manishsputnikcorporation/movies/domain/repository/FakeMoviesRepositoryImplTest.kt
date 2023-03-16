package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.data.MoviesRepositoryImpl
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.fakes.FakeMovieLocalDataSourceImpl
import com.manishsputnikcorporation.movies.utils.fakes.FakeMovieRemoteDataSourceImpl
import io.mockk.*
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

// TODO: to choose FakeMoviesRepositoryImplTest or MoviesRepositoryImplTest
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnitParamsRunner::class)
internal class FakeMoviesRepositoryImplTest {

  // region Setup
  private val movieLocalDataSource: FakeMovieLocalDataSourceImpl = FakeMovieLocalDataSourceImpl()
  private val movieRemoteDataSource: FakeMovieRemoteDataSourceImpl = FakeMovieRemoteDataSourceImpl()
  private val moviesRepository = MoviesRepositoryImpl(movieLocalDataSource, movieRemoteDataSource)

  @After
  fun tearDown() {
    unmockkAll()
  }
  // endregion

  // region Testing
  @Test
  fun `on refreshMostPopularMovies WHEN remote data doesn't contain an error THEN returns a success response`() {
    // When:
    val result = moviesRepository.refreshMostPopularMovies()

    // Then:
    assert(result.isSuccess)
  }

  @Test
  fun `on refreshMostPopularMovies WHEN remote data contains an error THEN returns a failure response`() {
    // Given:
    movieRemoteDataSource.isSuccessful = false

    // When:
    val result = moviesRepository.refreshMostPopularMovies()

    // Then:
    assert(result.isFailure)
  }

  @Test
  fun `on refreshMostPopularTVs WHEN remote data doesn't contain an error THEN returns a success response`() {
    // When:
    val result = moviesRepository.refreshMostPopularTVs()

    // Then:
    assert(result.isSuccess)
  }

  @Test
  fun `on refreshMostPopularTVs WHEN remote data contains an error THEN returns a failure response`() {
    // Given:
    movieRemoteDataSource.isSuccessful = false

    // When:
    val result = moviesRepository.refreshMostPopularTVs()

    // Then:
    assert(result.isFailure)
  }

  @Test
  @Parameters(method = "searchTypeScenario")
  fun `on getData THEN returns a list of the corresponding Type`(searchType: Type) = runTest {
    // Given:
    movieLocalDataSource.searchType = searchType

    // When:
    val result = moviesRepository.getData(searchType).first()

    // Then:
    assert(result.isNotEmpty())
    assert(result.all { movieModel -> movieModel.type == searchType })
  }

  @Test
  fun `on getFavorites THEN returns the list of favorites`() = runTest {
    // When:
    val result = moviesRepository.getFavorites().first()

    // Then:
    assert(result.isNotEmpty())
    assert(result.all { it.isFavorite })
  }
  // endregion

  // region Helper methods
  @SuppressWarnings("unused") private fun searchTypeScenario() = listOf(MOVIE, SERIES)
  // endregion
}
