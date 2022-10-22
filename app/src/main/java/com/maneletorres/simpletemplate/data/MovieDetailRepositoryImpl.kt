package com.maneletorres.simpletemplate.data

import com.maneletorres.simpletemplate.domain.common.DomainError
import com.maneletorres.simpletemplate.domain.common.DomainResponse
import com.maneletorres.simpletemplate.domain.model.IMDBMovieDetailModel
import com.maneletorres.simpletemplate.domain.repository.MovieDetailRepository
import com.maneletorres.simpletemplate.network.model.IMDBMovieDetailDtoMapper
import com.maneletorres.simpletemplate.network.model.MovieDtoMapper
import com.maneletorres.simpletemplate.utils.eitherFailure
import com.maneletorres.simpletemplate.utils.eitherSuccess
import com.maneletorres.simpletemplate.utils.mapFailure
import com.maneletorres.simpletemplate.utils.mapSuccess
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val imdbMapper: IMDBMovieDetailDtoMapper
) : MovieDetailRepository {

    override fun getMovie(id: String): DomainResponse<IMDBMovieDetailModel> {
        return movieRemoteDataSource.getMovie(id)
            .mapSuccess {
                return eitherSuccess(imdbMapper.mapToDomainModel(it))
            }
            .mapFailure {
                return eitherFailure(DomainError(it.statusCode.toString(), it.raw))
            }
    }
}