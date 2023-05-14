package com.manishsputnikcorporation.movies.utils.fake

import android.content.Context
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.network.response.*
import com.manishsputnikcorporation.movies.network.util.EMPTY_STRING
import com.manishsputnikcorporation.movies.utils.enums.Type.*

fun fakeMovieListDto(context: Context) =
    MoviesDto(
        items =
            listOf(
                MovieDto(
                    id = "tt11909878",
                    rank = "1",
                    title = context.resources.getString(R.string.dummy_movie_name_1),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BODcxYzI1MTktZWYzNC00MGZlLWI3ZjYtNzhmM2IyOTlkNjYyXkEyXkFqcGdeQXVyMTA1OTcyNDQ4._V1_Ratio0.6716_AL_.jpg",
                    rating = "6.1",
                    type = MOVIE),
                MovieDto(
                    id = "tt1655389",
                    rank = "2",
                    title = context.resources.getString(R.string.dummy_movie_name_2),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BNDk2YTA1MGYtMGNjMi00YTJlLWI1YjItMjBjOGJlZGIwZmYzXkEyXkFqcGdeQXVyODA0MjgyNzM@._V1_Ratio0.6716_AL_.jpg",
                    rating = "5.5",
                    type = MOVIE),
                MovieDto(
                    id = "tt0107120",
                    rank = "3",
                    title = context.resources.getString(R.string.dummy_movie_name_3),
                    year = "1993",
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BNWM3NmRhMGMtOGE4ZC00MDk0LWI1NjMtMjVlNTEwOTcwZTc0XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_Ratio0.6716_AL_.jpg",
                    rating = "6.9",
                    type = MOVIE),
                MovieDto(
                    id = "tt15474916",
                    rank = "4",
                    title = context.resources.getString(R.string.dummy_movie_name_4),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BZjE2ZWIwMWEtNGFlMy00ZjYzLWEzOWEtYzQ0MDAwZDRhYzNjXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_Ratio0.6716_AL_.jpg",
                    rating = "6.9",
                    type = MOVIE),
                MovieDto(
                    id = "tt6718170",
                    rank = "5",
                    title =  context.resources.getString(R.string.dummy_movie_name_5),
                    year = "2023",
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BYjY5MTYwMDYtNDk4OS00NmE1LWI2ZjItY2Q5ZmVmNTU4NTAyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_Ratio0.6716_AL_.jpg",
                    rating = EMPTY_STRING,
                    type = MOVIE),
                MovieDto(
                    id = "tt12593682",
                    rank = "6",
                    title = context.resources.getString(R.string.dummy_movie_name_6),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BMDU2ZmM2OTYtNzIxYy00NjM5LTliNGQtN2JmOWQzYTBmZWUzXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_Ratio0.6716_AL_.jpg",
                    rating = "7.4",
                    type = MOVIE),
                MovieDto(
                    id = "tt10731256",
                    rank = "7",
                    title = context.resources.getString(R.string.dummy_movie_name_7),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BMzFkMWUzM2ItZWFjMi00NDY0LTk2MDMtZDhkMDE2MjRlYmZlXkEyXkFqcGdeQXVyNTAzNzgwNTg@._V1_Ratio0.6716_AL_.jpg",
                    rating = "6.3",
                    type = MOVIE),
                MovieDto(
                    id = "tt9114286",
                    rank = "8",
                    title = context.resources.getString(R.string.dummy_movie_name_8),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BNTM4NjIxNmEtYWE5NS00NDczLTkyNWQtYThhNmQyZGQzMjM0XkEyXkFqcGdeQXVyODk4OTc3MTY@._V1_Ratio0.6716_AL_.jpg",
                    rating = EMPTY_STRING,
                    type = MOVIE),
                MovieDto(
                    id = "tt10304142",
                    rank = "9",
                    title = context.resources.getString(R.string.dummy_movie_name_9),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BNDQwNzE0ZTItYmZjMC00NjI3LWFlNzctNTExZDY2NWE0Zjc0XkEyXkFqcGdeQXVyMTA3MDk2NDg2._V1_Ratio0.6716_AL_.jpg",
                    rating = "6.2",
                    type = MOVIE),
                MovieDto(
                    id = "tt0887261",
                    rank = "10",
                    title = context.resources.getString(R.string.dummy_movie_name_10),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BYWM1YTgxNjMtNzY2NC00YjVmLWE1ODUtNTdiYTI4YjZhODMwXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_Ratio0.6716_AL_.jpg",
                    rating = "6.1",
                    type = MOVIE),
                MovieDto(
                    id = "tt9731598",
                    rank = "11",
                    title = context.resources.getString(R.string.dummy_movie_name_11),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl = "Wrong_image_url.jpg",
                    rating = "6.9",
                    type = MOVIE),
                MovieDto(
                    id = "tt12908110",
                    rank = "12",
                    title = context.resources.getString(R.string.dummy_movie_name_12),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BMmY3NGYzZGUtZDJjMS00NmI1LTgzY2ItODEyODcxNmI5MzkxXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_Ratio0.6716_AL_.jpg",
                    rating = "6.0",
                    type = MOVIE),
                MovieDto(
                    id = "tt14813212",
                    rank = "13",
                    title = context.resources.getString(R.string.dummy_movie_name_13),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BYmYyYWQ2ZmEtYTEzZi00OGExLWE0YTItOGYyODBhY2M4NDI1XkEyXkFqcGdeQXVyNTYzMTc4MzY@._V1_Ratio0.7015_AL_.jpg",
                    rating = "4.6",
                    type = MOVIE),
                MovieDto(
                    id = "tt10268488",
                    rank = "14",
                    title = context.resources.getString(R.string.dummy_movie_name_14),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BMWUwOThjYTAtZWYyYy00YjllLTkxYjEtNTJmNTI5N2M1NjkxXkEyXkFqcGdeQXVyOTU0NjY1MDM@._V1_Ratio0.6716_AL_.jpg",
                    rating = "6.8",
                    type = MOVIE),
                MovieDto(
                    id = "tt5315210",
                    rank = "15",
                    title = context.resources.getString(R.string.dummy_movie_name_15),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BYzdjMDViYTktM2QyZS00ZGNmLWFlMTctYTliZTUwMWM3YTMwXkEyXkFqcGdeQXVyMTE5MTg5NDIw._V1_Ratio0.6716_AL_.jpg",
                    rating = "6.1",
                    type = MOVIE),
                MovieDto(
                    id = "tt4595186",
                    rank = "16",
                    title = context.resources.getString(R.string.dummy_movie_name_16),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BNzJlMTE3ODEtODRjOS00Zjg4LWE0ZTYtODAwMWMwNjFmZmQyXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_Ratio0.6716_AL_.jpg",
                    rating = "6.4",
                    type = MOVIE),
                MovieDto(
                    id = "tt1745960",
                    rank = "17",
                    title = context.resources.getString(R.string.dummy_movie_name_17),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BZWYzOGEwNTgtNWU3NS00ZTQ0LWJkODUtMmVhMjIwMjA1ZmQwXkEyXkFqcGdeQXVyMjkwOTAyMDU@._V1_Ratio0.6716_AL_.jpg",
                    rating = "8.4",
                    type = MOVIE),
                MovieDto(
                    id = "tt13560574",
                    rank = "18",
                    title = context.resources.getString(R.string.dummy_movie_name_18),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BMTJiMmE5YWItOWZjYS00YTg0LWE0MTYtMzg2ZTY4YjNkNDEzXkEyXkFqcGdeQXVyMTAzMDg4NzU0._V1_Ratio0.7910_AL_.jpg",
                    rating = "6.6",
                    type = MOVIE),
                MovieDto(
                    id = "tt10242388",
                    rank = "19",
                    title = context.resources.getString(R.string.dummy_movie_name_19),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BODk4MzkxODgtODEwZi00YTAwLWJjYzUtNjRkYjEzY2ZmNzYxXkEyXkFqcGdeQXVyMDEyNTM2Mg@@._V1_Ratio0.7015_AL_.jpg",
                    rating = "5.6",
                    type = MOVIE),
                MovieDto(
                    id = "tt8093700",
                    rank = "20",
                    title = context.resources.getString(R.string.dummy_movie_name_20),
                    year = context.resources.getString(R.string.dummy_year_2022),
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BY2I4MDIwYWUtOWMxNC00ZTIzLWE3OGYtOWUyMmIwZGE2NjU4XkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_Ratio0.7910_AL_.jpg",
                    rating = "6.7",
                    type = MOVIE)),
        errorMessage = EMPTY_STRING)

fun fakeMovieDetailDto(context: Context) =
    MovieDetailDto(
        id = "tt11909878",
        title = context.resources.getString(R.string.dummy_movie_title),
        originalTitle = context.resources.getString(R.string.dummy_movie_original_title),
        fullTitle = context.resources.getString(R.string.dummy_movie_full_title),
        type = MOVIE.type,
        year = context.resources.getString(R.string.dummy_movie_year),
        image =
            "https://m.media-amazon.com/images/M/MV5BODcxYzI1MTktZWYzNC00MGZlLWI3ZjYtNzhmM2IyOTlkNjYyXkEyXkFqcGdeQXVyMTA1OTcyNDQ4._V1_Ratio0.6716_AL_.jpg",
        releaseDate = "1994–11–18",
        runtimeMins = EMPTY_STRING,
        runtimeStr = "1h 50 min",
        plot = context.resources.getString(R.string.dummy_movie_plot),
        plotLocal = EMPTY_STRING,
        plotLocalIsRtl = false,
        directors = context.resources.getString(R.string.dummy_movie_directors))
