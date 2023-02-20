package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCase
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCaseExceptionDelegate
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.onFailure
import com.manishsputnikcorporation.movies.utils.extensions.onSuccess
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieUseCase
@Inject
constructor(
    private val movieDetailRepository: MovieDetailRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider
) :
    FlowUseCase<String, Either<MovieDetailModel, DomainError>>(dispatcherProvider),
    FlowUseCaseExceptionDelegate {

  override fun prepareFlow(input: String): Flow<Either<MovieDetailModel, DomainError>> =
      flow {
            movieDetailRepository
                .getMovie(input)
                .onSuccess { emit(eitherSuccess(it)) }
                .onFailure { emit(eitherFailure(it)) }
          }
          .spreadUnauthorized { eitherFailure(DomainError("", "")) }
}
