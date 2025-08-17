package com.manishsputnikcorporation.movies.network.model

import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.movie.MovieDto

class TVDtoMapper : DomainMapper<MovieDto, MovieModel> {

    override fun mapToDomainModel(model: MovieDto): MovieModel {
        with(receiver = model) {
            return MovieModel(
                id = id ?: 0,
                originalTitle = originalTitle.orEmpty(),
                overview = overview.orEmpty(),
                popularity = popularity ?: 0.0,
                posterPath = posterPath.orEmpty(),
                releaseDate = releaseDate.orEmpty(),
                title = title.orEmpty(),
                rating = rating ?: 0.0,
                isFavorite = false,
            )
        }
    }

    override fun mapFromDomainModel(domainModel: MovieModel): MovieDto {
        with(receiver = domainModel) {
            return MovieDto(
                id = id,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = releaseDate,
                title = title,
                rating = rating,
            )
        }
    }
}
