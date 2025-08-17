package com.manishsputnikcorporation.movies.data.local.datasource

import com.manishsputnikcorporation.movies.data.local.DatabaseTv
import com.manishsputnikcorporation.movies.data.local.dao.TvShowDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface TvShowLocalDataSource {

    fun insertTvShows(tvShows: List<DatabaseTv>)

    fun getTvShows(): Flow<List<DatabaseTv>>

    fun getFavoriteTvShows(): Flow<List<DatabaseTv>>

    fun isFavoriteTvShow(id: String): Flow<Boolean>

    fun getTvShow(id: String): Flow<DatabaseTv>

    suspend fun updateTvShow(movie: DatabaseTv)
}

class TvShowLocalDataSourceImpl @Inject constructor(private val tvShowDao: TvShowDao) : TvShowLocalDataSource {

    override fun insertTvShows(tvShows: List<DatabaseTv>) = tvShowDao.insertAll(tvShows)

    override fun getTvShows(): Flow<List<DatabaseTv>> = tvShowDao.getTvShows()

    override fun getFavoriteTvShows(): Flow<List<DatabaseTv>> = tvShowDao.getFavorites()

    override fun isFavoriteTvShow(id: String): Flow<Boolean> = tvShowDao.isFavoriteTvShow(id)

    override fun getTvShow(id: String) = tvShowDao.getTvShow(id)

    override suspend fun updateTvShow(movie: DatabaseTv) = tvShowDao.update(movie)
}
