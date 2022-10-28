package com.maneletorres.simpletemplate.network.model

import com.maneletorres.simpletemplate.domain.model.MovieDetailModel
import com.maneletorres.simpletemplate.domain.util.DomainMapper
import com.maneletorres.simpletemplate.network.response.MovieDetailDto

class MovieDetailDtoMapper : DomainMapper<MovieDetailDto, MovieDetailModel> {

    override fun mapToDomainModel(model: MovieDetailDto): MovieDetailModel {
        with (model) {
            return MovieDetailModel(
                id = id,
                title = title,
                originalTitle = originalTitle,
                fullTitle = fullTitle,
                year = year,
                plot = plot,
                image = image,
                releaseDate = releaseDate,
                runtimeStr = runtimeStr,
                directors = directors
            )
        }
    }

    override fun mapFromDomainModel(domainModel: MovieDetailModel): MovieDetailDto {
        with (domainModel) {
            return MovieDetailDto(
                id = id,
                title = title,
                originalTitle = originalTitle,
                fullTitle = fullTitle,
                year = year,
                plot = plot,
                image = image,
                releaseDate = releaseDate,
                runtimeStr = runtimeStr,
                directors = directors
            )
        }
    }
}