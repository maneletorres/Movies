package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCase
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCaseExceptionDelegate
import com.manishsputnikcorporation.movies.network.util.EMPTY_STRING
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.onFailure
import com.manishsputnikcorporation.movies.utils.extensions.onSuccess
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieDetailUseCase
@Inject
constructor(
    private val movieDetailRepository: MovieDetailRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<Int, Either<MovieDetailModel, DomainError>>(dispatcherProvider), FlowUseCaseExceptionDelegate {

    override fun prepareFlow(input: Int): Flow<Either<MovieDetailModel, DomainError>> =
        flow {
                movieDetailRepository
                    .getMovieDetail(id = input)
                    .onSuccess { emit(value = eitherSuccess(data = it)) }
                    .onFailure { emit(value = eitherFailure(it)) }
            }
            .spreadUnauthorized { eitherFailure(error = DomainError(code = EMPTY_STRING, message = EMPTY_STRING)) } // TODO: to check!!
}
