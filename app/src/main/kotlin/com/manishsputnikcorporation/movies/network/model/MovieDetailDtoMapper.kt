package com.manishsputnikcorporation.movies.network.model

import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.MovieDetailDto
import com.manishsputnikcorporation.movies.utils.enums.toType

class MovieDetailDtoMapper : DomainMapper<MovieDetailDto, MovieDetailModel> {

  override fun mapToDomainModel(model: MovieDetailDto): MovieDetailModel {
    with(model) {
      return MovieDetailModel(
          id = id,
          title = title,
          originalTitle = originalTitle,
          fullTitle = fullTitle,
          type = type.toType(),
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
          type = type.type,
          year = year,
          plot = plot,
          image = image,
          releaseDate = releaseDate,
          runtimeStr = runtimeStr,
          directors = directors)
    }
  }
}
