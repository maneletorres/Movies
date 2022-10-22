package com.maneletorres.simpletemplate.domain.usecase.home

import com.maneletorres.simpletemplate.domain.common.DomainError
import com.maneletorres.simpletemplate.domain.error.GlobalErrorMapper
import com.maneletorres.simpletemplate.domain.model.MovieModel
import com.maneletorres.simpletemplate.domain.repository.MovieRepository
import com.maneletorres.simpletemplate.domain.usecase.base.dispatchers.DispatcherProvider
import com.maneletorres.simpletemplate.domain.usecase.base.flow.FlowUseCase
import com.maneletorres.simpletemplate.domain.usecase.base.flow.FlowUseCaseExceptionDelegate
import com.maneletorres.simpletemplate.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesUseCase2 @Inject constructor(
    private val movieRepository: MovieRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Unit, Either<List<MovieModel>, DomainError>>(dispatcherProvider),
    FlowUseCaseExceptionDelegate {

    override fun prepareFlow(input: Unit): Flow<Either<List<MovieModel>, DomainError>> = flow {
        movieRepository.getPopularMovies()
            .onSuccess {
                emit(eitherSuccess(it))
            }
            .onFailure {
                emit(eitherFailure(it))
            }
    }.spreadUnauthorized {
        eitherFailure(DomainError("", ""))
    }
}