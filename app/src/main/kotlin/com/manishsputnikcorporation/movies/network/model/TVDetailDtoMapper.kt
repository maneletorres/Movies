package com.manishsputnikcorporation.movies.network.model

import com.manishsputnikcorporation.movies.domain.model.TVDetailModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.tv.TVDetailDto

class TVDetailDtoMapper : DomainMapper<TVDetailDto, TVDetailModel> {

    override fun mapToDomainModel(dto: TVDetailDto): TVDetailModel {
        with(receiver = dto) {
            return TVDetailModel(
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

    override fun mapFromDomainModel(domainModel: TVDetailModel): TVDetailDto {
        with(receiver = domainModel) {
            return TVDetailDto(
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
