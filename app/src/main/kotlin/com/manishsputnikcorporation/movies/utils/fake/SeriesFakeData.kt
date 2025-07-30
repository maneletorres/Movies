package com.manishsputnikcorporation.movies.utils.fake

import com.manishsputnikcorporation.movies.network.response.tvshow.TVShowDetailDto
import com.manishsputnikcorporation.movies.network.response.tvshow.TVShowDto
import com.manishsputnikcorporation.movies.network.response.tvshow.TVShowsDto

val fakeTvShowsDto =
    TVShowsDto(
        page = 1,
        results =
            listOf(
                TVShowDto(
                    id = 2734,
                    title = "Law & Order: Special Victims Unit",
                    originalTitle = "Law & Order: Special Victims Unit",
                    overview =
                        "In the criminal justice system, sexually-based offenses are considered especially heinous. In New York City, the dedicated detectives who investigate these vicious felonies are members of an elite squad known as the Special Victims Unit. These are their stories.",
                    imageUrl = "/ywBt4WKADdMVgxTR1rS2uFwMYTH.jpg",
                    releaseDate = "1999-09-20",
                    rating = 8.0,
                ),
                TVShowDto(
                    id = 112470,
                    title = "Here it all begins",
                    originalTitle = "Ici tout commence",
                    overview = "",
                    imageUrl = "/60cqjI590JKXCAABqCStVmSBGET.jpg",
                    releaseDate = "2020-11-02",
                    rating = 7.3,
                ),
                TVShowDto(
                    id = 94722,
                    title = "Tagesschau",
                    originalTitle = "Tagesschau",
                    overview = "German daily news program, the oldest still existing program on German television.",
                    imageUrl = "/7dFZJ2ZJJdcmkp05B9NWlqTJ5tq.jpg",
                    releaseDate = "1952-12-26",
                    rating = 7.5,
                ),
            ),
        totalPages = 7676,
        totalResults = 153508,
    )

val fakeTvShowsDetailDto =
    TVShowDetailDto(
        id = 13,
        title = "How do you like Wednesday?",
        originalTitle = "水曜どうでしょう",
        image = "/i2MwS6U0XzD8ad6aS3HiTNKz8ov.jpg",
        releaseDate = "1996-10-09",
        runtime = listOf(30),
        overview =
            "How do you like Wednesday? was a Japanese television variety series that aired on the HTB network in Hokkaidō, Japan, and on other regional television networks in Japan. The program debuted on HTB on October 9, 1996. The series was one of the first local variety programs to be produced on Hokkaido; prior to this series' launch, local variety programs in Hokkaidō were virtually non-existent. The program also had a significant influence on other local programs in other regions in Japan, most notably Kwangaku! in Kansai and Nobunaga in Tokai.\\n\\nThe series achieved a record 18.6% viewing share on December 8, 1999, the highest share for a late-night program on a local TV station.\\n\\nProduction of the weekly regular series ended in September 2002, though new limited-run series were produced on average of every 18 months; the latest series was shown on HTB in late 2005, eight episodes in length.\\n\\nMost of the series have been rerun under the names of Dōdeshō Returns and Suiyō Dōdeshō Classic.",
    )
