package com.maneletorres.simpletemplate.data

import com.maneletorres.simpletemplate.domain.common.DomainError
import com.maneletorres.simpletemplate.domain.common.DomainResponse
import com.maneletorres.simpletemplate.domain.model.MovieDetailModel
import com.maneletorres.simpletemplate.domain.repository.MovieDetailRepository
import com.maneletorres.simpletemplate.network.model.MovieDetailDtoMapper
import com.maneletorres.simpletemplate.utils.*
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