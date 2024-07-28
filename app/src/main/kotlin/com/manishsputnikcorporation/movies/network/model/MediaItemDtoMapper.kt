package com.manishsputnikcorporation.movies.network.model

import com.manishsputnikcorporation.movies.domain.model.MediaItemModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.movie.MovieDto

class MediaItemDtoMapper : DomainMapper<MovieDto, MediaItemModel> {

    override fun mapToDomainModel(model: MovieDto): MediaItemModel {
        with(model) {
            return MediaItemModel(
                id = id.toString(),
                rank = "",
                title = title,
                year = releaseDate, // TODO: to extract info from releaseData
                imageUrl = imageUrl,
                rating = rating.toString(),
                isFavorite = false)
        }
    }

    override fun mapFromDomainModel(domainModel: MediaItemModel): MovieDto {
        with(domainModel) {
            return MovieDto(
                id = id.toInt(),
                title = title,
                originalTitle = "", // TODO: to study if delete
                overview = "", // TODO: to study if delete
                releaseDate = year, // TODO: to check & to study if delete
                imageUrl = imageUrl,
                rating = rating.toDouble())
        }
    }
}
