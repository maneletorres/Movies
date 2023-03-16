package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.common_test.asDatabaseModel
import com.manishsputnikcorporation.movies.data.MovieDetailRepositoryImpl
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fakes.FakeMovieLocalDataSourceImpl
import com.manishsputnikcorporation.movies.utils.fakes.FakeMovieRemoteDataSourceImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Test

// TODO: to choose FakeMovieDetailRepositoryImplTest or MovieDetailRepositoryImplTest
@OptIn(ExperimentalCoroutinesApi::class)
internal class FakeMovieDetailRepositoryImplTest {

  // region Setup
  private val movieLocalDataSource: FakeMovieLocalDataSourceImpl = FakeMovieLocalDataSourceImpl()
  private val movieRemoteDataSource: FakeMovieRemoteDataSourceImpl = FakeMovieRemoteDataSourceImpl()
  private val mapper: MovieDetailDtoMapper = mockk(relaxed = true)
  private val movieDetailRepository =
      MovieDetailRepositoryImpl(movieLocalDataSource, movieRemoteDataSource, mapper)

  private val fakeMovieId = "tt11909878"

  @After
  fun tearDown() {
    unmockkAll()
  }
  // endregion

  // region Testing
  @Test
  fun `on getMovieDetail WHEN remote data doesn't contain an error THEN returns a success response`() {
    // When:
    val result = movieDetailRepository.getMovieDetail(fakeMovieId)

    // Then:
    assert(result.isSuccess)
  }

  @Test
  fun `on getMovieDetail WHEN remote data contains an error THEN returns a failure response`() {
    // Given:
    movieRemoteDataSource.isSuccessful = false

    // When:
    val result = movieDetailRepository.getMovieDetail(fakeMovieId)

    // Then:
    assert(result.isFailure)
  }

  @Test
  fun `on isFavoriteMovie WHEN the movie ID belongs to a favorite movie THEN returns a true`() =
      runTest {
        // When:
        val result = movieDetailRepository.isFavoriteMovie(fakeMovieId).first()

        // Then:
        assertTrue(result)
      }

  @Test
  fun `on isFavoriteMovie WHEN the movie ID doesn't belong to a favorite movie THEN returns a false`() =
      runTest {
        // Given:
        movieLocalDataSource.isFavorite = false

        // When:
        val result = movieDetailRepository.isFavoriteMovie(fakeMovieId).first()

        // Then:
        assertFalse(result)
      }

  @Test
  fun `on getMovie THEN returns the corresponding movie`() = runTest {
    // When:
    val result = movieDetailRepository.getMovie(fakeMovieId).first()

    // Then:
    assert(result.id == fakeMovieId)
  }

  @Test
  fun `on updateMovie WHEN the database update process is successful THEN returns a success response`() =
      runTest {
        // Given:
        val fakeDatabaseMovieId = fakeDatabaseMovie.id

        // When:
        val result = movieDetailRepository.updateMovie(fakeDatabaseMovieId)

        // Then:
        assert(result.isSuccess)
        result.mapSuccess { assertFalse(fakeDatabaseMovie.isFavorite == it) }
      }

  @Test
  fun `on updateMovie WHEN the database update process is unsuccessful THEN returns a failure response`() =
      runTest {
        // Given:
        val fakeDatabaseMovieId = fakeMovieListDto.items.first().asDatabaseModel().id
        movieLocalDataSource.isSuccessful = false

        // When:
        val result = movieDetailRepository.updateMovie(fakeDatabaseMovieId)

        // Then:
        assert(result.isFailure)
      }

  // region Helper methods
  private val fakeDatabaseMovie = fakeMovieListDto.items[0].asDatabaseModel()
  // endregion
  // endregion
}
