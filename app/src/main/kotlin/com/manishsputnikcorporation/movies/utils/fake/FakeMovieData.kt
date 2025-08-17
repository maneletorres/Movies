package com.manishsputnikcorporation.movies.utils.fake

import com.manishsputnikcorporation.movies.data.local.DatabaseMovie
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.movie.MovieDto
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto

// region DTO
val fakeMovieDto =
    MovieDto(
        id = 680,
        originalTitle = "Pulp Fiction",
        overview =
            "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.",
        popularity = 19.6767,
        posterPath = "/vQWk5YBFWF4bZaofAbv0tShwBvQ.jpg",
        releaseDate = "1994-09-10",
        title = "Pulp Fiction",
        rating = 8.487,
    )

// TODO: meter nombres reales de pelis reales con info real
val fakeMoviesDto =
    MoviesDto(
        page = 1,
        results =
            listOf(
                fakeMovieDto,
                MovieDto(
                    id = 667538,
                    title = "Transformers: Rise of the Beasts",
                    originalTitle = "Transformers: Rise of the Beasts",
                    overview =
                        "When a new threat capable of destroying the entire planet emerges, Optimus Prime and the Autobots must team up with a powerful faction known as the Maximals. With the fate of humanity hanging in the balance, humans Noah and Elena will do whatever it takes to help the Transformers as they engage in the ultimate battle to save Earth.",
                    popularity = 16.1688,
                    posterPath = "/gPbM0MK8CP8A174rmUwGsADNYKD.jpg",
                    releaseDate = "2023-06-06",
                    rating = 7.5,
                ),
                MovieDto(
                    id = 298618,
                    title = "The Flash",
                    originalTitle = "The Flash",
                    overview =
                        "When his attempt to save his family inadvertently alters the future, Barry Allen becomes trapped in a reality in which General Zod has returned and there are no Super Heroes to turn to. In order to save the world that he is in and return to the future that he knows, Barry's only hope is to race for his life. But will making the ultimate sacrifice be enough to reset the universe?",
                    popularity = 14.8992,
                    posterPath = "/rktDFPbfHfUbArZ6OOOKsXcv0Bm.jpg",
                    releaseDate = "2023-06-13",
                    rating = 7.0,
                ),
                MovieDto(
                    id = 346698,
                    title = "Barbie",
                    originalTitle = "Barbie",
                    overview =
                        "Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans.",
                    popularity = 19.7305,
                    posterPath = "/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg",
                    releaseDate = "2023-07-19",
                    rating = 7.5,
                ),
                MovieDto(
                    id = 614479,
                    title = "Insidious: The Red Door",
                    originalTitle = "Insidious: The Red Door",
                    overview =
                        "To put their demons to rest once and for all, Josh Lambert and a college-aged Dalton Lambert must go deeper into The Further than ever before, facing their family's dark past and a host of new and more horrifying terrors that lurk behind the red door.",
                    popularity = 7.1331,
                    posterPath = "/d07phJqCx6z5wILDYqkyraorDPi.jpg",
                    releaseDate = "2023-07-05",
                    rating = 6.9,
                ),
                MovieDto(
                    id = 615656,
                    title = "Meg 2: The Trench",
                    originalTitle = "Meg 2: The Trench",
                    overview =
                        "An exploratory dive into the deepest depths of the ocean of a daring research team spirals into chaos when a malevolent mining operation threatens their mission and forces them into a high-stakes battle for survival.",
                    popularity = 15.1422,
                    posterPath = "/4m1Au3YkjqsxF8iwQy0fPYSxE0h.jpg",
                    releaseDate = "2023-08-02",
                    rating = 7.0,
                ),
                MovieDto(
                    id = 1006462,
                    title = "The Flood",
                    originalTitle = "The Flood",
                    overview =
                        "A horde of giant hungry alligators is unleashed on a group of in-transit prisoners and their guards after a massive hurricane floods Louisiana.",
                    popularity = 2.5234,
                    posterPath = "/mvjqqklMpHwOxc40rn7dMhGT0Fc.jpg",
                    releaseDate = "2023-07-14",
                    rating = 6.9,
                ),
                MovieDto(
                    id = 457332,
                    title = "Hidden Strike",
                    originalTitle = "Hidden Strike",
                    overview = "Two elite soldiers must escort civilians through a gauntlet of gunfire and explosions.",
                    popularity = 3.4104,
                    posterPath = "/zsbolOkw8RhTU4DKOrpf4M7KCmi.jpg",
                    releaseDate = "2023-07-06",
                    rating = 7.1,
                ),
                MovieDto(
                    id = 447277,
                    title = "The Little Mermaid",
                    originalTitle = "The Little Mermaid",
                    overview =
                        "The youngest of King Triton’s daughters, and the most defiant, Ariel longs to find out more about the world beyond the sea, and while visiting the surface, falls for the dashing Prince Eric. With mermaids forbidden to interact with humans, Ariel makes a deal with the evil sea witch, Ursula, which gives her a chance to experience life on land, but ultimately places her life – and her father’s crown – in jeopardy.",
                    popularity = 9.2392,
                    posterPath = "/ym1dxyOk4jFcSl4Q2zmRrA5BEEN.jpg",
                    releaseDate = "2023-05-18",
                    rating = 6.5,
                ),
                MovieDto(
                    id = 1149381,
                    title = "Soulcatcher",
                    originalTitle = "Operacja: Soulcatcher",
                    overview =
                        "A military contractor hired to seize a weapon that turns people into savage killers seeks revenge when his brother falls victim to the device.",
                    popularity = 1.3771,
                    posterPath = "/l1f9JSPjCfNftigEii1SDK1g2b3.jpg",
                    releaseDate = "2023-08-02",
                    rating = 6.6,
                ),
            ),
        totalPages = 39558,
        totalResults = 791149,
    )

val fakeMovieDetailDto =
    MovieDetailDto(
        id = 680,
        imdbId = "tt0110912",
        originalTitle = "Pulp Fiction",
        overview =
            "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.",
        popularity = 19.6767,
        posterPath = "/vQWk5YBFWF4bZaofAbv0tShwBvQ.jpg",
        releaseDate = "1994-09-10",
        runtime = 154,
        title = "Pulp Fiction",
        rating = 8.487,
    )
// endregion

// region Database
val fakeDatabaseMovie =
    DatabaseMovie(
        id = 680,
        originalTitle = "Pulp Fiction",
        overview =
            "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.",
        popularity = 19.6767,
        posterPath = "/vQWk5YBFWF4bZaofAbv0tShwBvQ.jpg",
        releaseDate = "1994-09-10",
        title = "Pulp Fiction",
        rating = 8.487,
        isFavorite = true,
    )

val fakeDatabaseMovies =
    listOf(
        fakeDatabaseMovie,
        DatabaseMovie(
            id = 78,
            originalTitle = "Blade Runner",
            overview =
                "In the smog-choked dystopian Los Angeles of 2019, blade runner Rick Deckard is called out of retirement to terminate a quartet of replicants who have escaped to Earth seeking their creator for a way to extend their short life spans.",
            popularity = 9.5075,
            posterPath = "/qTcATCpiFDcgY8snQIfS2j0bFP7.jpg",
            releaseDate = "1982-06-25",
            title = "Blade Runner",
            rating = 7.941,
            isFavorite = false,
        ),
    )
// endregion

// region Model
val fakeMovieModel =
    MovieModel(
        id = 680,
        originalTitle = "Pulp Fiction",
        overview =
            "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.",
        popularity = 19.6767,
        posterPath = "/vQWk5YBFWF4bZaofAbv0tShwBvQ.jpg",
        releaseDate = "1994-09-10",
        title = "Pulp Fiction",
        rating = 8.487,
        isFavorite = true,
    )

val fakeMovieModels = listOf(fakeMovieModel)

val fakeMovieDetailModel =
    MovieDetailModel(
        id = 680,
        imdbId = "tt0110912",
        originalTitle = "Pulp Fiction",
        overview =
            "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.",
        popularity = 19.6767,
        posterPath = "/vQWk5YBFWF4bZaofAbv0tShwBvQ.jpg",
        releaseDate = "1994-09-10",
        runtime = 154,
        title = "Pulp Fiction",
        rating = 8.487,
    )
// endregion
