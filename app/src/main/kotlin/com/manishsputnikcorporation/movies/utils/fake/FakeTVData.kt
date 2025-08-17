package com.manishsputnikcorporation.movies.utils.fake

import com.manishsputnikcorporation.movies.network.response.tv.TVDetailDto
import com.manishsputnikcorporation.movies.network.response.tv.TVDto
import com.manishsputnikcorporation.movies.network.response.tv.TVsDto

val fakeTVDto = TVDto(
    id = 2734,
    originalName = "Law & Order: Special Victims Unit",
    overview =
        "In the criminal justice system, sexually-based offenses are considered especially heinous. In New York City, the dedicated detectives who investigate these vicious felonies are members of an elite squad known as the Special Victims Unit. These are their stories.",
    popularity = 0.0,
    posterPath = "/ywBt4WKADdMVgxTR1rS2uFwMYTH.jpg",
    releaseDate = "1999-09-20",
    name = "Law & Order: Special Victims Unit",
    rating = 8.0,
)

val fakeTVsDto =
    TVsDto(
        page = 1,
        results =
            listOf(
                fakeTVDto,
                TVDto(
                    id = 112470,
                    name = "Here it all begins",
                    originalName = "Ici tout commence",
                    overview = "",
                    popularity = 0.0,
                    posterPath = "/60cqjI590JKXCAABqCStVmSBGET.jpg",
                    releaseDate = "2020-11-02",
                    rating = 7.3,
                ),
                TVDto(
                    id = 94722,
                    name = "Tagesschau",
                    originalName = "Tagesschau",
                    overview = "German daily news program, the oldest still existing program on German television.",
                    popularity = 0.0,
                    posterPath = "/7dFZJ2ZJJdcmkp05B9NWlqTJ5tq.jpg",
                    releaseDate = "1952-12-26",
                    rating = 7.5,
                ),
            ),
        totalPages = 7676,
        totalResults = 153508,
    )

val fakeTVDetailDto =
    TVDetailDto(
        id = 278,
        imdbId = "tt0111161",
        originalTitle = "The Shawshank Redemption",
        overview = "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
        popularity = 33.283,
        posterPath = "https://m.media-amazon.com/images/M/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
        releaseDate = "1994-09-23",
        runtime = 142,
        title = "The Shawshank Redemption",
        rating = 8.712
    )