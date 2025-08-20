package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.domain.repository.TVDetailRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCase
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCaseExceptionDelegate
import com.manishsputnikcorporation.movies.ui.navigation.DetailArgs
import com.manishsputnikcorporation.movies.utils.enums.Type
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetFavoriteStatusUseCase
@Inject
constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val tvDetailRepository: TVDetailRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<DetailArgs, Boolean>(dispatcherProvider), FlowUseCaseExceptionDelegate {

    override fun prepareFlow(input: DetailArgs): Flow<Boolean> =
        when (input.type) {
            Type.MOVIE -> movieDetailRepository.isFavoriteMovie(id = input.id)
            Type.TV -> tvDetailRepository.isFavoriteTV(id = input.id)
        }
}
