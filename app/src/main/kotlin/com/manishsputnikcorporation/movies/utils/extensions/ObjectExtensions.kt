package com.manishsputnikcorporation.movies.utils.extensions

import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.movie.MovieDto

fun MovieDto.isContentEquals(movieModel: MovieModel): Boolean =
    id == movieModel.id &&
        originalTitle == movieModel.originalTitle &&
        overview == movieModel.overview &&
        popularity == movieModel.popularity &&
        posterPath == movieModel.posterPath &&
        releaseDate == movieModel.releaseDate &&
        title == movieModel.title &&
        rating == movieModel.rating

fun MovieModel.isContentEquals(movieDto: MovieDto): Boolean =
    id == movieDto.id &&
        originalTitle == movieDto.originalTitle &&
        overview == movieDto.overview &&
        popularity == movieDto.popularity &&
        posterPath == movieDto.posterPath &&
        releaseDate == movieDto.releaseDate &&
        title == movieDto.title &&
        rating == movieDto.rating

fun MovieDetailDto.isContentEquals(movieModel: MovieDetailModel): Boolean =
    id == movieModel.id &&
        originalTitle == movieModel.originalTitle &&
        overview == movieModel.overview &&
        popularity == movieModel.popularity &&
        posterPath == movieModel.posterPath &&
        releaseDate == movieModel.releaseDate &&
        title == movieModel.title &&
        rating == movieModel.rating

fun MovieDetailModel.isContentEquals(movieDetailModel: MovieDetailDto): Boolean =
    id == movieDetailModel.id &&
        originalTitle == movieDetailModel.originalTitle &&
        overview == movieDetailModel.overview &&
        popularity == movieDetailModel.popularity &&
        posterPath == movieDetailModel.posterPath &&
        releaseDate == movieDetailModel.releaseDate &&
        title == movieDetailModel.title &&
        rating == movieDetailModel.rating
