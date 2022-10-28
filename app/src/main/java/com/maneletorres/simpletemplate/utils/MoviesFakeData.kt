package com.maneletorres.simpletemplate.utils

import com.maneletorres.simpletemplate.network.response.MovieDetailDto
import com.maneletorres.simpletemplate.network.response.MovieDto
import com.maneletorres.simpletemplate.network.response.MoviesDto

val fakeMoviesDto = MoviesDto(
    items = listOf(
        MovieDto(
            id = "tt11909878",
            rank = "1",
            rankUpDown = "+2",
            title = "Hocus Pocus 2",
            fullTitle = "Hocus Pocus 2 (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BODcxYzI1MTktZWYzNC00MGZlLWI3ZjYtNzhmM2IyOTlkNjYyXkEyXkFqcGdeQXVyMTA1OTcyNDQ4._V1_Ratio0.6716_AL_.jpg",
            crew = "Anne Fletcher (dir.), Bette Midler, Sarah Jessica Parker",
            imdbRating = "6.1",
            imdbRatingCount = "30321"
        ),
        MovieDto(
            id = "tt1655389",
            rank = "2",
            rankUpDown = "-1",
            title = "Blonde",
            fullTitle = "Blonde (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BNDk2YTA1MGYtMGNjMi00YTJlLWI1YjItMjBjOGJlZGIwZmYzXkEyXkFqcGdeQXVyODA0MjgyNzM@._V1_Ratio0.6716_AL_.jpg",
            crew = "Andrew Dominik (dir.), Ana de Armas, Lily Fisher",
            imdbRating = "5.5",
            imdbRatingCount = "39717"
        ),
        MovieDto(
            id = "tt0107120",
            rank = "3",
            rankUpDown = "+3",
            title = "Hocus Pocus",
            fullTitle = "Hocus Pocus (1993)",
            year = "1993",
            image = "https://m.media-amazon.com/images/M/MV5BNWM3NmRhMGMtOGE4ZC00MDk0LWI1NjMtMjVlNTEwOTcwZTc0XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_Ratio0.6716_AL_.jpg",
            crew = "Kenny Ortega (dir.), Bette Midler, Sarah Jessica Parker",
            imdbRating = "6.9",
            imdbRatingCount = "135580"
        ),
        MovieDto(
            id = "tt15474916",
            rank = "4",
            rankUpDown = "+3",
            title = "Smile",
            fullTitle = "Smile (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BZjE2ZWIwMWEtNGFlMy00ZjYzLWEzOWEtYzQ0MDAwZDRhYzNjXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_Ratio0.6716_AL_.jpg\",\n" +
                    "\"crew\":\"Parker Finn (dir.), Sosie Bacon, Jessie T. Usher",
            crew = "Parker Finn (dir.), Sosie Bacon, Jessie T. Usher",
            imdbRating = "6.9",
            imdbRatingCount = "22693"
        ),
        MovieDto(
            id = "tt6718170",
            rank = "5",
            rankUpDown = "+364",
            title = "The Super Mario Bros. Movie",
            fullTitle = "The Super Mario Bros. Movie (2023)",
            year = "2023",
            image = "https://m.media-amazon.com/images/M/MV5BYjY5MTYwMDYtNDk4OS00NmE1LWI2ZjItY2Q5ZmVmNTU4NTAyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_Ratio0.6716_AL_.jpg",
            crew = "Aaron Horvath (dir.), Chris Pratt, Anya Taylor-Joy",
            imdbRating = "",
            imdbRatingCount = "0"
        ),
        MovieDto(
            id = "tt12593682",
            rank = "6",
            rankUpDown = "-1",
            title = "Bullet Train",
            fullTitle = "Bullet Train (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BMDU2ZmM2OTYtNzIxYy00NjM5LTliNGQtN2JmOWQzYTBmZWUzXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_Ratio0.6716_AL_.jpg",
            crew = "David Leitch (dir.), Brad Pitt, Joey King",
            imdbRating = "7.4",
            imdbRatingCount = "135596"
        ),
        MovieDto(
            id = "tt10731256",
            rank = "7",
            rankUpDown = "-5",
            title = "Don't Worry Darling",
            fullTitle = "Don't Worry Darling (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BMzFkMWUzM2ItZWFjMi00NDY0LTk2MDMtZDhkMDE2MjRlYmZlXkEyXkFqcGdeQXVyNTAzNzgwNTg@._V1_Ratio0.6716_AL_.jpg",
            crew = "Olivia Wilde (dir.), Florence Pugh, Harry Styles",
            imdbRating = "6.3",
            imdbRatingCount = "31807"
        ),
        MovieDto(
            id = "tt9114286",
            rank = "8",
            rankUpDown = "+85",
            title = "Black Panther: Wakanda Forever",
            fullTitle = "Black Panther: Wakanda Forever (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BNTM4NjIxNmEtYWE5NS00NDczLTkyNWQtYThhNmQyZGQzMjM0XkEyXkFqcGdeQXVyODk4OTc3MTY@._V1_Ratio0.6716_AL_.jpg",
            crew = "Ryan Coogler (dir.), Tenoch Huerta, Angela Bassett",
            imdbRating = "",
            imdbRatingCount = "0"
        ),
        MovieDto(
            id = "tt10304142",
            rank = "9",
            rankUpDown = "+7",
            title = "Amsterdam",
            fullTitle = "Amsterdam (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BNDQwNzE0ZTItYmZjMC00NjI3LWFlNzctNTExZDY2NWE0Zjc0XkEyXkFqcGdeQXVyMTA3MDk2NDg2._V1_Ratio0.6716_AL_.jpg",
            crew = "David O. Russell (dir.), Christian Bale, Margot Robbie",
            imdbRating = "6.2",
            imdbRatingCount = "8213"
        ),
        MovieDto(
            id = "tt0887261",
            rank = "10",
            rankUpDown = "+43",
            title = "Hellraiser",
            fullTitle = "Hellraiser (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BYWM1YTgxNjMtNzY2NC00YjVmLWE1ODUtNTdiYTI4YjZhODMwXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_Ratio0.6716_AL_.jpg",
            crew = "David Bruckner (dir.), Odessa A’zion, Jamie Clayton",
            imdbRating = "6.1",
            imdbRatingCount = "18575"
        ),
        MovieDto(
            id = "tt9731598",
            rank = "11",
            rankUpDown = "+6",
            title = "Bros",
            fullTitle = "Bros (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BMzI4MDg3NDEtOTg1OC00MDdjLWEzY2QtMmFmMWNlNTcxZD 2022-10-16 21:23:28.765 31010-31041 okhttp.OkHttpClient com.maneletorres.simpletemplate I IxXkEyXkFqcGdeQXVyMjMxOTE0ODA@._V1_Ratio0.7910_AL_.jpg",
            crew = "Nicholas Stoller (dir.), Billy Eichner, Luke Macfarlane",
            imdbRating = "6.9",
            imdbRatingCount = "10656"
        ),
        MovieDto(
            id = "tt12908110",
            rank = "12",
            rankUpDown = "+338",
            title = "Mr. Harrigan's Phone",
            fullTitle = "Mr. Harrigan's Phone (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BMmY3NGYzZGUtZDJjMS00NmI1LTgzY2ItODEyODcxNmI5MzkxXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_Ratio0.6716_AL_.jpg",
            crew = "John Lee Hancock (dir.), Donald Sutherland, Jaeden Martell",
            imdbRating = "6.0",
            imdbRatingCount = "14896"
        ),
        MovieDto(
            id = "tt14813212",
            rank = "13",
            rankUpDown = "-5",
            title = "The Munsters",
            fullTitle = "The Munsters (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BYmYyYWQ2ZmEtYTEzZi00OGExLWE0YTItOGYyODBhY2M4NDI1XkEyXkFqcGdeQXVyNTYzMTc4MzY@._V1_Ratio0.7015_AL_.jpg",
            crew = "Rob Zombie (dir.), Sheri Moon Zombie, Jeff Daniel Phillips",
            imdbRating = "4.6",
            imdbRatingCount = "6990"
        ),
        MovieDto(
            id = "tt10268488",
            rank = "14",
            rankUpDown = "+18",
            title = "The Greatest Beer Run Ever",
            fullTitle = "The Greatest Beer Run Ever (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BMWUwOThjYTAtZWYyYy00YjllLTkxYjEtNTJmNTI5N2M1NjkxXkEyXkFqcGdeQXVyOTU0NjY1MDM@._V1_Ratio0.6716_AL_.jpg",
            crew = "Peter Farrelly (dir.), Zac Efron, Russell Crowe",
            imdbRating = "6.8",
            imdbRatingCount = "9699"
        ),
        MovieDto(
            id = "tt5315210",
            rank = "15",
            rankUpDown = "-11",
            title = "Lou",
            fullTitle = "Lou (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BYzdjMDViYTktM2QyZS00ZGNmLWFlMTctYTliZTUwMWM3YTMwXkEyXkFqcGdeQXVyMTE5MTg5NDIw._V1_Ratio0.6716_AL_.jpg",
            crew = "Anna Foerster (dir.), Allison Janney, Jurnee Smollett",
            imdbRating = "6.1",
            imdbRatingCount = "17014"
        ),
        MovieDto(
            id = "tt4595186",
            rank = "16",
            rankUpDown = "+376",
            title = "Luckiest Girl Alive",
            fullTitle = "Luckiest Girl Alive (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BNzJlMTE3ODEtODRjOS00Zjg4LWE0ZTYtODAwMWMwNjFmZmQyXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_Ratio0.6716_AL_.jpg",
            crew = "Mike Barker (dir.), Mila Kunis, Chiara Aurelia",
            imdbRating = "6.4",
            imdbRatingCount = "18244"
        ),
        MovieDto(
            id = "tt1745960",
            rank = "17",
            rankUpDown = "-6",
            title = "Top Gun: Maverick",
            fullTitle = "Top Gun: Maverick (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BZWYzOGEwNTgtNWU3NS00ZTQ0LWJkODUtMmVhMjIwMjA1ZmQwXkEyXkFqcGdeQXVyMjkwOTAyMDU@._V1_Ratio0.6716_AL_.jpg",
            crew = "Joseph Kosinski (dir.), Tom Cruise, Jennifer Connelly",
            imdbRating = "8.4",
            imdbRatingCount = "399426"
        ),
        MovieDto(
            id = "tt13560574",
            rank = "18",
            rankUpDown = "-4",
            title = "X",
            fullTitle = "X (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BMTJiMmE5YWItOWZjYS00YTg0LWE0MTYtMzg2ZTY4YjNkNDEzXkEyXkFqcGdeQXVyMTAzMDg4NzU0._V1_Ratio0.7910_AL_.jpg",
            crew = "Ti West (dir.), Mia Goth, Jenna Ortega",
            imdbRating = "6.6",
            imdbRatingCount = "74424"
        ),
        MovieDto(
            id = "tt10242388",
            rank = "19",
            rankUpDown = "+392",
            title = "Last Seen Alive",
            fullTitle = "Last Seen Alive (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BODk4MzkxODgtODEwZi00YTAwLWJjYzUtNjRkYjEzY2ZmNzYxXkEyXkFqcGdeQXVyMDEyNTM2Mg@@._V1_Ratio0.7015_AL_.jpg",
            crew = "Brian Goodman (dir.), Gerard Butler, Jaimie Alexander",
            imdbRating = "5.6",
            imdbRatingCount = "14951"
        ),
        MovieDto(
            id = "tt8093700",
            rank = "20",
            rankUpDown = "-5",
            title = "The Woman King",
            fullTitle = "The Woman King (2022)",
            year = "2022",
            image = "https://m.media-amazon.com/images/M/MV5BY2I4MDIwYWUtOWMxNC00ZTIzLWE3OGYtOWUyMmIwZGE2NjU4XkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_Ratio0.7910_AL_.jpg",
            crew = "Gina Prince-Bythewood (dir.), Viola Davis, Thuso Mbedu",
            imdbRating = "6.7",
            imdbRatingCount = "19589"
        )
    ),
    errorMessage = ""
)

val fakeMovieDetailDto = MovieDetailDto(
    id = "tt11909878",
    title = "Léon: The Professional",
    originalTitle = "Léon",
    fullTitle = "Léon: The Professional (1994)",
    year = "1994",
    image = "https://m.media-amazon.com/images/M/MV5BODcxYzI1MTktZWYzNC00MGZlLWI3ZjYtNzhmM2IyOTlkNjYyXkEyXkFqcGdeQXVyMTA1OTcyNDQ4._V1_Ratio0.6716_AL_.jpg",
    releaseDate = "1994–11–18",
    runtimeMins = "",
    runtimeStr = "1h 50 min",
    plot = "12-year-old Mathilda is reluctantly taken in by Léon, a professional assassin, after her family is murdered. An unusual relationship forms as she becomes his protégée and learns the assassin… Read all",
    plotLocal = "",
    plotLocalIsRtl = "",
    directors = "Luc Besson"
)