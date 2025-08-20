package com.manishsputnikcorporation.movies.utils

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.network.services.executor.ServiceError
import com.manishsputnikcorporation.movies.ui.navigation.DetailArgs
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailModel
import com.manishsputnikcorporation.movies.utils.fake.fakeTVDetailModel
import kotlinx.coroutines.flow.flow

val fakeMovieDetailModelId = fakeMovieDetailModel.id
val fakeTVDetailModelId = fakeTVDetailModel.id
val movieDetailsArgs = DetailArgs(id = fakeMovieDetailModelId, type = Type.MOVIE)
val tvDetailsArgs = DetailArgs(id = fakeTVDetailModelId, type = Type.TV)

val fakeDomainError = eitherFailure(DomainError("fakeErrorCode", "fakeErrorMessage"))

val fakeFlowDomainError = flow { emit(fakeDomainError) }

val fakeCustomError = eitherFailure(error = ServiceError(description = "fakeCustomErrorRaw", statusCode = 0, headers = emptyMap()))
