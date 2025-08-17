package com.manishsputnikcorporation.movies.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.manishsputnikcorporation.movies.data.local.dao.MovieDao
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseMovie
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseMovies
import java.io.IOException
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    // region Setup
    lateinit var db: MediaDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context = context, klass = MediaDatabase::class.java).allowMainThreadQueries().build()
        movieDao = db.movieDao()
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
    fun insertMovie() = runTest {
        movieDao.insert(fakeDatabaseMovie)

        movieDao.getMovie(id = fakeDatabaseMovie.id).test {
            val databaseMovie = awaitItem()
            assertNotNull(databaseMovie)
            assertEquals(fakeDatabaseMovie, databaseMovie)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAllMovies() = runTest {
        movieDao.insertAll(movies = fakeDatabaseMovies)

        movieDao.getMovies().test {
            val databaseMovies = awaitItem()
            assert(databaseMovies.isNotEmpty())
            assert(databaseMovies.containsAll(elements = fakeDatabaseMovies))
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateMovie() = runTest {
        movieDao.insert(fakeDatabaseMovie)
        val updatedFakeMovie = fakeDatabaseMovie.copy(title = "updated-fake-movie-title")
        movieDao.update(updatedFakeMovie)

        movieDao.getMovie(id = fakeDatabaseMovie.id).test { assert(awaitItem().title == updatedFakeMovie.title) }
    }

    @Test
    @Throws(Exception::class)
    fun deleteMovie() = runTest {
        movieDao.insertAll(fakeDatabaseMovies)
        movieDao.delete(movie = fakeDatabaseMovie)

        movieDao.getMovies().test {
            val databaseMovies = awaitItem()
            assertNotNull(databaseMovies)
            assertTrue(databaseMovies.isNotEmpty())
            assertFalse(databaseMovies.contains(fakeDatabaseMovie))
        }
    }

    @Test
    @Throws(Exception::class)
    fun getMovie() = runTest {
        movieDao.insert(fakeDatabaseMovie)

        movieDao.getMovie(id = fakeDatabaseMovie.id).test {
            val databaseMovie = awaitItem()
            assertNotNull(databaseMovie)
            assertEquals(fakeDatabaseMovie, databaseMovie)
        }
    }

    @Test
    @Throws(Exception::class)
    fun isFavoriteMovie() = runTest {
        movieDao.insert(fakeDatabaseMovie)

        movieDao.isFavoriteMovie(id = fakeDatabaseMovie.id).test { assertEquals(fakeDatabaseMovie.isFavorite, awaitItem()) }
    }

    @Test
    @Throws(Exception::class)
    fun isNotFavoriteMovie() = runTest {
        movieDao.insert(fakeDatabaseMovie.copy(isFavorite = false))

        movieDao.isFavoriteMovie(id = fakeDatabaseMovie.id).test { assertFalse(awaitItem()) }
    }

    @Test
    @Throws(Exception::class)
    fun getMovies() = runTest {
        movieDao.insertAll(movies = fakeDatabaseMovies)

        movieDao.getMovies().test {
            val movieList = awaitItem()
            assert(movieList.isNotEmpty())
            assert(movieList.containsAll(elements = fakeDatabaseMovies))
        }
    }

    @Test
    @Throws(Exception::class)
    fun getFavorites() = runTest {
        movieDao.insertAll(movies = fakeDatabaseMovies)

        movieDao.getFavorites().test {
            val favorites = awaitItem()
            assert(favorites.isNotEmpty())
            assert(favorites.containsAll(elements = fakeDatabaseMovies.filter { it.isFavorite }))
            assertFalse(favorites.containsAll(elements = fakeDatabaseMovies.filterNot { it.isFavorite }))
        }
    }
    // endregion
}
