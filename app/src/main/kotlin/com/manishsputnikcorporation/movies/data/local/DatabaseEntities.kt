package com.manishsputnikcorporation.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manishsputnikcorporation.movies.domain.model.MediaItemModel

interface DatabaseEntity

@Entity(tableName = "movies")
data class DatabaseMovie(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "rating") val rating: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
) : DatabaseEntity

fun DatabaseMovie.asDomainModel(): MediaItemModel =
    MediaItemModel(
        id = id,
        rank = rank,
        title = title,
        year = year,
        imageUrl = imageUrl,
        rating = rating,
        isFavorite = isFavorite)

@Entity(tableName = "tv_shows")
data class DatabaseTvShow(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "rating") val rating: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
) : DatabaseEntity

fun DatabaseTvShow.asDomainModel(): MediaItemModel =
    MediaItemModel(
        id = id,
        rank = rank,
        title = title,
        year = year,
        imageUrl = imageUrl,
        rating = rating,
        isFavorite = isFavorite)

fun <T : DatabaseEntity> List<T>.asDomainModel(): List<MediaItemModel> = map {
  when (it) {
    is DatabaseMovie -> it.asDomainModel()
    is DatabaseTvShow -> it.asDomainModel()
    else -> throw Exception() // TODO: to enhance this mechanism!
  }
}
