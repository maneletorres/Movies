package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.data.repository.MoviesRepositoryImpl
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fake.fakeTVListDto
import com.manishsputnikcorporation.movies.utils.fakeCustomError
import io.mockk.*
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
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

  @After
  fun tearDown() {
    unmockkAll()
  }
  // endregion

  // region Testing
  @Test
  fun `on refreshMostPopularMovies WHEN remote data doesn't contain an error THEN returns a success response`() {
    every { movieRemoteDataSource.getPopularMovies() } returns eitherSuccess(fakeMovieListDto)

    val result = moviesRepository.refreshPopularMovies()

    assert(result.isSuccess)
    verify(exactly = 1) { movieRemoteDataSource.getPopularMovies() }
    verify(exactly = 1) { moviesRepository.insertMovies(movies = fakeMovieListDto, MOVIE) }
    confirmVerified(movieRemoteDataSource)
  }

  @Test
  fun `on refreshMostPopularMovies WHEN remote data contains an error THEN returns a failure response`() {
    every { movieRemoteDataSource.getPopularMovies() } returns fakeCustomError

    val result = moviesRepository.refreshPopularMovies()

    assert(result.isFailure)
    verify(exactly = 1) { movieRemoteDataSource.getPopularMovies() }
    verify(exactly = 0) { moviesRepository.insertMovies(movies = fakeMovieListDto, MOVIE) }
    confirmVerified(movieRemoteDataSource)
  }

  @Test
  fun `on refreshMostPopularTVs WHEN remote data doesn't contain an error THEN returns a success response`() {
    every { movieRemoteDataSource.getMostPopularTVs() } returns eitherSuccess(data = fakeTVListDto)

    val result = moviesRepository.refreshMostPopularTVs()

    assert(result.isSuccess)
    verify(exactly = 1) { movieRemoteDataSource.getMostPopularTVs() }
    verify(exactly = 1) { moviesRepository.insertMovies(movies = fakeTVListDto, SERIES) }
    confirmVerified(movieRemoteDataSource)
  }

  @Test
  fun `on refreshMostPopularTVs WHEN remote data contains an error THEN returns a failure response`() {
    every { movieRemoteDataSource.getMostPopularTVs() } returns fakeCustomError

    val result = moviesRepository.refreshMostPopularTVs()

    assert(result.isFailure)
    verify(exactly = 1) { movieRemoteDataSource.getMostPopularTVs() }
    verify(exactly = 0) { moviesRepository.insertMovies(movies = fakeTVListDto, SERIES) }
    confirmVerified(movieRemoteDataSource)
  }

  @Test
  @Parameters(method = "searchTypeScenario")
  fun `on getData THEN returns a list of the corresponding Type`(searchType: Type) = runTest {
    every { movieLocalDataSource.getMovies() } returns flowDatabaseMovieList(searchType)

    val result = moviesRepository.getMovies().first()

    assert(result.isNotEmpty())
    verify(exactly = 1) { moviesRepository.getMovies() }
    verify(exactly = 1) { movieLocalDataSource.getMovies() }
    confirmVerified(movieLocalDataSource)
  }

  @Test
  fun `on getFavorites THEN returns the list of favorites`() = runTest {
    every { movieLocalDataSource.getFavoriteMovies() } returns flowDatabaseMovieList()

    val result = moviesRepository.getFavoriteMovies().first()

    assert(result.isNotEmpty())
    assert(result.all { it.isFavorite })
    verify(exactly = 1) { moviesRepository.getFavoriteMovies() }
    verify(exactly = 1) { movieLocalDataSource.getFavoriteMovies() }
    confirmVerified(movieLocalDataSource)
  }
  // endregion
}
