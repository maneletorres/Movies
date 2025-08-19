package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.domain.model.TvDetailModel
import kotlinx.coroutines.flow.Flow

interface TVDetailRepository {
    fun getTVDetail(id: Int): DomainResponse<TvDetailModel>

    fun isFavoriteTV(id: Int): Flow<Boolean>

    fun getTV(id: Int): Flow<TVModel>

    suspend fun updateTV(id: Int): DomainResponse<Boolean>
}
