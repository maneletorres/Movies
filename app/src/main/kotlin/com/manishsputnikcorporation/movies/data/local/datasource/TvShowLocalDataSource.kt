package com.manishsputnikcorporation.movies.data.local.datasource

import com.manishsputnikcorporation.movies.data.local.DatabaseTvShow
import com.manishsputnikcorporation.movies.data.local.dao.TvShowDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface TvShowLocalDataSource {

    fun insertTvShows(tvShows: List<DatabaseTvShow>)

    fun getTvShows(): Flow<List<DatabaseTvShow>>

    fun getFavoriteTvShows(): Flow<List<DatabaseTvShow>>

    fun isFavoriteTvShow(id: String): Flow<Boolean>

    fun getTvShow(id: String): Flow<DatabaseTvShow>

    suspend fun updateTvShow(movie: DatabaseTvShow)
}

class TvShowLocalDataSourceImpl @Inject constructor(private val tvShowDao: TvShowDao) : TvShowLocalDataSource {

    override fun insertTvShows(tvShows: List<DatabaseTvShow>) = tvShowDao.insertAll(tvShows)

    override fun getTvShows(): Flow<List<DatabaseTvShow>> = tvShowDao.getTvShows()

    override fun getFavoriteTvShows(): Flow<List<DatabaseTvShow>> = tvShowDao.getFavorites()

    override fun isFavoriteTvShow(id: String): Flow<Boolean> = tvShowDao.isFavoriteTvShow(id)

    override fun getTvShow(id: String) = tvShowDao.getTvShow(id)

    override suspend fun updateTvShow(movie: DatabaseTvShow) = tvShowDao.update(movie)
}
