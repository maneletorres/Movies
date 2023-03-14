package com.manishsputnikcorporation.movies.repository

import com.manishsputnikcorporation.movies.data.MoviesRepositoryImpl
import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.extensions.asDatabaseModel
import com.manishsputnikcorporation.movies.network.CustomError
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fake.fakeSeriesListDto
import io.mockk.*
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.Headers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// TODO: to change this unit test class by androidTests, using Hilt DI and Fakes instead of mocks.
@RunWith(JUnitParamsRunner::class)
internal class MoviesRepositoryImplTest {

  // region Setup
  private val movieLocalDataSource: MovieLocalDataSource = mockk(relaxed = true)
  private val movieRemoteDataSource: MovieRemoteDataSource = mockk(relaxed = true)
  private val moviesRepository = MoviesRepositoryImpl(movieLocalDataSource, movieRemoteDataSource)

  private val fakeCustomError: CustomError =
      CustomError("fakeCustomErrorRaw", 0, Headers.headersOf())

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
    every { movieRemoteDataSource.getMostPopularMovies() } returns eitherFailure(fakeCustomError)

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
    every { movieRemoteDataSource.getMostPopularTVs() } returns eitherFailure(fakeCustomError)

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
  fun `on getData THEN returns a list of the corresponding Type`(searchType: Type) = runBlocking {
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
  fun `on getFavorites THEN returns the list of favorites`() = runBlocking {
    // Given:
    every { movieLocalDataSource.getFavorites() } returns getDatabaseMovieList()

    // When:
    val result = moviesRepository.getFavorites().first()

    // Then:
    assert(result.isNotEmpty())
    verify(exactly = 1) { moviesRepository.getFavorites() }
    verify(exactly = 1) { movieLocalDataSource.getFavorites() }
    confirmVerified(movieLocalDataSource)
  }

  // region Helper methods
  @SuppressWarnings("unused") private fun searchTypeScenario() = listOf(MOVIE, SERIES)

  private fun getDatabaseMovieList(searchType: Type? = MOVIE): Flow<List<DatabaseMovie>> = flow {
    emit(
        when (searchType) {
          MOVIE -> fakeMovieListDto.items
          SERIES -> fakeSeriesListDto.items
          else -> fakeSeriesListDto.items.plus(fakeSeriesListDto.items)
        }.map { it.asDatabaseModel() })
  }
  // endregion
  // endregion
}
