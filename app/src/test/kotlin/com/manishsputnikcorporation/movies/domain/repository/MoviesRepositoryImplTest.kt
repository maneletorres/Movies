package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.data.MoviesRepositoryImpl
import com.manishsputnikcorporation.movies.data.local.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fake.fakeSeriesListDto
import com.manishsputnikcorporation.movies.utils.fakeCustomError
import com.manishsputnikcorporation.movies.utils.getDatabaseMovieList
import io.mockk.*
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// TODO: to change this unit test class by androidTests, using Hilt DI and Fakes instead of mocks.
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnitParamsRunner::class)
internal class MoviesRepositoryImplTest {

  // region Setup
  private val movieLocalDataSource: MovieLocalDataSource = mockk(relaxed = true)
  private val movieRemoteDataSource: MovieRemoteDataSource = mockk(relaxed = true)
  private val moviesRepository = MoviesRepositoryImpl(movieLocalDataSource, movieRemoteDataSource)

  @Before
  fun setUp() {
    // TODO:
  }

  @After
  fun tearDown() {
    unmockkAll()
  }
  // endregion

  // region Testing
  @Test
  fun `on refreshMostPopularMovies WHEN remote data doesn't contain an error THEN returns a success response`() {
    // Given:
    every { movieRemoteDataSource.getMostPopularMovies() } returns eitherSuccess(fakeMovieListDto)

    // When:
    val result = moviesRepository.refreshMostPopularMovies()

    // Then:
    assert(result.isSuccess)
    verify(exactly = 1) { movieRemoteDataSource.getMostPopularMovies() }
    verify(exactly = 1) { moviesRepository.insertMovies(fakeMovieListDto, MOVIE) }
    confirmVerified(movieRemoteDataSource)
  }

  @Test
  fun `on refreshMostPopularMovies WHEN remote data contains an error THEN returns a failure response`() {
    // Given:
    every { movieRemoteDataSource.getMostPopularMovies() } returns fakeCustomError

    // When:
    val result = moviesRepository.refreshMostPopularMovies()

    // Then:
    assert(result.isFailure)
    verify(exactly = 1) { movieRemoteDataSource.getMostPopularMovies() }
    verify(exactly = 0) { moviesRepository.insertMovies(fakeMovieListDto, MOVIE) }
    confirmVerified(movieRemoteDataSource)
  }

  @Test
  fun `on refreshMostPopularTVs WHEN remote data doesn't contain an error THEN returns a success response`() {
    // Given:
    every { movieRemoteDataSource.getMostPopularTVs() } returns eitherSuccess(fakeSeriesListDto)

    // When:
    val result = moviesRepository.refreshMostPopularTVs()

    // Then:
    assert(result.isSuccess)
    verify(exactly = 1) { movieRemoteDataSource.getMostPopularTVs() }
    verify(exactly = 1) { moviesRepository.insertMovies(fakeSeriesListDto, SERIES) }
    confirmVerified(movieRemoteDataSource)
  }

  @Test
  fun `on refreshMostPopularTVs WHEN remote data contains an error THEN returns a failure response`() {
    // Given:
    every { movieRemoteDataSource.getMostPopularTVs() } returns fakeCustomError

    // When:
    val result = moviesRepository.refreshMostPopularTVs()

    // Then:
    assert(result.isFailure)
    verify(exactly = 1) { movieRemoteDataSource.getMostPopularTVs() }
    verify(exactly = 0) { moviesRepository.insertMovies(fakeSeriesListDto, SERIES) }
    confirmVerified(movieRemoteDataSource)
  }

  @Test
  @Parameters(method = "searchTypeScenario")
  fun `on getData THEN returns a list of the corresponding Type`(searchType: Type) = runTest {
    // Given:
    every { movieLocalDataSource.getMovies(searchType) } returns getDatabaseMovieList(searchType)

    // When:
    val result = moviesRepository.getData(searchType).first()

    // Then:
    assert(result.isNotEmpty())
    assert(result.all { movieModel -> movieModel.type == searchType })
    verify(exactly = 1) { moviesRepository.getData(searchType) }
    verify(exactly = 1) { movieLocalDataSource.getMovies(searchType) }
    confirmVerified(movieLocalDataSource)
  }

  @Test
  fun `on getFavorites THEN returns the list of favorites`() = runTest {
    // Given:
    every { movieLocalDataSource.getFavorites() } returns getDatabaseMovieList()

    // When:
    val result = moviesRepository.getFavorites().first()

    // Then:
    assert(result.isNotEmpty())
    assert(result.all { it.isFavorite })
    verify(exactly = 1) { moviesRepository.getFavorites() }
    verify(exactly = 1) { movieLocalDataSource.getFavorites() }
    confirmVerified(movieLocalDataSource)
  }

  // region Helper methods
  @SuppressWarnings("unused") private fun searchTypeScenario() = listOf(MOVIE, SERIES)
  // endregion
  // endregion
}
