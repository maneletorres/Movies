package com.manishsputnikcorporation.movies.utils.fake

import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.network.response.movie.MovieDto
import com.manishsputnikcorporation.movies.network.response.movie.MoviesDto

val fakeMoviesDto =
    MoviesDto(
        page = 1,
        results =
            listOf(
                MovieDto(
                    id = 569094,
                    title = "Spider-Man: Across the Spider-Verse",
                    originalTitle = "Spider-Man: Across the Spider-Verse",
                    overview =
                        "After reuniting with Gwen Stacy, Brooklyn’s full-time, friendly neighborhood Spider-Man is catapulted across the Multiverse, where he encounters the Spider Society, a team of Spider-People charged with protecting the Multiverse’s very existence. But when the heroes clash on how to handle a new threat, Miles finds himself pitted against the other Spiders and must set out on his own to save those he loves most.",
                    imageUrl = "/8Vt6mWEReuy4Of61Lnj5Xj704m8.jpg",
                    releaseDate = "2023-05-31",
                    rating = 8.5
                ),
                MovieDto(
                    id = 667538,
                    title = "Transformers: Rise of the Beasts",
                    originalTitle = "Transformers: Rise of the Beasts",
                    overview =
                        "When a new threat capable of destroying the entire planet emerges, Optimus Prime and the Autobots must team up with a powerful faction known as the Maximals. With the fate of humanity hanging in the balance, humans Noah and Elena will do whatever it takes to help the Transformers as they engage in the ultimate battle to save Earth.",
                    imageUrl = "/gPbM0MK8CP8A174rmUwGsADNYKD.jpg",
                    releaseDate = "2023-06-06",
                    rating = 7.5
                ),
                MovieDto(
                    id = 298618,
                    title = "The Flash",
                    originalTitle = "The Flash",
                    overview =
                        "When his attempt to save his family inadvertently alters the future, Barry Allen becomes trapped in a reality in which General Zod has returned and there are no Super Heroes to turn to. In order to save the world that he is in and return to the future that he knows, Barry's only hope is to race for his life. But will making the ultimate sacrifice be enough to reset the universe?",
                    imageUrl = "/rktDFPbfHfUbArZ6OOOKsXcv0Bm.jpg",
                    releaseDate = "2023-06-13",
                    rating = 7.0
                ),
                MovieDto(
                    id = 346698,
                    title = "Barbie",
                    originalTitle = "Barbie",
                    overview =
                        "Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans.",
                    imageUrl = "/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg",
                    releaseDate = "2023-07-19",
                    rating = 7.5
                ),
                MovieDto(
                    id = 614479,
                    title = "Insidious: The Red Door",
                    originalTitle = "Insidious: The Red Door",
                    overview =
                        "To put their demons to rest once and for all, Josh Lambert and a college-aged Dalton Lambert must go deeper into The Further than ever before, facing their family's dark past and a host of new and more horrifying terrors that lurk behind the red door.",
                    imageUrl = "/d07phJqCx6z5wILDYqkyraorDPi.jpg",
                    releaseDate = "2023-07-05",
                    rating = 6.9
                ),
                MovieDto(
                    id = 615656,
                    title = "Meg 2: The Trench",
                    originalTitle = "Meg 2: The Trench",
                    overview =
                        "An exploratory dive into the deepest depths of the ocean of a daring research team spirals into chaos when a malevolent mining operation threatens their mission and forces them into a high-stakes battle for survival.",
                    imageUrl = "/4m1Au3YkjqsxF8iwQy0fPYSxE0h.jpg",
                    releaseDate = "2023-08-02",
                    rating = 7.0
                ),
                MovieDto(
                    id = 1006462,
                    title = "The Flood",
                    originalTitle = "The Flood",
                    overview =
                        "A horde of giant hungry alligators is unleashed on a group of in-transit prisoners and their guards after a massive hurricane floods Louisiana.",
                    imageUrl = "/mvjqqklMpHwOxc40rn7dMhGT0Fc.jpg",
                    releaseDate = "2023-07-14",
                    rating = 6.9
                ),
                MovieDto(
                    id = 457332,
                    title = "Hidden Strike",
                    originalTitle = "Hidden Strike",
                    overview = "Two elite soldiers must escort civilians through a gauntlet of gunfire and explosions.",
                    imageUrl = "/zsbolOkw8RhTU4DKOrpf4M7KCmi.jpg",
                    releaseDate = "2023-07-06",
                    rating = 7.1
                ),
                MovieDto(
                    id = 447277,
                    title = "The Little Mermaid",
                    originalTitle = "The Little Mermaid",
                    overview =
                        "The youngest of King Triton’s daughters, and the most defiant, Ariel longs to find out more about the world beyond the sea, and while visiting the surface, falls for the dashing Prince Eric. With mermaids forbidden to interact with humans, Ariel makes a deal with the evil sea witch, Ursula, which gives her a chance to experience life on land, but ultimately places her life – and her father’s crown – in jeopardy.",
                    imageUrl = "/ym1dxyOk4jFcSl4Q2zmRrA5BEEN.jpg",
                    releaseDate = "2023-05-18",
                    rating = 6.5
                ),
                MovieDto(
                    id = 1149381,
                    title = "Soulcatcher",
                    originalTitle = "Operacja: Soulcatcher",
                    overview =
                        "A military contractor hired to seize a weapon that turns people into savage killers seeks revenge when his brother falls victim to the device.",
                    imageUrl = "/l1f9JSPjCfNftigEii1SDK1g2b3.jpg",
                    releaseDate = "2023-08-02",
                    rating = 6.6
                ),
            ),
        totalPages = 39558,
        totalResults = 791149
    )

val fakeMovieDetailDto =
    MovieDetailDto(
        imdbId = "tt0109830",
        id = 13,
        title = "Forrest Gump",
        originalTitle = "Forrest Gump",
        image =
            "https://m.media-amazon.com/images/M/MV5BODcxYzI1MTktZWYzNC00MGZlLWI3ZjYtNzhmM2IyOTlkNjYyXkEyXkFqcGdeQXVyMTA1OTcyNDQ4._V1_Ratio0.6716_AL_.jpg",
        releaseDate = "1994-06-23",
        overview =
            "A man with a low IQ has accomplished great things in his life and been present during significant historic events—in each case, far exceeding what anyone imagined he could do. But despite all he has achieved, his one true love eludes him.",
        runtime = 142
    )
