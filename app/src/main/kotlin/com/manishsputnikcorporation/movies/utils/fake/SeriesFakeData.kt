package com.manishsputnikcorporation.movies.utils.fake

import android.content.Context
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.network.response.*
import com.manishsputnikcorporation.movies.network.util.EMPTY_STRING
import com.manishsputnikcorporation.movies.utils.enums.Type.*

fun fakeSeriesListDto(context: Context) =
    MoviesDto(
        items =
            listOf(
                MovieDto(
                    id = "tt3581920",
                    rank = "1",
                    title = context.resources.getString(R.string.dummy_series_title),
                    year = "2023",
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BZGUzYTI3M2EtZmM0Yy00NGUyLWI4ODEtN2Q3ZGJlYzhhZjU3XkEyXkFqcGdeQXVyNTM0OTY1OQ@@._V1_Ratio0.6716_AL_.jpg",
                    rating = "9.2",
                    type = SERIES),
                MovieDto(
                    id = "tt7335184",
                    rank = "2",
                    title = context.resources.getString(R.string.dummy_series_name_2),
                    year = "2018",
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BZmUxYTllMzEtNmNmMi00OWNkLTg0ZmItZDdkYTJkOTAxZmJlXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_Ratio0.7910_AL_.jpg",
                    rating = "7.7",
                    type = SERIES),
                MovieDto(
                    id = "tt14269590",
                    rank = "3",
                    title = context.resources.getString(R.string.dummy_series_name_3),
                    year = "2023",
                    imageUrl =
                        "https://m.media-amazon.com/images/M/MV5BNDMyNjAxYTQtMjAyNi00NGQ5LWE0YzUtMTEzNWQ5NjYyNmFjXkEyXkFqcGdeQXVyMDA4NzMyOA@@._V1_Ratio0.6716_AL_.jpg",
                    rating = "8.1",
                    type = SERIES),
            ),
        errorMessage = EMPTY_STRING)

fun fakeSeriesDetailDto(context: Context) =
    MovieDetailDto(
        id = "tt3581920",
        title = context.resources.getString(R.string.dummy_series_title),
        originalTitle = EMPTY_STRING,
        fullTitle = context.resources.getString(R.string.dummy_series_full_title),
        type = SERIES.type,
        year = context.resources.getString(R.string.dummy_year_2023),
        image =
            "https://m.media-amazon.com/images/M/MV5BZGUzYTI3M2EtZmM0Yy00NGUyLWI4ODEtN2Q3ZGJlYzhhZjU3XkEyXkFqcGdeQXVyNTM0OTY1OQ@@._V1_Ratio0.6762_AL_.jpg",
        releaseDate = "2023-01-15",
        runtimeMins = null,
        runtimeStr = null,
        plot = context.resources.getString(R.string.dummy_series_plot),
        plotLocal = EMPTY_STRING,
        plotLocalIsRtl = false,
        directors = EMPTY_STRING)
