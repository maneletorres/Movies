package com.manishsputnikcorporation.movies.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.MovieDao
import com.manishsputnikcorporation.movies.data.local.MovieRoomDatabase
import java.io.IOException
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

  // region Setup
  private lateinit var movieDao: MovieDao
  private lateinit var db: MovieRoomDatabase

  @Before
  fun createDb() {
    val context: Context = ApplicationProvider.getApplicationContext()
    db =
        Room.inMemoryDatabaseBuilder(context, MovieRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    movieDao = db.movieDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }
  // endregion

  // region Testing
  @Test
  @Throws(Exception::class)
  fun insertAndGetMovie() = runBlocking {
    val movie = DatabaseMovie("fake-id", "fake-rank", "fake-title", "fake-year", "", "")
    movieDao.insert(movie)

    val movies = movieDao.getMovies().first()
    assertTrue(movies.size == 1)
    assertEquals(movies[0].id, movie.id)
    assertEquals(movies[0].title, movie.title)
    assertEquals(movies[0].year, movie.year)
  }

  @Test
  @Throws(Exception::class)
  fun getAllMovies() = runBlocking {
    val movie = DatabaseMovie("fake-id", "fake-rank", "fake-title", "fake-year", "", "")
    movieDao.insert(movie)

    val movie2 = DatabaseMovie("fake-id2", "fake-rank2", "fake-title2", "fake-year2", "", "")
    movieDao.insert(movie2)

    // TODO:
  }
  // endregion
}
