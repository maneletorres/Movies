package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCase
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCaseExceptionDelegate
import com.manishsputnikcorporation.movies.utils.Either
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.extensions.onFailure
import com.manishsputnikcorporation.movies.utils.extensions.onSuccess
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadDataUseCase
@Inject
constructor(
    private val moviesRepository: MoviesRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Type, Either<Unit, DomainError>>(dispatcherProvider), FlowUseCaseExceptionDelegate {

  override fun prepareFlow(input: Type): Flow<Either<Unit, DomainError>> = flow {
    when (input) {
          MOVIE -> moviesRepository.refreshMostPopularMovies()
          SERIES -> moviesRepository.refreshMostPopularTVs()
        }
        .onSuccess { emit(eitherSuccess(it)) }
        .onFailure { emit(eitherFailure(it)) }
  }
}
