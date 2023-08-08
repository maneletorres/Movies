package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.model.movie.MovieModel
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCase
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCaseExceptionDelegate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetFavoriteDataUseCase
@Inject
constructor(
    private val moviesRepository: MoviesRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Unit, List<MovieModel>>(dispatcherProvider), FlowUseCaseExceptionDelegate {

  override fun prepareFlow(input: Unit): Flow<List<MovieModel>> = moviesRepository.getFavoriteMovies()
}
