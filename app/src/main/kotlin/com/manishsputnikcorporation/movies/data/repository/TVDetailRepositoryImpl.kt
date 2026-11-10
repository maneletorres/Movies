package com.manishsputnikcorporation.movies.data.repository

import com.manishsputnikcorporation.movies.data.local.datasource.TVLocalDataSource
import com.manishsputnikcorporation.movies.data.local.toTVModel
import com.manishsputnikcorporation.movies.data.remote.TVRemoteDataSource
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.TVDetailModel
import com.manishsputnikcorporation.movies.domain.repository.TVDetailRepository
import com.manishsputnikcorporation.movies.network.model.TVDetailDtoMapper
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform

class TVDetailRepositoryImpl
@Inject
constructor(
    private val tvLocalDataSource: TVLocalDataSource,
    private val tvRemoteDataSource: TVRemoteDataSource,
    private val tvDetailMapper: TVDetailDtoMapper,
) : TVDetailRepository {

    override fun getTVDetail(id: Int): DomainResponse<TVDetailModel> =
        tvRemoteDataSource
            .getTV(id)
            .mapSuccess { tvDetailDto ->
                return eitherSuccess(data = tvDetailMapper.mapToDomainModel(dto = tvDetailDto))
            }
            .mapFailure { error ->
                return eitherFailure(error = DomainError(code = error.statusCode.toString(), message = error.description ?: ""))
            }

    override fun isFavoriteTV(id: Int): Flow<Boolean> = tvLocalDataSource.isFavoriteTV(id)

    override fun getTV(id: Int) = tvLocalDataSource.getTV(id).transform { databaseTV -> emit(value = databaseTV.toTVModel()) }

    override suspend fun updateTV(id: Int): DomainResponse<Boolean> {
        val tv = tvLocalDataSource.getTV(id).first()
        val updatedTvFavoriteStatus: Boolean = !tv.isFavorite
        return try {
            tvLocalDataSource.updateFavorite(id = id, isFavorite = updatedTvFavoriteStatus)
            eitherSuccess(data = updatedTvFavoriteStatus)
        } catch (ex: Exception) {
            eitherFailure(error = DomainError(code = "Code: ", message = ex.message.toString()))
        }
    }
}