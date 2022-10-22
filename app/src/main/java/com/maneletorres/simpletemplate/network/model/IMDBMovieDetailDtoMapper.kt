package com.maneletorres.simpletemplate.network.model

import com.maneletorres.simpletemplate.domain.model.IMDBMovieDetailModel
import com.maneletorres.simpletemplate.domain.util.DomainMapper
import com.maneletorres.simpletemplate.network.response.imdb.ImdbMovieDetailDto

class IMDBMovieDetailDtoMapper : DomainMapper<ImdbMovieDetailDto, IMDBMovieDetailModel> {

    override fun mapToDomainModel(model: ImdbMovieDetailDto): IMDBMovieDetailModel {
        with (model) {
            return IMDBMovieDetailModel(
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

    override fun mapFromDomainModel(domainModel: IMDBMovieDetailModel): ImdbMovieDetailDto {
        with (domainModel) {
            return ImdbMovieDetailDto(
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