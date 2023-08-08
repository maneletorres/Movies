package com.manishsputnikcorporation.movies.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.dao.MovieDao
import com.manishsputnikcorporation.movies.data.local.MovieRoomDatabase
import com.manishsputnikcorporation.movies.utils.createMovie
import com.manishsputnikcorporation.movies.utils.createSeries
import com.manishsputnikcorporation.movies.utils.enums.Type
import java.io.IOException
import junit.framework.TestCase.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
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
  fun insertMovie() = runBlocking {
    // Given:
    val fakeMovie = createMovie()

    // When:
    movieDao.insert(fakeMovie)

    // Then:
    val movieList = movieDao.getMovies(Type.MOVIE).first()
    val movie = movieList[0]
    assertTrue(movieList.size == 1)
    assertThat(fakeMovie, equalTo(movie))

    // TODO: is this necessary?
    assertEquals(movie.id, fakeMovie.id)
    assertEquals(movie.rank, fakeMovie.rank)
    assertEquals(movie.title, fakeMovie.title)
    assertEquals(movie.year, fakeMovie.year)
    assertEquals(movie.imageUrl, fakeMovie.imageUrl)
    assertEquals(movie.rating, fakeMovie.rating)
    assertEquals(movie.type, fakeMovie.type)
  }

  @Test
  @Throws(Exception::class)
  fun insertSeries() = runBlocking {
    // Given:
    val fakeSeries = createSeries()

    // When:
    movieDao.insert(fakeSeries)

    // Then:
    val seriesList = movieDao.getMovies(Type.SERIES).first()
    val series = seriesList[0]
    assertTrue(seriesList.size == 1)
    assertThat(fakeSeries, equalTo(series))

    // TODO: is this necessary?
    assertEquals(series.id, fakeSeries.id)
    assertEquals(series.rank, fakeSeries.rank)
    assertEquals(series.title, fakeSeries.title)
    assertEquals(series.year, fakeSeries.year)
    assertEquals(series.imageUrl, fakeSeries.imageUrl)
    assertEquals(series.rating, fakeSeries.rating)
    assertEquals(series.type, fakeSeries.type)
  }

  @Test
  @Throws(Exception::class)
  fun insertAllMovieList() = runBlocking {
    // Given:
    val fakeMovieList = mutableListOf<DatabaseMovie>()
    for (i in 0..2) fakeMovieList.add(createMovie(i.toString()))

    // When:
    movieDao.insertAll(fakeMovieList)

    // Then:
    val movieList = movieDao.getMovies(Type.MOVIE).first()
    assert(movieList.isNotEmpty())
    assert(movieList.containsAll(fakeMovieList))
  }

  @Test
  @Throws(Exception::class)
  fun insertAllSeriesList() = runBlocking {
    // Given:
    val fakeSeriesList = mutableListOf<DatabaseMovie>()
    for (i in 0..2) fakeSeriesList.add(createSeries(i.toString()))

    // When:
    movieDao.insertAll(fakeSeriesList)

    // Then:
    val seriesList = movieDao.getMovies(Type.SERIES).first()
    assert(seriesList.isNotEmpty())
    assert(seriesList.containsAll(fakeSeriesList))
  }

  @Test
  @Throws(Exception::class)
  fun updateMovie() = runBlocking {
    // Given:
    val fakeMovie = createMovie()
    val updatedFakeMovieTitle = "updated-fake-movie-title"
    val updatedFakeMovie = fakeMovie.copy(title = updatedFakeMovieTitle)
    movieDao.insert(fakeMovie)

    // When:
    movieDao.update(updatedFakeMovie)

    // Then:
    val movie = movieDao.getMovie(fakeMovie.id).first()
    assert(movie.title == updatedFakeMovieTitle)
  }

  @Test
  @Throws(Exception::class)
  fun updateSeries() = runBlocking {
    // Given:
    val fakeSeries = createSeries()
    val updatedFakeSeriesTitle = "updated-fake-series-title"
    val updatedFakeSeries = fakeSeries.copy(title = updatedFakeSeriesTitle)
    movieDao.insert(fakeSeries)

    // When:
    movieDao.update(updatedFakeSeries)

    // Then:
    val series = movieDao.getMovie(fakeSeries.id).first()
    assert(series.title == updatedFakeSeriesTitle)
  }

  @Test
  @Throws(Exception::class)
  fun deleteMovie() = runBlocking {
    // Given:
    val fakeMovie1 = createMovie("1")
    val fakeMovie2 = createMovie("2")
    movieDao.insertAll(listOf(fakeMovie1, fakeMovie2))

    // When:
    movieDao.delete(fakeMovie1)

    // Then:
    val movieList = movieDao.getMovies(Type.MOVIE).first()
    assert(movieList.isNotEmpty())
    assertFalse(movieList.contains(fakeMovie1))
  }

  @Test
  @Throws(Exception::class)
  fun deleteSeries() = runBlocking {
    // Given:
    val fakeSeries1 = createSeries("1")
    val fakeSeries2 = createSeries("2")
    movieDao.insertAll(listOf(fakeSeries1, fakeSeries2))

    // When:
    movieDao.delete(fakeSeries1)

    // Then:
    val seriesList = movieDao.getMovies(Type.SERIES).first()
    assert(seriesList.isNotEmpty())
    assertFalse(seriesList.contains(fakeSeries1))
  }

  @Test
  @Throws(Exception::class)
  fun getMovie() = runBlocking {
    // Given:
    val fakeMovie = createMovie()
    movieDao.insert(fakeMovie)

    // When:
    val movie = movieDao.getMovie(fakeMovie.id).first()

    // Then:
    assertNotNull(movie)
    assertThat(fakeMovie, equalTo(movie))
  }

  @Test
  @Throws(Exception::class)
  fun getSeries() = runBlocking {
    // Given:
    val fakeSeries = createSeries()
    movieDao.insert(fakeSeries)

    // When:
    val series = movieDao.getMovie(fakeSeries.id).first()

    // Then:
    assertNotNull(series)
    assertThat(fakeSeries, equalTo(series))
  }

  @Test
  @Throws(Exception::class)
  fun getMovieList() = runBlocking {
    // Given:
    val fakeMovieList = listOf(createMovie("1"), createMovie("2"))
    movieDao.insertAll(fakeMovieList)

    // When:
    val movieList = movieDao.getMovies(Type.MOVIE).first()

    // Then:
    assert(movieList.isNotEmpty())
    assert(movieList.containsAll(fakeMovieList))
  }

  @Test
  @Throws(Exception::class)
  fun getSeriesList() = runBlocking {
    // Given:
    val fakeSeriesList = listOf(createSeries("1"), createSeries("2"))
    movieDao.insertAll(fakeSeriesList)

    // When:
    val seriesList = movieDao.getMovies(Type.SERIES).first()

    // Then:
    assert(seriesList.isNotEmpty())
    assert(seriesList.containsAll(fakeSeriesList))
  }

  @Test
  @Throws(Exception::class)
  fun getFavorites() = runBlocking {
    // Given:
    val fakeNotFavoriteMovie = createMovie("1")
    val fakeDataList =
        listOf(
            fakeNotFavoriteMovie,
            createMovie("2", true),
            createSeries("3", true),
        )
    movieDao.insertAll(fakeDataList)

    // When:
    val favorites = movieDao.getFavorites().first()

    // Then:
    assert(favorites.isNotEmpty())
    assert(favorites.containsAll(fakeDataList))
    assertFalse(favorites.contains(fakeNotFavoriteMovie))
  }
  // endregion
}
