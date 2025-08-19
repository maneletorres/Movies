package com.manishsputnikcorporation.movies.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.manishsputnikcorporation.movies.data.local.dao.MovieDao
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSource
import com.manishsputnikcorporation.movies.data.local.datasource.MovieLocalDataSourceImpl
import com.manishsputnikcorporation.movies.di.annotations.TestDatabase
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseMovie
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseMovies
import dagger.hilt.android.testing.HiltAndroidTest
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MovieLocalDataSourceImplTest {

    // region Setup
    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()

    @Inject @TestDatabase lateinit var db: MediaDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var localDataSource: MovieLocalDataSource

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(context = ApplicationProvider.getApplicationContext(), klass = MediaDatabase::class.java).build()
        movieDao = db.movieDao()
        localDataSource = MovieLocalDataSourceImpl(movieDao)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    // endregion

    // region Testing
    @Test
    @Throws(Exception::class)
    fun insertAll() = runTest {
        localDataSource.insertAll(movies = fakeDatabaseMovies)

        movieDao.getMovies().test {
            val databaseMovies: List<DatabaseMovie> = awaitItem()
            assertTrue(databaseMovies.isNotEmpty())
            assertTrue(databaseMovies.containsAll(elements = fakeDatabaseMovies))
        }
    }

    @Test
    @Throws(Exception::class)
    fun getMovies() = runTest {
        movieDao.insertAll(movies = fakeDatabaseMovies)

        localDataSource.getMovies().test {
            val databaseMovies: List<DatabaseMovie> = awaitItem()
            assertTrue(databaseMovies.isNotEmpty())
            assertTrue(databaseMovies.containsAll(elements = fakeDatabaseMovies))
        }
    }

    @Test
    @Throws(Exception::class)
    fun getFavoriteMovies() = runTest {
        movieDao.insertAll(movies = fakeDatabaseMovies.map { it.copy(isFavorite = true) })

        localDataSource.getFavoriteMovies().test {
            val favoriteDatabaseMovies: List<DatabaseMovie> = awaitItem()
            assertTrue(favoriteDatabaseMovies.isNotEmpty())
            assertTrue(favoriteDatabaseMovies.containsAll(elements = fakeDatabaseMovies.filter { it.isFavorite }))
        }
    }

    @Test
    @Throws(Exception::class)
    fun isFavoriteMovie() = runTest {
        movieDao.insert(movie = fakeDatabaseMovie.copy(isFavorite = true))

        localDataSource.isFavoriteMovie(id = fakeDatabaseMovie.id).test { assertTrue(awaitItem()) }
    }

    @Test
    @Throws(Exception::class)
    fun getMovie() = runTest {
        movieDao.insert(movie = fakeDatabaseMovie)

        localDataSource.getMovie(id = fakeDatabaseMovie.id).test {
            val databaseMovie: DatabaseMovie = awaitItem()
            assertNotNull(databaseMovie)
            assertEquals(fakeDatabaseMovie, databaseMovie)
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateMovie() = runTest {
        movieDao.insert(movie = fakeDatabaseMovie)

        val updatedDatabaseMovie = fakeDatabaseMovie.copy(title = "updated-movie-title")

        localDataSource.updateMovie(movie = updatedDatabaseMovie)

        movieDao.getMovie(id = updatedDatabaseMovie.id).test {
            val databaseMovie: DatabaseMovie = awaitItem()
            assertNotNull(databaseMovie)
            assertEquals(updatedDatabaseMovie.title, databaseMovie.title)
        }
    }
    // endregion
}
