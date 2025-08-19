package com.manishsputnikcorporation.movies.domain.repository

import app.cash.turbine.test
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.data.repository.MovieDetailRepositoryImpl
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseMovie
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailDto
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieModel
import com.manishsputnikcorporation.movies.utils.fakeCustomError
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before

internal class MovieDetailRepositoryImplTest {

    // region Setup
    private lateinit var movieLocalDataSource: MovieLocalDataSource
    private lateinit var movieRemoteDataSource: MovieRemoteDataSource
    private lateinit var movieDetailMapper: MovieDetailDtoMapper
    private lateinit var movieDetailRepository: MovieDetailRepositoryImpl

    private companion object {
        val fakeDatabaseMovieId = fakeDatabaseMovie.id
    }

    @Before
    fun setUp() {
        movieLocalDataSource = mockk(relaxed = true)
        movieRemoteDataSource = mockk(relaxed = true)
        movieDetailMapper = mockk(relaxed = true)
        movieDetailRepository =
            MovieDetailRepositoryImpl(
                movieLocalDataSource = movieLocalDataSource,
                movieRemoteDataSource = movieRemoteDataSource,
                movieDetailMapper = movieDetailMapper,
            )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN getMovieDetail is called AND remote data doesn't contain an error THEN returns a success response`() {
        every { movieRemoteDataSource.getMovie(id = fakeDatabaseMovieId) } returns eitherSuccess(data = fakeMovieDetailDto)

        val result = movieDetailRepository.getMovieDetail(id = fakeDatabaseMovieId)

        assert(result.isSuccess)
        verify(exactly = 1) { movieRemoteDataSource.getMovie(id = fakeDatabaseMovieId) }
        verify(exactly = 1) { movieDetailMapper.mapToDomainModel(model = fakeMovieDetailDto) }
        confirmVerified(movieRemoteDataSource, movieDetailMapper)
    }

    @Test
    fun `WHEN getMovieDetail is called AND remote data contains an error THEN returns a failure response`() {
        every { movieRemoteDataSource.getMovie(id = fakeDatabaseMovieId) } returns fakeCustomError

        val result = movieDetailRepository.getMovieDetail(id = fakeDatabaseMovieId)

        assert(result.isFailure)
        verify(exactly = 1) { movieRemoteDataSource.getMovie(id = fakeDatabaseMovieId) }
        verify(exactly = 0) { movieDetailMapper.mapToDomainModel(model = any()) }
        confirmVerified(movieRemoteDataSource, movieDetailMapper)
    }

    @Test
    fun `WHEN isFavoriteMovie is called AND the movie ID belongs to a favorite movie THEN returns true`() = runTest {
        every { movieLocalDataSource.isFavoriteMovie(id = fakeDatabaseMovieId) } returns flow { emit(value = true) }

        movieDetailRepository.isFavoriteMovie(id = fakeDatabaseMovieId).test {
            val result: Boolean = awaitItem()
            awaitComplete()

            assertTrue(result)
            coVerify(exactly = 1) { movieLocalDataSource.isFavoriteMovie(id = fakeDatabaseMovieId) }
            confirmVerified(movieLocalDataSource)
        }
    }

    @Test
    fun `WHEN isFavoriteMovie is called AND the movie ID doesn't belong to a favorite movie THEN returns a false`() = runTest {
        every { movieLocalDataSource.isFavoriteMovie(id = fakeDatabaseMovieId) } returns flow { emit(value = false) }

        movieDetailRepository.isFavoriteMovie(id = fakeDatabaseMovieId).test {
            val result: Boolean = awaitItem()
            awaitComplete()

            assertFalse(result)
            coVerify(exactly = 1) { movieLocalDataSource.isFavoriteMovie(id = fakeDatabaseMovieId) }
            confirmVerified(movieLocalDataSource)
        }
    }

    @Test
    fun `WHEN getMovie is called THEN returns the corresponding movie`() = runTest {
        every { movieLocalDataSource.getMovie(id = fakeDatabaseMovieId) } returns flow { emit(value = fakeDatabaseMovie) }

        movieDetailRepository.getMovie(id = fakeDatabaseMovieId).test {
            val result: MovieModel = awaitItem()
            awaitComplete()

            assertEquals(fakeMovieModel, result)
            coVerify(exactly = 1) { movieLocalDataSource.getMovie(id = fakeDatabaseMovieId) }
            confirmVerified(movieLocalDataSource)
        }
    }

    @Test
    fun `WHEN updateMovie is called AND the database update process is successful THEN returns a success response`() = runTest {
        val updatedMovie = fakeDatabaseMovie.copy(isFavorite = !fakeDatabaseMovie.isFavorite)
        every { movieLocalDataSource.getMovie(fakeDatabaseMovieId) } returns flow { emit(value = fakeDatabaseMovie) }
        coEvery { movieLocalDataSource.updateMovie(movie = updatedMovie) } returns Unit

        val result = movieDetailRepository.updateMovie(fakeDatabaseMovieId)

        assert(result.isSuccess)
        result.mapSuccess { updatedMovieFavoriteStatus -> assertEquals(updatedMovie.isFavorite, updatedMovieFavoriteStatus) }
        verify(exactly = 1) { movieLocalDataSource.getMovie(id = fakeDatabaseMovieId) }
        coVerify(exactly = 1) { movieLocalDataSource.updateMovie(movie = updatedMovie) }
        confirmVerified(movieLocalDataSource)
    }

    @Test
    fun `WHEN updateMovie is called AND the database update process is unsuccessful THEN returns a failure response`() = runTest {
        val updatedMovie = fakeDatabaseMovie.copy(isFavorite = !fakeDatabaseMovie.isFavorite)
        every { movieLocalDataSource.getMovie(fakeDatabaseMovieId) } returns flow { emit(value = fakeDatabaseMovie) }
        coEvery { movieLocalDataSource.updateMovie(movie = updatedMovie) } throws Exception()

        val result = movieDetailRepository.updateMovie(fakeDatabaseMovieId)

        assert(result.isFailure)
        verify(exactly = 1) { movieLocalDataSource.getMovie(fakeDatabaseMovieId) }
        coVerify(exactly = 1) { movieLocalDataSource.updateMovie(movie = updatedMovie) }
        confirmVerified(movieLocalDataSource)
    }
    // endregion
}
