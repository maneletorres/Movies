package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.repository.MovieRepository
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

class GetMoviesUseCase
@Inject
constructor(
    private val movieRepository: MovieRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider
) :
    FlowUseCase<Unit, Either<List<MovieModel>, DomainError>>(dispatcherProvider),
    FlowUseCaseExceptionDelegate {

  override fun prepareFlow(input: Unit): Flow<Either<List<MovieModel>, DomainError>> =
      flow {
            movieRepository
                .getMostPopularMovies()
                .onSuccess { emit(eitherSuccess(it)) }
                .onFailure { emit(eitherFailure(it)) }
          }
          .spreadUnauthorized { eitherFailure(DomainError("", "")) }
}
