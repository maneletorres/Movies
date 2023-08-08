package com.manishsputnikcorporation.movies.data.local

import com.manishsputnikcorporation.movies.data.local.dao.MovieDao
import com.manishsputnikcorporation.movies.data.local.dao.TVShowDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

  fun insertAllMovies(movies: List<DatabaseMovie>)

  fun insertAllTVShows(tvShows: List<DatabaseTVShow>)

  fun getMovies(): Flow<List<DatabaseMovie>>

  fun getTVShows(): Flow<List<DatabaseTVShow>>

  fun getFavoriteMovies(): Flow<List<DatabaseMovie>>

  fun getFavoriteTVShows(): Flow<List<DatabaseTVShow>>

  fun isFavoriteMovie(id: String): Flow<Boolean>

  fun getMovie(id: String): Flow<DatabaseMovie>

  suspend fun updateMovie(movie: DatabaseMovie)
}

class MovieLocalDataSourceImpl @Inject constructor(
  private val movieDao: MovieDao,
  private val tvShowDao: TVShowDao
) :
    MovieLocalDataSource {

  override fun insertAllMovies(movies: List<DatabaseMovie>) = movieDao.insertAll(movies)

  override fun insertAllTVShows(tvShows: List<DatabaseTVShow>) = tvShowDao.insertAll(tvShows)

  override fun getMovies(): Flow<List<DatabaseMovie>> = movieDao.getMovies()

  override fun getTVShows(): Flow<List<DatabaseTVShow>> = tvShowDao.getTVShows()

  override fun getFavoriteMovies(): Flow<List<DatabaseMovie>> = movieDao.getFavorites()

  override fun getFavoriteTVShows(): Flow<List<DatabaseTVShow>> = tvShowDao.getFavorites()

  override fun isFavoriteMovie(id: String): Flow<Boolean> = movieDao.isFavoriteMovie(id)

  override fun getMovie(id: String) = movieDao.getMovie(id)

  override suspend fun updateMovie(movie: DatabaseMovie) = movieDao.update(movie)
}
