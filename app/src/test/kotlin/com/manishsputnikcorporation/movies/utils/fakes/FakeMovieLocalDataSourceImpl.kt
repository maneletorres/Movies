package com.manishsputnikcorporation.movies.utils.fakes

import com.manishsputnikcorporation.movies.common_test.asDatabaseModel
import com.manishsputnikcorporation.movies.common_test.flowDatabaseMovieList
import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.MovieLocalDataSource
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieLocalDataSourceImpl : MovieLocalDataSource {

  var searchType = Type.MOVIE
  var isFavorite = true
  var isSuccessful = true

  override fun insertAll(movies: List<DatabaseMovie>) = Unit

  override fun getMovies(type: Type): Flow<List<DatabaseMovie>> = flowDatabaseMovieList(searchType)

  override fun getFavorites(): Flow<List<DatabaseMovie>> = flow {
    emit(fakeMovieListDto.items.map { it.asDatabaseModel() })
  }

  override fun isFavoriteMovie(id: String): Flow<Boolean> = flow { emit(isFavorite) }

  override fun getMovie(id: String): Flow<DatabaseMovie> = flow {
    emit(fakeMovieListDto.items.first().asDatabaseModel())
  }

  override suspend fun updateMovie(movie: DatabaseMovie) =
      if (isSuccessful) Unit else throw Exception()
}
