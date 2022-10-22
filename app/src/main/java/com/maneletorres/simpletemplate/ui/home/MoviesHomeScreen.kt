package com.maneletorres.simpletemplate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.maneletorres.simpletemplate.ui.home.MoviesHomeViewModel.MoviesHomeUiState.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.maneletorres.simpletemplate.R
import com.maneletorres.simpletemplate.domain.model.IMDBMovieModel
import com.maneletorres.simpletemplate.network.model.IMDBMovieDtoMapper
import com.maneletorres.simpletemplate.utils.fakeImdbMoviesDto
import kotlinx.coroutines.launch

@Composable
fun MoviesHomeScreen(moviesHomeViewModel: MoviesHomeViewModel, /* TODO: navController: NavController */) {
    val moviesHomeUIState by moviesHomeViewModel.moviesHomeUIState.collectAsState()
    moviesHomeUIState.let {
        when (it) {
            Idle -> Unit
            Loading -> MoviesHomeLoading()
            is Data -> MoviesHomeContent(movies = it.movieList)
            Error -> MoviesHomeError()
        }
    }
}

@Composable
fun MoviesHomeLoading(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MoviesHomeContent(
    modifier: Modifier = Modifier,
    movies: List<IMDBMovieModel>
) {
    Surface(modifier = modifier.fillMaxSize(), color = Color.White) {
        Box {
            val gridState = rememberLazyGridState()
            MoviesGrid(movies, gridState = gridState)

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
fun MoviesHomeError() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp)
    ) {
        Text(
            text = stringResource(id = R.string.movies_home_error)
        )
    }
}

@Composable
fun MoviesGrid(
    movies: List<IMDBMovieModel>,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState()
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        state = gridState
    ) {
        items(movies, key = { it.id }) { movie ->
            val rememberedMovie = rememberSaveable { movie } // TODO: to check
            Movie(rememberedMovie)
        }
    }
}

@Composable
fun Movie(movie: IMDBMovieModel) {
    SubcomposeAsyncImage(
        model = movie.image,
        contentDescription = "",
        modifier = Modifier
            .height(200.dp)
            .clickable {
                // TODO: click.invoke() or navController.navigate("details")
            },
        loading = {
            MoviesHomeLoading()
        },
        error = {
            MoviePlaceholder(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth())
        },
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MoviePlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_placeholder),
            contentDescription = "",
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        )
    }
}

// region Previews
@Preview
@Composable
private fun MoviesHomeLoadingPreview() {
    MaterialTheme {
        MoviesHomeLoading(modifier = Modifier.fillMaxSize())
    }
}

@Preview
@Composable
private fun MoviesHomeContentPreview() {
    MaterialTheme {
        val movies = fakeImdbMoviesDto.items.map {  IMDBMovieDtoMapper().mapToDomainModel(it) }
        MoviesHomeContent(movies = movies)
    }
}

@Preview
@Composable
private fun MoviesHomeErrorPreview() {
    MaterialTheme {
        MoviesHomeError()
    }
}

@Preview
@Composable
private fun MovieLoadingPreview() {
    MaterialTheme {
        MoviesHomeLoading(modifier = Modifier
            .heightIn(200.dp)
            .widthIn(150.dp))
    }
}

@Preview
@Composable
private fun MoviePreview() {
    MaterialTheme {
        val movie = IMDBMovieDtoMapper().mapToDomainModel(fakeImdbMoviesDto.items[0])
        Movie(movie)
    }
}

@Preview
@Composable
private fun MoviePlaceholderPreview() {
    MaterialTheme {
        MoviePlaceholder(modifier = Modifier
            .heightIn(200.dp)
            .widthIn(150.dp))
    }
}
// endregion