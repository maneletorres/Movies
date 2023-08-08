package com.manishsputnikcorporation.movies.utils.fake

import com.manishsputnikcorporation.movies.network.response.imdb.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.imdb.MovieDto
import com.manishsputnikcorporation.movies.network.response.imdb.MoviesDto
import com.manishsputnikcorporation.movies.utils.enums.Type

val fakeSeriesListDto =
    MoviesDto(
        items =
            listOf(
                MovieDto(
                    id = "tt3581920",
                    rank = "1",
                    title = "The Last of Us",
                    year = "2023",
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BZGUzYTI3M2EtZmM0Yy00NGUyLWI4ODEtN2Q3ZGJlYzhhZjU3XkEyXkFqcGdeQXVyNTM0OTY1OQ@@._V1_Ratio0.6716_AL_.jpg",
                    rating = "9.2",
                    type = Type.SERIES),
                MovieDto(
                    id = "tt7335184",
                    rank = "2",
                    title = "You",
                    year = "2018",
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BZmUxYTllMzEtNmNmMi00OWNkLTg0ZmItZDdkYTJkOTAxZmJlXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_Ratio0.7910_AL_.jpg",
                    rating = "7.7",
                    type = Type.SERIES),
                MovieDto(
                    id = "tt14269590",
                    rank = "3",
                    title = "Poker Face",
                    year = "2023",
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BNDMyNjAxYTQtMjAyNi00NGQ5LWE0YzUtMTEzNWQ5NjYyNmFjXkEyXkFqcGdeQXVyMDA4NzMyOA@@._V1_Ratio0.6716_AL_.jpg",
                    rating = "8.1",
                    type = Type.SERIES),
            ),
        errorMessage = "")

val fakeSeriesDetailDto =
    MovieDetailDto(
        id = "tt3581920",
        title = "The Last of Us",
        originalTitle = "",
        fullTitle = "The Last of Us (TV Series 2023– )",
        type = Type.SERIES.type,
        year = "2023",
        image =
            "https://m.media-amazon.com/images/M/MV5BZGUzYTI3M2EtZmM0Yy00NGUyLWI4ODEtN2Q3ZGJlYzhhZjU3XkEyXkFqcGdeQXVyNTM0OTY1OQ@@._V1_Ratio0.6762_AL_.jpg",
        releaseDate = "2023-01-15",
        runtimeMins = null,
        runtimeStr = null,
        plot =
            "After a global pandemic destroys civilization, a hardened survivor takes charge of a 14-year-old girl who may be humanity's last hope.",
        plotLocal = "",
        plotLocalIsRtl = false,
        directors = "")
