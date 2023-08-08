package com.manishsputnikcorporation.movies.network.model.tmdb.tvshow

import com.manishsputnikcorporation.movies.domain.model.tvshow.TVShowModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.tmdb.tvshow.TVShowDto

class TVShowDtoMapper : DomainMapper<TVShowDto, TVShowModel> {
    override fun mapToDomainModel(model: TVShowDto): TVShowModel {
        with (model) {
            return TVShowModel(
                id = id.toString(),
                rank = "",
                title = title,
                year = releaseDate, // TODO: to extract info from releaseData
                imageUrl = imageUrl,
                rating = rating.toString(),
                isFavorite = false
            )
        }
    }

    override fun mapFromDomainModel(domainModel: TVShowModel): TVShowDto {
        with (domainModel) {
            return TVShowDto(
                id = id.toInt(),
                title = title,
                originalTitle = "", // TODO: to study if delete
                overview = "", // TODO: to study if delete
                releaseDate = year, // TODO: to check & to study if delete
                imageUrl = imageUrl,
                rating = rating.toDouble()
            )
        }
    }
}