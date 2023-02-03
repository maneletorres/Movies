package com.manishsputnikcorporation.movies.data

import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.common.DomainResponse
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.utils.eitherFailure
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapFailure
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val mapper: MovieDetailDtoMapper
) : MovieDetailRepository {

    override fun getMovie(id: String): DomainResponse<MovieDetailModel> {
        // TODO: only for test purposes
        //  val fakeMovieDetail = mapper.mapToDomainModel(fakeMovieDetailDto)
        // return eitherSuccess(fakeMovieDetail)

        return movieRemoteDataSource.getMovie(id)
            .mapSuccess { movieDetailDto ->
                return eitherSuccess(mapper.mapToDomainModel(movieDetailDto))
            }
            .mapFailure { error ->
                return eitherFailure(DomainError(error.statusCode.toString(), error.raw))
            }
    }
}