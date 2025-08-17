package com.manishsputnikcorporation.movies.data.repository

import androidx.annotation.VisibleForTesting
import com.manishsputnikcorporation.movies.data.local.datasource.TVLocalDataSource
import com.manishsputnikcorporation.movies.data.local.toTVModel
import com.manishsputnikcorporation.movies.data.remote.TVRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.domain.repository.TVsRepository
import com.manishsputnikcorporation.movies.network.asDatabaseModel
import com.manishsputnikcorporation.movies.network.response.tv.TVsDto
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import kotlin.collections.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class TVsRepositoryImpl
@Inject
constructor(private val tvLocalDataSource: TVLocalDataSource, private val tvRemoteDataSource: TVRemoteDataSource) : TVsRepository {

    // TODO: optimize the loadData and getData calls to these!!
    // override val tvs = tvLocalDataSource.getTvs().map { it.asDomainModel() }

    override fun refreshPopularTVs(): DomainResponse<Unit> =
        tvRemoteDataSource
            .getPopularTVs()
            .mapSuccess { tvsDto ->
                return insertTvs(tvsDto)
            }
            .mapFailure { error ->
                return eitherFailure(error = DomainError(code = error.statusCode.toString(), message = error.description ?: ""))
            }

    override fun getTVs(): Flow<List<TVModel>> =
        tvLocalDataSource.getTVs().transform { databaseTVs -> emit(value = databaseTVs.map { databaseTv -> databaseTv.toTVModel() }) }

    override fun getFavoriteTVs(): Flow<List<TVModel>> =
        tvLocalDataSource.getFavoriteTVs().transform { databaseTVs ->
            emit(value = databaseTVs.map { databaseTv -> databaseTv.toTVModel() })
        }

    // region Helper methods
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertTvs(tvs: TVsDto): DomainResponse<Unit> =
        try {
            eitherSuccess(data = tvLocalDataSource.insertAll(tvs = tvs.asDatabaseModel()))
        } catch (ex: Exception) {
            eitherFailure(error = DomainError(code = "Code: ", message = ex.message.toString()))
        }
    // endregion
}
