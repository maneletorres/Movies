package com.manishsputnikcorporation.movies.data.local.datasource

import com.manishsputnikcorporation.movies.data.local.DatabaseTV
import com.manishsputnikcorporation.movies.data.local.dao.TVDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface TVLocalDataSource {

    fun insertAll(tvs: List<DatabaseTV>)

    fun getTVs(): Flow<List<DatabaseTV>>

    fun getFavoriteTVs(): Flow<List<DatabaseTV>>

    fun isFavoriteTV(id: Int): Flow<Boolean>

    fun getTV(id: Int): Flow<DatabaseTV>

    suspend fun updateTV(tv: DatabaseTV)
}

class TVLocalDataSourceImpl @Inject constructor(private val tvDao: TVDao) : TVLocalDataSource {

    override fun insertAll(tvs: List<DatabaseTV>) = tvDao.insertAll(tvs)

    override fun getTVs(): Flow<List<DatabaseTV>> = tvDao.getTVs()

    override fun getFavoriteTVs(): Flow<List<DatabaseTV>> = tvDao.getFavorites()

    override fun isFavoriteTV(id: Int): Flow<Boolean> = tvDao.isFavoriteTV(id)

    override fun getTV(id: Int) = tvDao.getTV(id)

    override suspend fun updateTV(tv: DatabaseTV) = tvDao.update(tv)
}
