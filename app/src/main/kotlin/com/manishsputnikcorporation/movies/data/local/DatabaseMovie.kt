package com.manishsputnikcorporation.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.utils.enums.Type

@Entity(tableName = "movie")
data class DatabaseMovie(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "rating") val rating: String,
    @ColumnInfo(name = "type") val type: Type,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
)

fun DatabaseMovie.asDomainModel(): MovieModel =
    MovieModel(
        id = id,
        rank = rank,
        title = title,
        year = year,
        imageUrl = imageUrl,
        rating = rating,
        type = type,
        isFavorite = isFavorite)

fun List<DatabaseMovie>.asDomainModel(): List<MovieModel> = map { it.asDomainModel() }
