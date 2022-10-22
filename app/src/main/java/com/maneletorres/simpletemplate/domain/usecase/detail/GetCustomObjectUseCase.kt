package com.maneletorres.simpletemplate.domain.usecase.detail

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

//class GetCustomObjectUseCase @Inject constructor(
//    private val movieRepository: MovieRepository,
//    override val globalErrorMapper: GlobalErrorMapper,
//    dispatcherProvider: DispatcherProvider
//) : FlowUseCase<Int, Either<MovieModel, DomainError>>(dispatcherProvider), FlowUseCaseExceptionDelegate {
//
//    override fun prepareFlow(input: Int): Flow<Either<MovieModel, DomainError>> = flow {
//        movieRepository.getMovie(input)
//            .onSuccess { emit(eitherSuccess(it)) }
//            .onFailure { emit(eitherFailure(DomainError("", ""))) }
//    }.spreadUnauthorized { eitherFailure(DomainError("", "")) }
//}