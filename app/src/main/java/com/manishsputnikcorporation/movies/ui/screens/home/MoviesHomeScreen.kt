package com.manishsputnikcorporation.movies.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericError
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericLoading
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericPlaceholder
import kotlinx.coroutines.launch
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.fakeMoviesDto
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.*

@Composable
fun MoviesHomeScreen(
    moviesHomeViewModel: MoviesHomeViewModel,
    onClickMovie: (String) -> Unit = {}
) {
    val moviesHomeUIState by moviesHomeViewModel.moviesHomeUIState.collectAsState()
    moviesHomeUIState.let {
        when (it) {
            Idle -> Unit
            Loading -> GenericLoading()
            is Data -> MoviesHomeContent(movies = it.movieList) { movieId -> onClickMovie(movieId) }
            Error -> GenericError(errorMessage = R.string.movies_home_error)
        }
    }
}

@Composable
fun MoviesHomeContent(
    movies: List<MovieModel>,
    modifier: Modifier = Modifier,
    onClickMovie: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        bottomBar = {
            // TODO:
        },
        floatingActionButton = {
            // TODO: to move the showButton logic
        }
    ) { contentPadding ->
        Box(
            modifier = modifier
                .padding(contentPadding)
                .semantics { contentDescription = "Movies Home Screen" }
        ) {
            val gridState = rememberLazyGridState()
            MoviesGrid(movies, gridState = gridState) { movieId ->
                onClickMovie(movieId)
            }

            val showButton by remember {
                derivedStateOf {
                    gridState.firstVisibleItemIndex > 0
                }
            }
            if (showButton) {
                val coroutineScope = rememberCoroutineScope()
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .navigationBarsPadding()
                        .padding(bottom = 8.dp, end = 8.dp),
                    onClick = {
                        coroutineScope.launch {
                            gridState.scrollToItem(0)
                        }
                    }
                ) {
                    Text("Up!")
                }
            }
        }
    }
}

@Composable
fun MoviesGrid(
    movies: List<MovieModel>,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    onClickMovie: (String) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        state = gridState
    ) {
        items(movies, key = { it.id }) { movie ->
            val rememberedMovie = rememberSaveable { movie } // TODO: to check
            with (rememberedMovie) {
                Movie(id, image) { movieId -> onClickMovie(movieId) }
            }
        }
    }
}

@Composable
fun Movie(
    id: String,
    image: String,
    onClickMovie: (String) -> Unit = {}
) {
    SubcomposeAsyncImage(
        model = image,
        contentDescription = "",
        modifier = Modifier
            .height(200.dp)
            .clickable {
                onClickMovie(id)
            }
            .semantics { contentDescription = "Movie Detail" },
        loading = {
            GenericLoading()
        },
        error = {
            GenericPlaceholder(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )
        },
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop
    )
}

// region Previews
// TODO: to check!!!
@Preview
@Composable
private fun MoviesHomeContentPreview() {
    MoviesTheme {
        val movies = fakeMoviesDto.items.map {  MovieDtoMapper().mapToDomainModel(it) }
        MoviesHomeContent(movies = movies)
    }
}

// TODO: to check!!!
@Preview
@Composable
private fun MoviesGridPreview() {
    MoviesTheme {
        val movies = fakeMoviesDto.items.map {  MovieDtoMapper().mapToDomainModel(it) }
        MoviesGrid(movies = movies)
    }
}

// TODO: to check!!!
@Preview
@Composable
private fun MoviePreview() {
    MoviesTheme {
        with (MovieDtoMapper().mapToDomainModel(fakeMoviesDto.items[0])) {
            Movie(id, image)
        }
    }
}
// endregion