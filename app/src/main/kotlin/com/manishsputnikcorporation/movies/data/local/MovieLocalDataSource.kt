package com.manishsputnikcorporation.movies.data.local

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

  fun insertAll(movies: List<DatabaseMovie>)

  fun getMovies(): Flow<List<DatabaseMovie>>
}

class MovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) :
    MovieLocalDataSource {

  override fun insertAll(movies: List<DatabaseMovie>) = movieDao.insertAll(movies)

  override fun getMovies(): Flow<List<DatabaseMovie>> = movieDao.getDistinctMovies()
}
