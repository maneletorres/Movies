package com.manishsputnikcorporation.movies.data.local

import com.manishsputnikcorporation.movies.utils.enums.Type
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

  fun insertAll(movies: List<DatabaseMovie>)

  fun getMovies(type: Type): Flow<List<DatabaseMovie>>

  fun getFavorites(): Flow<List<DatabaseMovie>>

  fun isFavoriteMovie(id: String): Flow<Boolean>

  fun getMovie(id: String): Flow<DatabaseMovie>

  suspend fun updateMovie(movie: DatabaseMovie)
}

class MovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) :
    MovieLocalDataSource {

  override fun insertAll(movies: List<DatabaseMovie>) = movieDao.insertAll(movies)

  override fun getMovies(type: Type): Flow<List<DatabaseMovie>> = movieDao.getMovies(type)

  override fun getFavorites(): Flow<List<DatabaseMovie>> = movieDao.getFavorites()

  override fun isFavoriteMovie(id: String): Flow<Boolean> = movieDao.isFavoriteMovie(id)

  override fun getMovie(id: String) = movieDao.getMovie(id)

  override suspend fun updateMovie(movie: DatabaseMovie) = movieDao.update(movie)
}
