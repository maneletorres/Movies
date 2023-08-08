package com.manishsputnikcorporation.movies.network.model.imdb

import com.manishsputnikcorporation.movies.domain.model.movie.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.imdb.MovieDetailDto
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.toType

class MovieDetailDtoMapper : DomainMapper<MovieDetailDto, MovieDetailModel> {

  override fun mapToDomainModel(model: MovieDetailDto): MovieDetailModel {
    with(model) {
      return MovieDetailModel(
          id = id,
          title = title,
          originalTitle = originalTitle,
          fullTitle = fullTitle,
          year = year,
          plot = plot,
          image = image,
          releaseDate = releaseDate,
          runtimeStr = runtimeStr,
          directors = directors)
    }
  }

  override fun mapFromDomainModel(domainModel: MovieDetailModel): MovieDetailDto {
    with(domainModel) {
      return MovieDetailDto(
          id = id,
          title = title,
          originalTitle = originalTitle,
          fullTitle = fullTitle,
          type = Type.MOVIE.type,
          year = year,
          plot = plot,
          image = image,
          releaseDate = releaseDate,
          runtimeStr = runtimeStr,
          directors = directors)
    }
  }
}
