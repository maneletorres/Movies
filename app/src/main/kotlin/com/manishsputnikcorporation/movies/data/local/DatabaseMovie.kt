package com.manishsputnikcorporation.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manishsputnikcorporation.movies.domain.model.movie.MovieModel
import com.manishsputnikcorporation.movies.domain.model.tvshow.TVShowModel
import com.manishsputnikcorporation.movies.utils.enums.Type

interface DatabaseEntity

@Entity(tableName = "movie")
data class DatabaseMovie(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "rating") val rating: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
): DatabaseEntity

@Entity(tableName = "tvShow")
data class DatabaseTVShow(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "rating") val rating: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
): DatabaseEntity

fun DatabaseMovie.asDomainModel(): MovieModel =
    MovieModel(
        id = id,
        rank = rank,
        title = title,
        year = year,
        imageUrl = imageUrl,
        rating = rating,
        isFavorite = isFavorite)

fun List<DatabaseMovie>.asDomainModel(): List<MovieModel> = map { it.asDomainModel() }

fun DatabaseTVShow.asDomainModel(): TVShowModel =
    TVShowModel(
        id = id,
        rank = rank,
        title = title,
        year = year,
        imageUrl = imageUrl,
        rating = rating,
        isFavorite = isFavorite)

fun List<DatabaseTVShow>.asDomainModel(): List<TVShowModel> = map { it.asDomainModel() }
