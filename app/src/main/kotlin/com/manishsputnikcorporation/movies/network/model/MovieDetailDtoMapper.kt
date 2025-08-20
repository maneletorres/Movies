package com.manishsputnikcorporation.movies.network.model

import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto

class MovieDetailDtoMapper : DomainMapper<MovieDetailDto, MovieDetailModel> {

    override fun mapToDomainModel(dto: MovieDetailDto): MovieDetailModel =
        with(receiver = dto) {
            MovieDetailModel(
                id = id ?: 0,
                imdbId = imdbId.orEmpty(),
                originalTitle = originalTitle.orEmpty(),
                overview = overview.orEmpty(),
                popularity = popularity ?: 0.0,
                posterPath = posterPath.orEmpty(),
                releaseDate = releaseDate.orEmpty(),
                runtime = runtime ?: 0,
                title = title.orEmpty(),
                rating = rating ?: 0.0,
                isFavorite = false,
            )
        }

    override fun mapFromDomainModel(domainModel: MovieDetailModel): MovieDetailDto =
        with(receiver = domainModel) {
            MovieDetailDto(
                id = id,
                imdbId = imdbId,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = releaseDate,
                runtime = runtime,
                title = title,
                rating = rating,
            )
        }
}
