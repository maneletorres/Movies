package com.manishsputnikcorporation.movies.utils.fake

import com.manishsputnikcorporation.movies.data.local.DatabaseTV
import com.manishsputnikcorporation.movies.domain.model.TVDetailModel
import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.network.response.tv.TVDetailDto
import com.manishsputnikcorporation.movies.network.response.tv.TVDto
import com.manishsputnikcorporation.movies.network.response.tv.TVsDto

// region DTO
val fakeTVDto =
    TVDto(
        id = 60059,
        overview =
            "Six years before Saul Goodman meets Walter White. We meet him when the man who will become Saul Goodman is known as Jimmy McGill, a small-time lawyer searching for his destiny, and, more immediately, hustling to make ends meet. Working alongside, and, often, against Jimmy, is “fixer” Mike Ehrmantraut. The series tracks Jimmy’s transformation into Saul Goodman, the man who puts “criminal” in “criminal lawyer\".",
        popularity = 34.4233,
        posterPath = "/zjg4jpK1Wp2kiRvtt5ND0kznako.jpg",
        releaseDate = "2015-02-08",
        rating = 8.696,
        originalName = "Better Call Saul",
        name = "Better Call Saul",
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
        id = 60059,
        overview =
            "Six years before Saul Goodman meets Walter White. We meet him when the man who will become Saul Goodman is known as Jimmy McGill, a small-time lawyer searching for his destiny, and, more immediately, hustling to make ends meet. Working alongside, and, often, against Jimmy, is “fixer” Mike Ehrmantraut. The series tracks Jimmy’s transformation into Saul Goodman, the man who puts “criminal” in “criminal lawyer\".",
        popularity = 34.4233,
        posterPath = "/zjg4jpK1Wp2kiRvtt5ND0kznako.jpg",
        releaseDate = "2015-02-08",
        rating = 8.696,
        originalName = "Better Call Saul",
        name = "Better Call Saul",
    )
// endregion

// region Database
val fakeDatabaseTV =
    DatabaseTV(
        id = 60059,
        originalName = "Better Call Saul",
        overview =
            "Six years before Saul Goodman meets Walter White. We meet him when the man who will become Saul Goodman is known as Jimmy McGill, a small-time lawyer searching for his destiny, and, more immediately, hustling to make ends meet. Working alongside, and, often, against Jimmy, is “fixer” Mike Ehrmantraut. The series tracks Jimmy’s transformation into Saul Goodman, the man who puts “criminal” in “criminal lawyer\".",
        popularity = 34.4233,
        posterPath = "/zjg4jpK1Wp2kiRvtt5ND0kznako.jpg",
        releaseDate = "2015-02-08",
        name = "Better Call Saul",
        rating = 8.696,
        isFavorite = true,
    )

val fakeDatabaseTVs: List<DatabaseTV> =
    listOf(
        fakeDatabaseTV,
        DatabaseTV(
            id = 1396,
            originalName = "Breaking Bad",
            overview =
                "Walter White, a New Mexico chemistry teacher, is diagnosed with Stage III cancer and given a prognosis of only two years left to live. He becomes filled with a sense of fearlessness and an unrelenting desire to secure his family's financial future at any cost as he enters the dangerous world of drugs and crime.",
            popularity = 101.7799,
            posterPath = "/ztkUQFLlC19CCMYHW9o1zWhJRNq.jpg",
            releaseDate = "2008-01-20",
            name = "Breaking Bad",
            rating = 8.9,
            isFavorite = false,
        ),
    )
// endregion

// region Model
val fakeTVModel =
    TVModel(
        id = 60059,
        overview =
            "Six years before Saul Goodman meets Walter White. We meet him when the man who will become Saul Goodman is known as Jimmy McGill, a small-time lawyer searching for his destiny, and, more immediately, hustling to make ends meet. Working alongside, and, often, against Jimmy, is “fixer” Mike Ehrmantraut. The series tracks Jimmy’s transformation into Saul Goodman, the man who puts “criminal” in “criminal lawyer\".",
        popularity = 34.4233,
        posterPath = "/zjg4jpK1Wp2kiRvtt5ND0kznako.jpg",
        releaseDate = "2015-02-08",
        rating = 8.696,
        isFavorite = true,
        originalName = "Better Call Saul",
        name = "Better Call Saul",
    )
val fakeTVModels = listOf(fakeTVModel)

val fakeTVDetailModel =
    TVDetailModel(
        id = 60059,
        overview =
            "Six years before Saul Goodman meets Walter White. We meet him when the man who will become Saul Goodman is known as Jimmy McGill, a small-time lawyer searching for his destiny, and, more immediately, hustling to make ends meet. Working alongside, and, often, against Jimmy, is “fixer” Mike Ehrmantraut. The series tracks Jimmy’s transformation into Saul Goodman, the man who puts “criminal” in “criminal lawyer\".",
        popularity = 34.4233,
        posterPath = "/zjg4jpK1Wp2kiRvtt5ND0kznako.jpg",
        releaseDate = "2015-02-08",
        rating = 8.696,
        isFavorite = true,
        originalName = "Better Call Saul",
        name = "Better Call Saul",
    )
// endregion
