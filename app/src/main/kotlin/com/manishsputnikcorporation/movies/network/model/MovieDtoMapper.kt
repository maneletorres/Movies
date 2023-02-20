package com.manishsputnikcorporation.movies.network.model

import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.MovieDto

class MovieDtoMapper : DomainMapper<MovieDto, MovieModel> {

  override fun mapToDomainModel(model: MovieDto): MovieModel {
    with(model) {
      return MovieModel(
          id = id,
          rank = rank,
          rankUpDown = rankUpDown,
          title = title,
          fullTitle = fullTitle,
          year = year,
          image = image,
          crew = crew,
          imdbRating = imdbRating,
          imdbRatingCount = imdbRatingCount)
    }
  }

  override fun mapFromDomainModel(domainModel: MovieModel): MovieDto {
    with(domainModel) {
      return MovieDto(
          id = id,
          rank = rank,
          rankUpDown = rankUpDown,
          title = title,
          fullTitle = fullTitle,
          year = year,
          image = image,
          crew = crew,
          imdbRating = imdbRating,
          imdbRatingCount = imdbRatingCount)
    }
  }
}
