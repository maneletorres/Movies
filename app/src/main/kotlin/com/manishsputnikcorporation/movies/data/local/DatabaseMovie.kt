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
    @ColumnInfo(name = "type") val type: Type
)

fun List<DatabaseMovie>.asDomainModel(): List<MovieModel> {
  return map {
    MovieModel(id = it.id,
        rank = it.rank,
        title = it.title,
        year = it.year,
        imageUrl = it.imageUrl,
        rating = it.rating,
        type = it.type
    )
  }
}
