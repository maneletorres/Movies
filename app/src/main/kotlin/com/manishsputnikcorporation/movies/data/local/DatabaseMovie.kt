package com.manishsputnikcorporation.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manishsputnikcorporation.movies.domain.model.MovieModel

@Entity(tableName = "movie")
data class DatabaseMovie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
//    @ColumnInfo(name = "release_date")
//    val releaseDate: String,
//    @ColumnInfo(name = "runtime")
//    val runtime: String,
//    @ColumnInfo(name = "plot")
//    val plot: String,
//    @ColumnInfo(name = "directors")
//    val directors: String
)

fun List<DatabaseMovie>.asDomainModel(): List<MovieModel> {
  return map {
    MovieModel(id = it.id.toString(), title = it.title, year = it.year, image = it.imageUrl)
  }
}
