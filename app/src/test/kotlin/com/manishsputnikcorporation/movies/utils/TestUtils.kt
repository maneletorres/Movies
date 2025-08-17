package com.manishsputnikcorporation.movies.utils

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.model.base.Model
import com.manishsputnikcorporation.movies.network.services.executor.ServiceError
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieModel
import com.manishsputnikcorporation.movies.utils.fake.fakeTVModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val fakeDomainError = eitherFailure(DomainError("fakeErrorCode", "fakeErrorMessage"))

val fakeFlowDomainError = flow { emit(fakeDomainError) }

val fakeCustomError = eitherFailure(error = ServiceError(description = "fakeCustomErrorRaw", statusCode = 0, headers = emptyMap()))

fun getDomainMovieList(searchType: Type): Flow<List<Model>> = flow {
    when (searchType) {
        Type.MOVIE -> listOf(fakeMovieModel)
        Type.TV -> listOf(fakeTVModel)
    }
}
