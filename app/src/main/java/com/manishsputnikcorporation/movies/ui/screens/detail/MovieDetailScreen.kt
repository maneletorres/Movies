package com.manishsputnikcorporation.movies.ui.screens.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericLoading
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericPlaceholder
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.fakeMovieDetailDto
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailViewModel.MovieDetailUiState.*
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericError

@Composable
fun MovieDetailScreen(
    movieDetailViewModel: MovieDetailViewModel,
    movieId: String?,
    onBackArrowClick: () -> Unit
) {
    val movieDetailUIState by movieDetailViewModel.movieDetailUIState.collectAsState()
    movieDetailUIState.let {
        when (it) {
            Idle -> movieDetailViewModel.loadMovieDetail(movieId)
            Loading -> GenericLoading()
            is Data -> with (it.movieDetail) {
                MovieDetailContent(image, title, directors, year, plot, runtimeStr) {
                    onBackArrowClick()
                }
            }
            Error -> GenericError(errorMessage = R.string.movie_detail_error)
        }
    }
}

@Composable
fun MovieDetailContent(
    image: String,
    title: String,
    directors: String,
    year: String,
    plot: String,
    runtime: String?,
    modifier: Modifier = Modifier,
    onBackArrowClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar() {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.movie_detail_arrow_back_label),
                    modifier = Modifier.clickable {
                        onBackArrowClick()
                    }
                )
                Spacer(modifier = modifier.width(8.dp))
                Text(stringResource(id = R.string.movie_detail_label))
            }
        },
        bottomBar = {
            // TODO:
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(contentPadding) // TODO: .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxHeight()
                .semantics { contentDescription = "Movie Detail Screen" }
        ) {
            MovieGeneralInfoSection(
                image = image,
                title = title,
                directors = directors,
                year = year,
                runtime = runtime
            )
            Spacer(modifier = Modifier.height(8.dp))
            MoviePlotSection(plot = plot)
        }
    }
}

@Composable
fun MovieGeneralInfoSection(
    image: String,
    title: String,
    directors: String,
    year: String,
    runtime: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        MovieImageSection(imageUrl = image)
        Spacer(modifier = Modifier.width(8.dp))
        MoviePrimaryInfoSection(
            title = title,
            directors = directors,
            year = year,
            runtime = runtime
        )
    }
}

@Composable
private fun MoviePlotSection(
    plot: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = plot,
        style = MaterialTheme.typography.body1,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun MovieImageSection(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(1.dp, colorResource(id = R.color.black)),
        modifier = modifier
            .height(100.dp)
            .width(75.dp)
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "",
            loading = {
                GenericLoading()
            },
            error = {
                GenericPlaceholder(
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                )
            },
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun MoviePrimaryInfoSection(
    title: String,
    directors: String,
    year: String,
    runtime: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        MovieTitle(title = title)
        MovieDirectors(director = directors)
        MovieDetailSecondaryInfo(
            year = year,
            runtime = runtime
        )
    }
}

@Composable
private fun MovieTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h6
    )
}

@Composable
private fun MovieDirectors(director: String) {
    Text(
        text = director,
        style = MaterialTheme.typography.subtitle1,
    )
}

@Composable
fun MovieDetailSecondaryInfo(
    year: String,
    runtime: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MovieYear(year)
        runtime?.let { MovieRuntime(it) }
    }
}

@Composable
private fun MovieYear(year: String) {
    Text(
        text = year,
        style = MaterialTheme.typography.subtitle2,
    )
}

@Composable
private fun MovieRuntime(runtime: String) {
    Text(
        text = runtime,
        style = MaterialTheme.typography.subtitle2
    )
}

// region Previews
@Preview
@Composable
private fun MovieDetailContentPreview() {
    MoviesTheme {
        with (MovieDetailDtoMapper().mapToDomainModel(fakeMovieDetailDto)) {
            MovieDetailContent(
                image = image,
                title = title,
                directors = directors,
                year = year,
                plot = plot,
                runtime = runtimeStr
            )
        }
    }
}

@Preview
@Composable
private fun MovieGeneralInfoSectionPreview() {
    MoviesTheme {
        with (MovieDetailDtoMapper().mapToDomainModel(fakeMovieDetailDto)) {
            MovieGeneralInfoSection(
                image = image,
                title = title,
                directors = directors,
                year = year,
                runtime = runtimeStr
            )
        }
    }
}

@Preview
@Composable
private fun MoviePlotSectionPreview() {
    MoviesTheme {
        MoviePlotSection(plot = stringResource(id = R.string.dummy_movie_plot))
    }
}

// TODO: to check!!!
@Preview
@Composable
private fun MovieImageSectionPreview() {
    MoviesTheme {
        MovieImageSection(imageUrl = "")
    }
}

@Preview
@Composable
private fun MoviePrimaryInfoSectionPreview() {
    MoviesTheme {
        with (MovieDetailDtoMapper().mapToDomainModel(fakeMovieDetailDto)) {
            MoviePrimaryInfoSection(
                title = title,
                directors = directors,
                year = year,
                runtime = runtimeStr
            )
        }
    }
}

@Preview
@Composable
private fun MovieTitlePreview() {
    MoviesTheme {
        MovieTitle(title = stringResource(id = R.string.dummy_movie_title))
    }
}

@Preview
@Composable
private fun MovieDirectorsPreview() {
    MoviesTheme {
        MovieDirectors(director = stringResource(id = R.string.dummy_movie_directors))
    }
}

@Preview
@Composable
private fun MovieDetailSecondaryInfoPreview() {
    MoviesTheme {
        MovieDetailSecondaryInfo(
            year = stringResource(id = R.string.dummy_movie_year),
            runtime = stringResource(id = R.string.dummy_movie_runtime)
        )
    }
}

@Preview
@Composable
private fun MovieYearPreview() {
    MoviesTheme {
        MovieYear(year = stringResource(id = R.string.dummy_movie_year))
    }
}

@Preview
@Composable
private fun MovieRuntimePreview() {
    MoviesTheme {
        MovieRuntime(runtime = stringResource(id = R.string.dummy_movie_runtime))
    }
}
// endregion