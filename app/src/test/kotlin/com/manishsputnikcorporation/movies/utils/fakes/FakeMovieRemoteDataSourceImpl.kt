package com.manishsputnikcorporation.movies.utils.fakes

import com.manishsputnikcorporation.movies.data.remote.MovieRemoteDataSource
import com.manishsputnikcorporation.movies.network.CustomError
import com.manishsputnikcorporation.movies.network.response.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.MoviesDto
import com.manishsputnikcorporation.movies.utils.*
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailDto
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fake.fakeSeriesListDto

class FakeMovieRemoteDataSourceImpl : MovieRemoteDataSource {
  var isSuccessful = true

  override fun getMostPopularMovies(): Either<MoviesDto, CustomError> =
      if (isSuccessful) eitherSuccess(fakeMovieListDto) else fakeCustomError

  override fun getMostPopularTVs(): Either<MoviesDto, CustomError> =
      if (isSuccessful) eitherSuccess(fakeSeriesListDto) else fakeCustomError

  override fun getMovie(id: String): Either<MovieDetailDto, CustomError> =
      if (isSuccessful) eitherSuccess(fakeMovieDetailDto) else fakeCustomError
}
