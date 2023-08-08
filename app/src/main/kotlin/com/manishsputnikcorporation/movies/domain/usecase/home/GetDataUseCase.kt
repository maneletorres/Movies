package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.id.Identifiable
import com.manishsputnikcorporation.movies.domain.model.movie.MovieModel
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCase
import com.manishsputnikcorporation.movies.domain.usecase.base.flow.FlowUseCaseExceptionDelegate
import com.manishsputnikcorporation.movies.utils.enums.Type
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetDataUseCase
@Inject
constructor(
    private val moviesRepository: MoviesRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Type, List<Identifiable>>(dispatcherProvider), FlowUseCaseExceptionDelegate {

  override fun prepareFlow(input: Type): Flow<List<Identifiable>> =
      when (input) {
          Type.MOVIE -> moviesRepository.getMovies()
          Type.SERIES -> moviesRepository.getTVShows()
      }
}
