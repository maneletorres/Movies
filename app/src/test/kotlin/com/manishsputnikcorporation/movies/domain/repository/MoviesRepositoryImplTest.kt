package com.manishsputnikcorporation.movies.domain.repository

import app.cash.turbine.test
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.toMovieModel
import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.data.repository.MoviesRepositoryImpl
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseMovies
import com.manishsputnikcorporation.movies.utils.fake.fakeMoviesDto
import com.manishsputnikcorporation.movies.utils.fakeCustomError
import io.mockk.*
import kotlin.collections.isNotEmpty
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class MoviesRepositoryImplTest {

    // region Setup
    private lateinit var movieLocalDataSource: MovieLocalDataSource
    private lateinit var movieRemoteDataSource: MovieRemoteDataSource
    private lateinit var moviesRepository: MoviesRepositoryImpl

    @Before
    fun setUp() {
        movieLocalDataSource = mockk(relaxed = true)
        movieRemoteDataSource = mockk(relaxed = true)
        moviesRepository = MoviesRepositoryImpl(movieLocalDataSource = movieLocalDataSource, movieRemoteDataSource = movieRemoteDataSource)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN refreshPopularMovies is called AND remote data doesn't contain an error THEN returns a success response`() = runTest {
        every { movieRemoteDataSource.getPopularMovies() } returns eitherSuccess(data = fakeMoviesDto)
        every { movieLocalDataSource.getMovies() } returns flow { emit(value = fakeDatabaseMovies) }

        val result = moviesRepository.refreshPopularMovies()

        assert(result.isSuccess)
        verify(exactly = 1) { movieRemoteDataSource.getPopularMovies() }
        verify(exactly = 1) { movieLocalDataSource.getMovies() }
        verify(exactly = 1) { movieLocalDataSource.insertAll(movies = any()) }
        confirmVerified(movieRemoteDataSource, movieLocalDataSource)
    }

    @Test
    fun `WHEN refreshPopularMovies is called AND remote data contains an error THEN returns a failure response`() = runTest {
        every { movieRemoteDataSource.getPopularMovies() } returns fakeCustomError

        val result = moviesRepository.refreshPopularMovies()

        assert(result.isFailure)
        verify(exactly = 1) { movieRemoteDataSource.getPopularMovies() }
        verify(exactly = 0) { movieLocalDataSource.insertAll(movies = any()) }
        confirmVerified(movieRemoteDataSource, movieLocalDataSource)
    }

    @Test
    fun `WHEN getMovies is called THEN returns the movie list`() = runTest {
        every { movieLocalDataSource.getMovies() } returns flow { emit(value = fakeDatabaseMovies) }
        val expected = fakeDatabaseMovies.map { it.toMovieModel() }

        moviesRepository.getMovies().test {
            val result: List<MovieModel> = awaitItem()
            awaitComplete()

            assertTrue(result.isNotEmpty())
            assertEquals(expected, result)
            verify(exactly = 1) { movieLocalDataSource.getMovies() }
            confirmVerified(movieLocalDataSource)
        }
    }

    @Test
    fun `WHEN getFavoriteMovies is called THEN returns the favorite movie list`() = runTest {
        val favoriteDatabaseMovies = fakeDatabaseMovies.filter { it.isFavorite }
        every { movieLocalDataSource.getFavoriteMovies() } returns flow { emit(value = favoriteDatabaseMovies) }
        val expected = favoriteDatabaseMovies.map { it.toMovieModel() }

        moviesRepository.getFavoriteMovies().test {
            val result: List<MovieModel> = awaitItem()
            awaitComplete()

            assertTrue(result.isNotEmpty())
            assertEquals(expected, result)
            verify(exactly = 1) { movieLocalDataSource.getFavoriteMovies() }
            confirmVerified(movieLocalDataSource)
        }
    }
    // endregion
}
