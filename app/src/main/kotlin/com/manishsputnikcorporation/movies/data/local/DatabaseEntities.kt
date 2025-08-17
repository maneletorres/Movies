package com.manishsputnikcorporation.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.model.TVModel

interface DatabaseEntity

@Entity(tableName = "movies")
data class DatabaseMovie(
    @ColumnInfo(name = "id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
) : DatabaseEntity

fun DatabaseMovie.toMovieModel(): MovieModel =
    MovieModel(
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        rating = rating,
        isFavorite = isFavorite,
    )

@Entity(tableName = "tvs")
data class DatabaseTV(
    @ColumnInfo(name = "id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "originalName") val originalName: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
) : DatabaseEntity

fun DatabaseTV.toTVModel(): TVModel =
    TVModel(
        id = id,
        originalName = originalName,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        name = name,
        rating = rating,
        isFavorite = isFavorite,
    )
