package com.manishsputnikcorporation.movies.domain.repository

import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.TVModel
import kotlinx.coroutines.flow.Flow

interface TVsRepository {

    // TODO: optimize the loadData and getData calls to this!!
    // val tvs: Flow<List<TVModel>>

    fun refreshPopularTVs(): DomainResponse<Unit>

    fun getTVs(): Flow<List<TVModel>>

    fun getFavoriteTVs(): Flow<List<TVModel>>
}
