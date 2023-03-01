package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
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

class SetMovieFavoriteStatusUseCase
@Inject
constructor(
    private val movieDetailRepository: MovieDetailRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider
) :
    FlowUseCase<String, Either<Boolean, DomainError>>(dispatcherProvider),
    FlowUseCaseExceptionDelegate {

  override fun prepareFlow(input: String): Flow<Either<Boolean, DomainError>> = flow {
    movieDetailRepository
        .updateMovie(input)
        .onSuccess { emit(eitherSuccess(it)) }
        .onFailure { emit(eitherFailure(it)) }
  }
}
