package com.maneletorres.simpletemplate.network.model

import com.maneletorres.simpletemplate.domain.model.IMDBMovieModel
import com.maneletorres.simpletemplate.domain.util.DomainMapper
import com.maneletorres.simpletemplate.network.response.imdb.ImdbMovieDto

class IMDBMovieDtoMapper : DomainMapper<ImdbMovieDto, IMDBMovieModel> {

    override fun mapToDomainModel(model: ImdbMovieDto): IMDBMovieModel {
        with (model) {
            return IMDBMovieModel(
                id = id,
                rank = rank,
                rankUpDown = rankUpDown,
                title = title,
                fullTitle = fullTitle,
                year = year,
                image = image,
                crew = crew,
                imdbRating = imdbRating,
                imdbRatingCount = imdbRatingCount
            )
        }
    }

    override fun mapFromDomainModel(domainModel: IMDBMovieModel): ImdbMovieDto {
        with (domainModel) {
            return ImdbMovieDto(
                id = id,
                rank = rank,
                rankUpDown = rankUpDown,
                title = title,
                fullTitle = fullTitle,
                year = year,
                image = image,
                crew = crew,
                imdbRating = imdbRating,
                imdbRatingCount = imdbRatingCount
            )
        }
    }
}