package com.manishsputnikcorporation.movies.network.model.tmdb.movie

import com.manishsputnikcorporation.movies.domain.model.movie.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.tmdb.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.utils.enums.Type

class MovieDetailDtoMapper : DomainMapper<MovieDetailDto, MovieDetailModel> {

  override fun mapToDomainModel(model: MovieDetailDto): MovieDetailModel {
    with(model) {
      return MovieDetailModel(
          id = id,
          title = title,
          originalTitle = originalTitle,
          fullTitle = title,
          type = Type.MOVIE,
          year = "", // TODO:
          plot = overview,
          image = image,
          releaseDate = releaseDate,
          runtimeStr = runtime,
          directors = "")
    }
  }

  override fun mapFromDomainModel(domainModel: MovieDetailModel): MovieDetailDto {
    with(domainModel) {
      return MovieDetailDto(
          id = id,
          title = title,
          originalTitle = originalTitle,
          image = image,
          releaseDate = releaseDate,
          runtime = runtimeStr ?: "", // TODO:
          overview = plot)
    }
  }
}
