package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.model.MediaItemModel
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCase
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCaseExceptionDelegate
import com.manishsputnikcorporation.movies.utils.enums.Type
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetFavoriteDataUseCase
@Inject
constructor(
    private val moviesRepository: MoviesRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Type, List<MediaItemModel>>(dispatcherProvider), FlowUseCaseExceptionDelegate {

    override fun prepareFlow(input: Type): Flow<List<MediaItemModel>> =
        when (input) {
            Type.MOVIE -> moviesRepository.getFavoriteMovies()
            Type.TV_SHOW -> moviesRepository.getFavoriteTvShows()
        }
}
