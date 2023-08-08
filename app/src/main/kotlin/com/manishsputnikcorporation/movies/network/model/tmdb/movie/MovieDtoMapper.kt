package com.manishsputnikcorporation.movies.network.model.tmdb.movie

import com.manishsputnikcorporation.movies.domain.model.movie.MovieModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.tmdb.movie.MovieDto
import com.manishsputnikcorporation.movies.utils.enums.Type

class MovieDtoMapper : DomainMapper<MovieDto, MovieModel> {

  override fun mapToDomainModel(model: MovieDto): MovieModel {
    with(model) {
      return MovieModel(
          id = id.toString(),
          rank = "",
          title = title,
          year = releaseDate, // TODO: to extract info from releaseData
          imageUrl = imageUrl,
          rating = rating.toString(),
          isFavorite = false)
    }
  }

  override fun mapFromDomainModel(domainModel: MovieModel): MovieDto {
    with(domainModel) {
      return MovieDto(
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
