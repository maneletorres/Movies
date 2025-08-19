package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.model.base.Model
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.repository.TVsRepository
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
    private val tvsRepository: TVsRepository,
    override val globalErrorMapper: GlobalErrorMapper,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<Type, List<Model>>(dispatcherProvider), FlowUseCaseExceptionDelegate {

    override fun prepareFlow(input: Type): Flow<List<Model>> =
        when (input) {
            Type.MOVIE -> moviesRepository.getMovies()
            Type.TV -> tvsRepository.getTVs()
        }
}
