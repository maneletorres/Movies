package com.manishsputnikcorporation.movies.utils

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.extensions.asDatabaseModel
import com.manishsputnikcorporation.movies.utils.extensions.asDomainModel
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fake.fakeSeriesListDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun getDatabaseMovieList(searchType: Type? = Type.MOVIE): Flow<List<DatabaseMovie>> = flow {
  emit(
      when (searchType) {
        Type.MOVIE -> fakeMovieListDto.items
        Type.SERIES -> fakeSeriesListDto.items
        else -> fakeSeriesListDto.items.plus(fakeSeriesListDto.items)
      }.map { it.asDatabaseModel() })
}

fun getDomainMovieList(searchType: Type? = Type.MOVIE): Flow<List<MovieModel>> = flow {
  emit(
      when (searchType) {
        Type.MOVIE -> fakeMovieListDto.items
        Type.SERIES -> fakeSeriesListDto.items
        else -> fakeSeriesListDto.items.plus(fakeSeriesListDto.items)
      }.map { it.asDomainModel() })
}
