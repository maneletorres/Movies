package com.manishsputnikcorporation.movies.network.model

import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.tv.TVDto

class TVDtoMapper : DomainMapper<TVDto, TVModel> {

    override fun mapToDomainModel(model: TVDto): TVModel {
        with(receiver = model) {
            return TVModel(
                id = id ?: 0,
                originalName = originalName.orEmpty(),
                overview = overview.orEmpty(),
                popularity = popularity ?: 0.0,
                posterPath = posterPath.orEmpty(),
                releaseDate = releaseDate.orEmpty(),
                name = name.orEmpty(),
                rating = rating ?: 0.0,
                isFavorite = false,
            )
        }
    }

    override fun mapFromDomainModel(domainModel: TVModel): TVDto {
        with(receiver = domainModel) {
            return TVDto(
                id = id,
                originalName = originalName,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = releaseDate,
                name = name,
                rating = rating,
            )
        }
    }
}
