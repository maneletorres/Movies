package com.manishsputnikcorporation.movies.data.local

import com.manishsputnikcorporation.movies.common_test.getDatabaseMovieList
import com.manishsputnikcorporation.movies.di.annotations.TestDatabase
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import java.io.IOException
import javax.inject.Inject
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnitParamsRunner::class)
@HiltAndroidTest
class MovieLocalDataSourceImplTest {

  // region Setup
  @get:Rule var hiltRule = HiltAndroidRule(this)

  @Inject @TestDatabase lateinit var db: MovieRoomDatabase
  private lateinit var movieDao: MovieDao
  private lateinit var localDataSource: MovieLocalDataSource

  @Before
  fun setup() {
    hiltRule.inject()
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
  fun insertAll() = runTest {
    // Given:
    val fakeEntityList = getDatabaseMovieList()

    // When:
    val result = localDataSource.insertAll(fakeEntityList)

    // Then:
    assertEquals(Unit, result)
  }

  @Test
  @Parameters(method = "searchTypeScenario")
  fun getMovies(searchType: Type) = runTest {
    // Given:
    val fakeEntityList = getDatabaseMovieList(searchType)
    movieDao.insertAll(fakeEntityList)

    // When:
    val result = localDataSource.getMovies(searchType).first()

    // Then:
    assert(result.isNotEmpty())
    assert(fakeEntityList.containsAll(result))
  }

  @Test
  fun getFavorites() = runTest {
    // Given:
    val fakeEntityList = getDatabaseMovieList()
    movieDao.insertAll(fakeEntityList)

    // When:
    val result = localDataSource.getFavorites().first()

    // Then:
    assert(result.isNotEmpty())
    assert(result.all { it.isFavorite })
    assert(fakeEntityList.containsAll(result))
  }

  @Test
  @Parameters(method = "isFavoriteScenario")
  fun isFavoriteMovie(isFavorite: Boolean) = runTest {
    // Given:
    val fakeEntity = getDatabaseMovieList(isFavorite = isFavorite).first()
    movieDao.insert(fakeEntity)

    // When:
    val result = localDataSource.isFavoriteMovie(fakeEntity.id).first()

    // Then:
    assertEquals(isFavorite, result)
  }

  @Test
  fun getMovie() = runTest {
    // Given:
    val fakeEntity = getDatabaseMovieList().first()
    movieDao.insert(fakeEntity)

    // When:
    val result = localDataSource.getMovie(fakeEntity.id).first()

    // Then:
    assertEquals(fakeEntity, result)
  }

  @Test
  fun updateMovie() = runTest {
    // Given:
    val fakeEntity = getDatabaseMovieList().first()

    // When:
    val result = localDataSource.updateMovie(fakeEntity)

    // Then:
    assertEquals(Unit, result)
  }
  // endregion

  // region Helper methods
  fun isFavoriteScenario() = listOf(true, false)

  fun searchTypeScenario() = listOf(MOVIE, SERIES)
  // endregion
}
