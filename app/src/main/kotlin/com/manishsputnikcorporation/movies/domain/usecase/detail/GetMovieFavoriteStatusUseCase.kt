package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCase
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCaseExceptionDelegate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMovieFavoriteStatusUseCase
@Inject
constructor(
    private val movieDetailRepository: MovieDetailRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<String, Boolean>(dispatcherProvider), FlowUseCaseExceptionDelegate {

    override fun prepareFlow(input: String): Flow<Boolean> = movieDetailRepository.isFavoriteMovie(input)
}
