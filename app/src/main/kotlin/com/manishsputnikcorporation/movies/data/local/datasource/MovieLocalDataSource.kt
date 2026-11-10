package com.manishsputnikcorporation.movies.data.local.datasource

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.dao.MovieDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    fun insertAll(movies: List<DatabaseMovie>)

    fun getMovies(): Flow<List<DatabaseMovie>>

    fun getFavoriteMovies(): Flow<List<DatabaseMovie>>

    fun isFavoriteMovie(id: Int): Flow<Boolean>

    fun getMovie(id: Int): Flow<DatabaseMovie>

    suspend fun updateMovie(movie: DatabaseMovie)

    suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}

class MovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) : MovieLocalDataSource {

    override fun insertAll(movies: List<DatabaseMovie>) = movieDao.insertAll(movies)

    override fun getMovies(): Flow<List<DatabaseMovie>> = movieDao.getMovies()

    override fun getFavoriteMovies(): Flow<List<DatabaseMovie>> = movieDao.getFavorites()

    override fun isFavoriteMovie(id: Int): Flow<Boolean> = movieDao.isFavoriteMovie(id)

    override fun getMovie(id: Int) = movieDao.getMovie(id)

    override suspend fun updateMovie(movie: DatabaseMovie) = movieDao.update(movie)

    override suspend fun updateFavorite(id: Int, isFavorite: Boolean) = movieDao.updateFavorite(id, isFavorite)
}
