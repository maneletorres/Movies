package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
<<<<<<<< HEAD:app/src/main/kotlin/com/manishsputnikcorporation/movies/domain/usecase/home/LoadDataUseCase.kt
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
========
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
>>>>>>>> 7e40ba7c97301e667d658f183024f1cf21602e24:app/src/main/kotlin/com/manishsputnikcorporation/movies/domain/usecase/detail/GetMovieDetailUseCase.kt
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

<<<<<<<< HEAD:app/src/main/kotlin/com/manishsputnikcorporation/movies/domain/usecase/home/LoadDataUseCase.kt
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
========
class GetMovieDetailUseCase
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
                .getMovieDetail(input)
                .onSuccess { emit(eitherSuccess(it)) }
                .onFailure { emit(eitherFailure(it)) }
          }
          .spreadUnauthorized { eitherFailure(DomainError("", "")) }
>>>>>>>> 7e40ba7c97301e667d658f183024f1cf21602e24:app/src/main/kotlin/com/manishsputnikcorporation/movies/domain/usecase/detail/GetMovieDetailUseCase.kt
}
