package com.manishsputnikcorporation.movies.utils.extensions

import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.model.TVDetailModel
import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.movie.MovieDto
import com.manishsputnikcorporation.movies.network.response.tv.TVDetailDto
import com.manishsputnikcorporation.movies.network.response.tv.TVDto

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

fun MovieDetailModel.isContentEquals(movieDetailDto: MovieDetailDto): Boolean =
    id == movieDetailDto.id &&
        originalTitle == movieDetailDto.originalTitle &&
        overview == movieDetailDto.overview &&
        popularity == movieDetailDto.popularity &&
        posterPath == movieDetailDto.posterPath &&
        releaseDate == movieDetailDto.releaseDate &&
        title == movieDetailDto.title &&
        rating == movieDetailDto.rating

fun TVDto.isContentEquals(tvModel: TVModel): Boolean =
    id == tvModel.id &&
        originalName == tvModel.originalName &&
        overview == tvModel.overview &&
        popularity == tvModel.popularity &&
        posterPath == tvModel.posterPath &&
        releaseDate == tvModel.releaseDate &&
        name == tvModel.name &&
        rating == tvModel.rating

fun TVModel.isContentEquals(tvDto: TVDto): Boolean =
    id == tvDto.id &&
        originalName == tvDto.originalName &&
        overview == tvDto.overview &&
        popularity == tvDto.popularity &&
        posterPath == tvDto.posterPath &&
        releaseDate == tvDto.releaseDate &&
        name == tvDto.name &&
        rating == tvDto.rating

fun TVDetailDto.isContentEquals(tvModel: TVDetailModel): Boolean =
    id == tvModel.id &&
        originalName == tvModel.originalName &&
        overview == tvModel.overview &&
        popularity == tvModel.popularity &&
        posterPath == tvModel.posterPath &&
        releaseDate == tvModel.releaseDate &&
        name == tvModel.name &&
        rating == tvModel.rating

fun TVDetailModel.isContentEquals(tvDetailDto: TVDetailDto): Boolean =
    id == tvDetailDto.id &&
        originalName == tvDetailDto.originalName &&
        overview == tvDetailDto.overview &&
        popularity == tvDetailDto.popularity &&
        posterPath == tvDetailDto.posterPath &&
        releaseDate == tvDetailDto.releaseDate &&
        name == tvDetailDto.name &&
        rating == tvDetailDto.rating
