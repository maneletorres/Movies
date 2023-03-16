package com.manishsputnikcorporation.movies.common_test

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fake.fakeSeriesListDto
import junitparams.NamedParameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@NamedParameters("searchTypeScenario") // TODO: to implement
fun searchTypeScenario() = listOf(MOVIE, SERIES)

fun getDatabaseMovieList(
    searchType: Type? = MOVIE,
    isFavorite: Boolean = true
) = when (searchType) {
    MOVIE -> fakeMovieListDto.items
    SERIES -> fakeSeriesListDto.items
    else -> fakeSeriesListDto.items.plus(fakeSeriesListDto.items)
}.map { it.asDatabaseModel(isFavorite = isFavorite) }

fun flowDatabaseMovieList(searchType: Type? = MOVIE): Flow<List<DatabaseMovie>> = flow {
    emit(getDatabaseMovieList(searchType))
}