package com.maneletorres.simpletemplate.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.maneletorres.simpletemplate.ui.home.MoviesHomeViewModel.MoviesHomeUiState.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.maneletorres.simpletemplate.R
import com.maneletorres.simpletemplate.domain.model.MovieModel
import com.maneletorres.simpletemplate.network.model.MovieDtoMapper
import com.maneletorres.simpletemplate.ui.generic.GenericError
import com.maneletorres.simpletemplate.ui.generic.GenericLoading
import com.maneletorres.simpletemplate.ui.generic.GenericPlaceholder
import com.maneletorres.simpletemplate.ui.theme.SimpleTemplateTheme
import com.maneletorres.simpletemplate.utils.fakeMoviesDto
import kotlinx.coroutines.launch

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
            is Data -> {
                MoviesHomeContent(movies = it.movieList) { movieId ->
                    onClickMovie(movieId)
                }
            }
            Error -> GenericError(errorMessage = R.string.movies_home_error)
        }
    }
}

@Composable
fun MoviesHomeContent(
    movies: List<MovieModel>,
    modifier: Modifier = Modifier,
    onClickMovie: (String) -> Unit
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box {
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
    onClickMovie: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        state = gridState
    ) {
        items(movies, key = { it.id }) { movie ->
            val rememberedMovie = rememberSaveable { movie } // TODO: to check
            Movie(rememberedMovie) { movieId ->
                onClickMovie(movieId)
            }
        }
    }
}

@Composable
fun Movie(
    movie: MovieModel,
    onClickMovie: (String) -> Unit
) {
    SubcomposeAsyncImage(
        model = movie.image,
        contentDescription = "",
        modifier = Modifier
            .height(200.dp)
            .clickable {
                onClickMovie(movie.id)
            },
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
    SimpleTemplateTheme {
        val movies = fakeMoviesDto.items.map {  MovieDtoMapper().mapToDomainModel(it) }
        MoviesHomeContent(movies = movies) {}
    }
}

// TODO: to check!!!
@Preview
@Composable
private fun MoviesGridPreview() {
    SimpleTemplateTheme {
        val movies = fakeMoviesDto.items.map {  MovieDtoMapper().mapToDomainModel(it) }
        MoviesGrid(movies = movies) {}
    }
}

// TODO: to check!!!
@Preview
@Composable
private fun MoviePreview() {
    SimpleTemplateTheme {
        val movie = MovieDtoMapper().mapToDomainModel(fakeMoviesDto.items[0])
        Movie(movie) {}
    }
}
// endregion