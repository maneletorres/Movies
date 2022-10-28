package com.maneletorres.simpletemplate.data

import com.maneletorres.simpletemplate.domain.common.DomainError
import com.maneletorres.simpletemplate.domain.common.DomainResponse
import com.maneletorres.simpletemplate.domain.model.MovieModel
import com.maneletorres.simpletemplate.domain.repository.MovieRepository
import com.maneletorres.simpletemplate.network.model.MovieDtoMapper
import com.maneletorres.simpletemplate.utils.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val mapper: MovieDtoMapper
) : MovieRepository {

    override fun getMostPopularMovies(): DomainResponse<List<MovieModel>> {
        // TODO: only for test purposes
        val fakeMovies = fakeMoviesDto.items.map { mapper.mapToDomainModel(it) }
        return eitherSuccess(fakeMovies)

        return movieRemoteDataSource.getMostPopularMovies()
            .mapSuccess { moviesDto ->
                return eitherSuccess(moviesDto.items.map { movieDto ->
                    mapper.mapToDomainModel(movieDto)
                })
            }
            .mapFailure { error ->
                return eitherFailure(DomainError(error.statusCode.toString(), error.raw))
            }
    }

    override fun getMostPopularTVs(): DomainResponse<List<MovieModel>> {
        return movieRemoteDataSource.getMostPopularTVs()
            .mapSuccess { moviesDto ->
                return eitherSuccess(moviesDto.items.map { movieDto ->
                    mapper.mapToDomainModel(movieDto)
                })
            }
            .mapFailure { error ->
                return eitherFailure(DomainError(error.statusCode.toString(), error.raw))
            }
    }
}