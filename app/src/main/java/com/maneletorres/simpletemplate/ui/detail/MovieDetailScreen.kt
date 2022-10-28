package com.maneletorres.simpletemplate.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.maneletorres.simpletemplate.R
import com.maneletorres.simpletemplate.domain.model.MovieDetailModel
import com.maneletorres.simpletemplate.network.model.MovieDetailDtoMapper
import com.maneletorres.simpletemplate.ui.detail.MovieDetailViewModel.MovieDetailUiState.*
import com.maneletorres.simpletemplate.ui.generic.GenericError
import com.maneletorres.simpletemplate.ui.generic.GenericLoading
import com.maneletorres.simpletemplate.ui.generic.GenericPlaceholder
import com.maneletorres.simpletemplate.ui.theme.SimpleTemplateTheme
import com.maneletorres.simpletemplate.utils.fakeMovieDetailDto

@Composable
fun MovieDetailScreen(
    movieDetailViewModel: MovieDetailViewModel,
    movieId: String?
) {
    val movieDetailUIState by movieDetailViewModel.movieDetailUIState.collectAsState()
    movieDetailUIState.let {
        when (it) {
            Idle -> movieDetailViewModel.loadMovieDetail(movieId)
            Loading -> GenericLoading()
            is Data -> MovieDetailContent(it.movieDetail)
            Error -> GenericError(errorMessage = R.string.movie_detail_error)
        }
    }
}

@Composable
fun MovieDetailContent(
    movieDetail: MovieDetailModel,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .verticalScroll(rememberScrollState()) // TODO: check vertical scrolling when plot is very large.
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxHeight()
        ) {
            MovieGeneralInfoSection(movieDetail = movieDetail)
            Spacer(modifier = Modifier.height(8.dp))
            MoviePlotSection(plot = movieDetail.plot)
        }
    }
}

@Composable
fun MovieGeneralInfoSection(
    movieDetail: MovieDetailModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        MovieImageSection(imageUrl = movieDetail.image)
        Spacer(modifier = Modifier.width(8.dp))
        MoviePrimaryInfoSection(movieDetail = movieDetail)
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
    movieDetail: MovieDetailModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        MovieTitle(title = movieDetail.title)
        MovieDirectors(director = movieDetail.directors)
        MovieDetailSecondaryInfo(
            year = movieDetail.year,
            runtimeStr = movieDetail.runtimeStr
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
    runtimeStr: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MovieYear(year)
        runtimeStr?.let { MovieRuntime(it) }
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
    val movieDetail = MovieDetailModel(
        id = stringResource(id = R.string.dummy_movie_id),
        title = stringResource(id = R.string.dummy_movie_title),
        originalTitle = stringResource(id = R.string.dummy_movie_original_title),
        fullTitle = stringResource(id = R.string.dummy_movie_full_title),
        year = stringResource(id = R.string.dummy_movie_year),
        image = "",
        releaseDate = stringResource(id = R.string.dummy_movie_release_date),
        runtimeStr = stringResource(id = R.string.dummy_movie_runtime),
        plot = stringResource(id = R.string.dummy_movie_plot),
        directors = stringResource(id = R.string.dummy_movie_directors)
    )
    SimpleTemplateTheme {
        MovieDetailContent(movieDetail)
    }
}

@Preview
@Composable
private fun MovieGeneralInfoSectionPreview() {
    SimpleTemplateTheme {
        val movieDetail = MovieDetailDtoMapper().mapToDomainModel(fakeMovieDetailDto)
        MovieGeneralInfoSection(movieDetail = movieDetail)
    }
}

@Preview
@Composable
private fun MoviePlotSectionPreview() {
    SimpleTemplateTheme {
        MoviePlotSection(plot = stringResource(id = R.string.dummy_movie_plot))
    }
}

// TODO: to check!!!
@Preview
@Composable
private fun MovieImageSectionPreview() {
    SimpleTemplateTheme {
        MovieImageSection(imageUrl = "")
    }
}

@Preview
@Composable
private fun MoviePrimaryInfoSectionPreview() {
    SimpleTemplateTheme {
        val movieDetail = MovieDetailDtoMapper().mapToDomainModel(fakeMovieDetailDto)
        MoviePrimaryInfoSection(movieDetail = movieDetail)
    }
}

@Preview
@Composable
private fun MovieTitlePreview() {
    SimpleTemplateTheme {
        MovieTitle(title = stringResource(id = R.string.dummy_movie_title))
    }
}

@Preview
@Composable
private fun MovieDirectorsPreview() {
    SimpleTemplateTheme {
        MovieDirectors(director = stringResource(id = R.string.dummy_movie_directors))
    }
}

@Preview
@Composable
private fun MovieDetailSecondaryInfoPreview() {
    SimpleTemplateTheme {
        MovieDetailSecondaryInfo(
            year = stringResource(id = R.string.dummy_movie_year),
            runtimeStr = stringResource(id = R.string.dummy_movie_runtime)
        )
    }
}

@Preview
@Composable
private fun MovieYearPreview() {
    SimpleTemplateTheme {
        MovieYear(year = stringResource(id = R.string.dummy_movie_year))
    }
}

@Preview
@Composable
private fun MovieRuntimePreview() {
    SimpleTemplateTheme {
        MovieRuntime(runtime = stringResource(id = R.string.dummy_movie_runtime))
    }
}
// endregion