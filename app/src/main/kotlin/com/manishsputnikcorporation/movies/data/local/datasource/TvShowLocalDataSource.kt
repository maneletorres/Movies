package com.manishsputnikcorporation.movies.data.local.datasource

import com.manishsputnikcorporation.movies.data.local.DatabaseTV
import com.manishsputnikcorporation.movies.data.local.dao.TvShowDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface TvShowLocalDataSource {

    fun insertTvShows(tvShows: List<DatabaseTV>)

    fun getTvShows(): Flow<List<DatabaseTV>>

    fun getFavoriteTvShows(): Flow<List<DatabaseTV>>

    fun isFavoriteTvShow(id: String): Flow<Boolean>

    fun getTvShow(id: String): Flow<DatabaseTV>

    suspend fun updateTvShow(movie: DatabaseTV)
}

class TvShowLocalDataSourceImpl @Inject constructor(private val tvShowDao: TvShowDao) : TvShowLocalDataSource {

    override fun insertTvShows(tvShows: List<DatabaseTV>) = tvShowDao.insertAll(tvShows)

    override fun getTvShows(): Flow<List<DatabaseTV>> = tvShowDao.getTvShows()

    override fun getFavoriteTvShows(): Flow<List<DatabaseTV>> = tvShowDao.getFavorites()

    override fun isFavoriteTvShow(id: String): Flow<Boolean> = tvShowDao.isFavoriteTvShow(id)

    override fun getTvShow(id: String) = tvShowDao.getTvShow(id)

    override suspend fun updateTvShow(movie: DatabaseTV) = tvShowDao.update(movie)
}
