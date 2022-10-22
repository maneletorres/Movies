package com.maneletorres.simpletemplate.network.model

import com.maneletorres.simpletemplate.domain.model.MovieModel
import com.maneletorres.simpletemplate.domain.util.DomainMapper
import com.maneletorres.simpletemplate.network.response.theMoviesDB.MovieDto

class MovieDtoMapper: DomainMapper<MovieDto, MovieModel> {

    override fun mapToDomainModel(model: MovieDto): MovieModel {
        with (model) {
            return MovieModel(
                id = id.toString(),
                title = title,
                originalTitle = originalTitle,
                overview = overview,
                adult = adult,
                popularity = popularity,
                posterPath = image
            )
        }
    }

    override fun mapFromDomainModel(domainModel: MovieModel): MovieDto {
        with (domainModel) {
            return MovieDto(
                id = id.toInt(),
                title = title,
                originalTitle = originalTitle,
                overview = overview,
                adult = adult,
                popularity = popularity,
                image = posterPath
            )
        }
    }
}