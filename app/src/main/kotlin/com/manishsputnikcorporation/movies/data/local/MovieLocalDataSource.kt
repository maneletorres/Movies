package com.manishsputnikcorporation.movies.data.local

import com.manishsputnikcorporation.movies.utils.enums.Type
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

  fun insertAll(movies: List<DatabaseMovie>)

  fun getMovies(type: Type): Flow<List<DatabaseMovie>>
}

class MovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) :
    MovieLocalDataSource {

  override fun insertAll(movies: List<DatabaseMovie>) = movieDao.insertAll(movies)

  override fun getMovies(type: Type): Flow<List<DatabaseMovie>> = movieDao.getMovies(type)
}
