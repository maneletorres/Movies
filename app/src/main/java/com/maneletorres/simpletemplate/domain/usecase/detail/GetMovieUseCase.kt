package com.maneletorres.simpletemplate.domain.usecase.detail

import com.maneletorres.simpletemplate.domain.common.DomainError
import com.maneletorres.simpletemplate.domain.error.GlobalErrorMapper
import com.maneletorres.simpletemplate.domain.model.MovieDetailModel
import com.maneletorres.simpletemplate.domain.repository.MovieDetailRepository
import com.maneletorres.simpletemplate.domain.usecase.base.dispatchers.DispatcherProvider
import com.maneletorres.simpletemplate.domain.usecase.base.flow.FlowUseCase
import com.maneletorres.simpletemplate.domain.usecase.base.flow.FlowUseCaseExceptionDelegate
import com.maneletorres.simpletemplate.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<String, Either<MovieDetailModel, DomainError>>(dispatcherProvider),
    FlowUseCaseExceptionDelegate {

    override fun prepareFlow(input: String): Flow<Either<MovieDetailModel, DomainError>> = flow {
        movieDetailRepository.getMovie(input)
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