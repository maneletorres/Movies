package com.manishsputnikcorporation.movies.common_test

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.data.local.DatabaseTv
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDto
import com.manishsputnikcorporation.movies.utils.fake.fakeMoviesDto
import com.manishsputnikcorporation.movies.utils.fake.fakeTVDto
import com.manishsputnikcorporation.movies.utils.fake.fakeTVsDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun flowDatabaseMovieList(isFavorite: Boolean): Flow<List<DatabaseMovie>> = flow {
    emit(value = listOf(fakeMovieDto.toDatabase(isFavorite = isFavorite)))
}

fun flowDatabaseTVList(isFavorite: Boolean): Flow<List<DatabaseTv>> = flow {
    emit(value = listOf(fakeTVDto.toDatabase(isFavorite = isFavorite)))
}