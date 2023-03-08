package com.manishsputnikcorporation.movies.utils.extensions

import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.network.response.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.MovieDto
import com.manishsputnikcorporation.movies.utils.enums.toType

fun MovieDto.isContentEquals(movieModel: MovieModel): Boolean =
    movieModel.let {
      return id == it.id &&
          rank == it.rank &&
          title == it.title &&
          year == it.year &&
          imageUrl == it.imageUrl &&
          rating == it.rating &&
          type == it.type
    }

fun MovieDetailDto.isContentEquals(movieDetailModel: MovieDetailModel): Boolean {
  movieDetailModel.let {
    return id == it.id &&
        title == it.title &&
        originalTitle == it.originalTitle &&
        fullTitle == it.fullTitle &&
        type.toType() == it.type &&
        year == it.year &&
        plot == it.plot &&
        image == it.image &&
        releaseDate == it.releaseDate &&
        runtimeStr == it.runtimeStr &&
        directors == it.directors
  }
}
