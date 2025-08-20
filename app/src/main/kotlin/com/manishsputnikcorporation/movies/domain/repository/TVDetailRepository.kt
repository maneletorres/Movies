package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.TVDetailModel
import com.manishsputnikcorporation.movies.domain.model.TVModel
import kotlinx.coroutines.flow.Flow

interface TVDetailRepository {
    fun getTVDetail(id: Int): DomainResponse<TVDetailModel>

    fun isFavoriteTV(id: Int): Flow<Boolean>

    fun getTV(id: Int): Flow<TVModel>

    suspend fun updateTV(id: Int): DomainResponse<Boolean>
}
