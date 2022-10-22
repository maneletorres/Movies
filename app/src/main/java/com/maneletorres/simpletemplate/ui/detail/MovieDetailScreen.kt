package com.maneletorres.simpletemplate.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.maneletorres.simpletemplate.R
import com.maneletorres.simpletemplate.domain.model.IMDBMovieDetailModel
import com.maneletorres.simpletemplate.ui.detail.MovieDetailViewModel.MovieDetailUiState.*

// TODO 1: check image failed and loading.
// TODO 2: check vertical scrolling when plot is very large.

@Composable
fun MovieDetailScreen(movieDetailViewModel: MovieDetailViewModel) {
    val movieDetailUIState by movieDetailViewModel.movieDetailUIState.collectAsState()
    movieDetailUIState.let {
        when (it) {
            Idle -> {}
            Loading -> MovieDetailLoading()
            is Data -> MovieDetailContent(it.movieDetail)
            Error -> MovieDetailError()
        }
    }
}

@Composable
fun MovieDetailLoading(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MovieDetailContent(movieDetail: IMDBMovieDetailModel) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxHeight()
    ) {
        MovieGeneralInfoSection(movieDetail = movieDetail)
        Spacer(modifier = Modifier.height(8.dp))
        MoviePlotSection(plot = movieDetail.plot)
    }
}

@Composable
fun MovieDetailError() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.movie_detail_error)
        )
    }
}

@Composable
fun MovieGeneralInfoSection(movieDetail: IMDBMovieDetailModel) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        MovieImageSection(imageUrl = movieDetail.image)
        Spacer(modifier = Modifier.width(8.dp))
        MoviePrimaryInfoSection(movieDetail = movieDetail)
    }
}

@Composable
private fun MoviePlotSection(plot: String) {
    Text(
        text = plot,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun MovieImageSection(imageUrl: String) {
    Card(
        border = BorderStroke(1.dp, colorResource(id = R.color.black)),
        modifier = Modifier
            .height(100.dp)
            .width(75.dp)
    ) {
//        val painter = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
//            .data(imageUrl)
//            .size(Size.ORIGINAL)
//            .build())
//
//        when (painter.state) {
//            AsyncImagePainter.State.Empty -> {}
//            is AsyncImagePainter.State.Error -> {}
//            is AsyncImagePainter.State.Loading -> {}
//            is AsyncImagePainter.State.Success -> {}
//        }

        // Image(painter = painter, contentDescription = "")

        AsyncImage(
            model = imageUrl,
            contentDescription = "",
            modifier = Modifier,
            placeholder = painterResource(id = R.drawable.ic_pulp_fiction),
            error = painterResource(id = R.drawable.ic_placeholder),
            onLoading = {
                // TODO:
            },
            onSuccess = {},
            onError = {},
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
            // previewPlaceholder = R.drawable.ic_pulp_fiction,
        )

//        CoilImage(
//            loading = {
//                Box(modifier = Modifier.matchParentSize()) {
//                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//                }
//            },
//            failure = {
//                Image(
//                    painterResource(id = R.drawable.ic_placeholder),
//                    contentDescription = "",
//                )
//            }
//        )
    }
}

@Composable
fun MoviePrimaryInfoSection(movieDetail: IMDBMovieDetailModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        MovieTitle(title = movieDetail.title)
        MovieDirectors(director = movieDetail.directors)
        MovieDetailSecondaryInfo(year = movieDetail.year, runtimeStr = movieDetail.runtimeStr)
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
fun MovieDetailSecondaryInfo(year: String, runtimeStr: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
private fun MovieDetailLoadingPreview() {
    MaterialTheme {
        MovieDetailLoading()
    }
}

@Preview
@Composable
private fun MovieDetailContentPreview() {
    val movieDetail = IMDBMovieDetailModel(
        id = "0",
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
    MaterialTheme {
        MovieDetailContent(movieDetail)
    }
}

@Preview
@Composable
private fun MovieDetailErrorPreview() {
    MaterialTheme {
        MovieDetailError()
    }
}

@Preview
@Composable
private fun MovieGeneralInfoSectionPreview() {
    MaterialTheme {
        MovieGeneralInfoSection(movieDetail = getDummyIMDBMovieDetailModel())
    }
}

@Preview
@Composable
private fun MoviePlotSectionPreview() {
    MaterialTheme {
        MoviePlotSection(plot = stringResource(id = R.string.dummy_movie_plot))
    }
}

@Preview
@Composable
private fun MovieImageSectionPreview() {
    MaterialTheme {
        MovieImageSection(imageUrl = "")
    }
}

@Preview
@Composable
private fun MoviePrimaryInfoSectionPreview() {
    MaterialTheme {
        MoviePrimaryInfoSection(movieDetail = getDummyIMDBMovieDetailModel())
    }
}

@Preview
@Composable
private fun MovieTitlePreview() {
    MaterialTheme {
        MovieTitle(title = stringResource(id = R.string.dummy_movie_title))
    }
}

@Preview
@Composable
private fun MovieDirectorsPreview() {
    MaterialTheme {
        MovieDirectors(director = stringResource(id = R.string.dummy_movie_directors))
    }
}

@Composable
private fun MovieDetailSecondaryInfoPreview() {
    MaterialTheme {
        MovieDetailSecondaryInfo(
            year = stringResource(id = R.string.dummy_movie_year),
            runtimeStr = stringResource(id = R.string.dummy_movie_runtime)
        )
    }
}
// endregion

// region Helper methods
@Composable
private fun getDummyIMDBMovieDetailModel(): IMDBMovieDetailModel = IMDBMovieDetailModel(
    id = "0",
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
//endregion