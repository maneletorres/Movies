package com.manishsputnikcorporation.movies.utils

import com.manishsputnikcorporation.movies.common_test.asDomainModel
import com.manishsputnikcorporation.movies.domain.common.DomainError
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.network.CustomError
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fake.fakeSeriesListDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Headers

fun getDomainMovieList(searchType: Type? = MOVIE): Flow<List<MovieModel>> = flow {
  emit(
      when (searchType) {
        MOVIE -> fakeMovieListDto.items
        SERIES -> fakeSeriesListDto.items
        else -> fakeSeriesListDto.items.plus(fakeSeriesListDto.items)
      }.map { it.asDomainModel() })
}

val fakeDomainError = eitherFailure(DomainError("fakeErrorCode", "fakeErrorMessage"))

val fakeFlowDomainError = flow { emit(fakeDomainError) }

val fakeCustomError = eitherFailure(CustomError("fakeCustomErrorRaw", 0, Headers.headersOf()))
