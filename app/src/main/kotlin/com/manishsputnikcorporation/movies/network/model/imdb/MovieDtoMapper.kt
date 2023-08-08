package com.manishsputnikcorporation.movies.network.model.imdb

import com.manishsputnikcorporation.movies.domain.model.movie.MovieModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.imdb.MovieDto
import com.manishsputnikcorporation.movies.utils.enums.Type

class MovieDtoMapper : DomainMapper<MovieDto, MovieModel> {
  override fun mapToDomainModel(model: MovieDto): MovieModel {
    with(model) {
      return MovieModel(
          id = id,
          rank = rank,
          title = title,
          year = year,
          imageUrl = imageUrl,
          rating = rating,
          isFavorite = false)
    }
  }

  override fun mapFromDomainModel(domainModel: MovieModel): MovieDto {
    with(domainModel) {
      return MovieDto(
          id = id,
          rank = rank,
          title = title,
          year = year,
          imageUrl = imageUrl,
          rating = rating,
          type = Type.MOVIE)
    }
  }
}
