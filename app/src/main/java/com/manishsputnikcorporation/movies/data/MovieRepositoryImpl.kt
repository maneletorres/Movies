package com.manishsputnikcorporation.movies.data

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.repository.MovieRepository
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMoviesDto
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