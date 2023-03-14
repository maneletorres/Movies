package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.data.MovieDetailRepositoryImpl
import com.manishsputnikcorporation.movies.data.local.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.network.CustomError
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.asDatabaseModel
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailDto
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import okhttp3.Headers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MovieDetailRepositoryImplTest {

  // region Setup
  private val movieLocalDataSource: MovieLocalDataSource = mockk(relaxed = true)
  private val movieRemoteDataSource: MovieRemoteDataSource = mockk(relaxed = true)
  private val mapper: MovieDetailDtoMapper = mockk(relaxed = true)
  private val movieDetailRepository =
      MovieDetailRepositoryImpl(movieLocalDataSource, movieRemoteDataSource, mapper)

  private val fakeMovieId = "tt11909878"
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
  fun `on getMovieDetail WHEN remote data doesn't contain an error THEN returns a success response`() {
    // Given:
    every { movieRemoteDataSource.getMovie(fakeMovieId) } returns eitherSuccess(fakeMovieDetailDto)

    // When:
    val result = movieDetailRepository.getMovieDetail(fakeMovieId)

    // Then:
    assert(result.isSuccess)
    verify(exactly = 1) { movieRemoteDataSource.getMovie(fakeMovieId) }
    confirmVerified(movieRemoteDataSource)
  }

  @Test
  fun `on getMovieDetail WHEN remote data contains an error THEN returns a failure response`() {
    // Given:
    every { movieRemoteDataSource.getMovie(fakeMovieId) } returns eitherFailure(fakeCustomError)

    // When:
    val result = movieDetailRepository.getMovieDetail(fakeMovieId)

    // Then:
    assert(result.isFailure)
    verify(exactly = 1) { movieRemoteDataSource.getMovie(fakeMovieId) }
    confirmVerified(movieRemoteDataSource)
  }

  @Test
  fun `on isFavoriteMovie WHEN the movie ID belongs to a favorite movie THEN returns a true`() =
      runTest {
        // Given:
        every { movieLocalDataSource.isFavoriteMovie(fakeMovieId) } returns flow { emit(true) }

        // When:
        val result = movieDetailRepository.isFavoriteMovie(fakeMovieId).first()

        // Then:
        assertTrue(result)
        verify(exactly = 1) { movieLocalDataSource.isFavoriteMovie(fakeMovieId) }
        confirmVerified(movieLocalDataSource)
      }

  @Test
  fun `on isFavoriteMovie WHEN the movie ID doesn't belong to a favorite movie THEN returns a false`() =
      runTest {
        // Given:
        every { movieLocalDataSource.isFavoriteMovie(fakeMovieId) } returns flow { emit(false) }

        // When:
        val result = movieDetailRepository.isFavoriteMovie(fakeMovieId).first()

        // Then:
        assertFalse(result)
        verify(exactly = 1) { movieLocalDataSource.isFavoriteMovie(fakeMovieId) }
        confirmVerified(movieLocalDataSource)
      }

  @Test
  fun `on getMovie THEN returns the corresponding movie`() = runTest {
    // Given:
    every { movieLocalDataSource.getMovie(fakeMovieId) } returns flow { emit(fakeDatabaseMovie) }

    // When:
    val result = movieDetailRepository.getMovie(fakeMovieId).first()

    // Then:
    assert(result.id == fakeMovieId)
    verify(exactly = 1) { movieLocalDataSource.getMovie(fakeMovieId) }
    confirmVerified(movieLocalDataSource)
  }

  @Test
  fun `on updateMovie WHEN the database update process is successful THEN returns a success response`() =
      runTest {
        // Given:
        val fakeDatabaseMovieId = fakeDatabaseMovie.id
        every { movieLocalDataSource.getMovie(fakeDatabaseMovieId) } returns
            flow { emit(fakeDatabaseMovie) }
        coEvery { movieLocalDataSource.updateMovie(any()) } returns Unit

        // When:
        val result = movieDetailRepository.updateMovie(fakeDatabaseMovieId)

        // Then:
        assert(result.isSuccess)
        result.mapSuccess { assertFalse(fakeDatabaseMovie.isFavorite == it) }
        verify(exactly = 1) { movieLocalDataSource.getMovie(fakeDatabaseMovieId) }
        coVerify(exactly = 1) { movieLocalDataSource.updateMovie(any()) }
        confirmVerified(movieLocalDataSource)
      }

  @Test
  fun `on updateMovie WHEN the database update process is unsuccessful THEN returns a failure response`() =
      runTest {
        // Given:
        val fakeDatabaseMovieId = fakeDatabaseMovie.id
        every { movieLocalDataSource.getMovie(fakeDatabaseMovieId) } returns
            flow { emit(fakeDatabaseMovie) }
        coEvery { movieLocalDataSource.updateMovie(any()) } throws Exception()

        // When:
        val result = movieDetailRepository.updateMovie(fakeDatabaseMovieId)

        // Then:
        assert(result.isFailure)
        verify(exactly = 1) { movieLocalDataSource.getMovie(fakeDatabaseMovieId) }
        coVerify(exactly = 1) { movieLocalDataSource.updateMovie(any()) }
        confirmVerified(movieLocalDataSource)
      }

  // region Helper methods
  private val fakeDatabaseMovie = fakeMovieListDto.items[0].asDatabaseModel()
  // endregion
  // endregion
}
