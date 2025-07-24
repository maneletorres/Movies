package com.manishsputnikcorporation.movies.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import com.manishsputnikcorporation.movies.network.util.TMDB_IMAGE_API_BASE_URL
import com.manishsputnikcorporation.movies.ui.navigation.MovieDetail
import com.manishsputnikcorporation.movies.ui.screens.common.EmptyContent
import com.manishsputnikcorporation.movies.ui.screens.common.Loading
import com.manishsputnikcorporation.movies.ui.screens.common.Placeholder
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailViewModel.MovieDetailUiState
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailViewModel.MovieDetailUiState.MovieDetailData
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailDto
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager

@Composable
fun MovieDetailScreen(
    analyticsManager: AnalyticsManager,
    movieDetailViewModel: MovieDetailViewModel,
    movieId: String?,
    onBackArrowClick: () -> Unit = {},
) {
    analyticsManager.trackScreen(screenName = MovieDetail.route)

    val movieDetailUiState by movieDetailViewModel.movieDetailUiState.collectAsState()
    movieDetailUiState.let { state ->
        when {
            state.isIdle -> movieDetailViewModel.loadData(movieId)
            else ->
                MovieDetailContent(
                    state = state,
                    onBackArrowClick = { onBackArrowClick() },
                    onFavoriteMovieClick = { movieId?.let { movieDetailViewModel.setMovieFavoriteStatus(it) } },
                )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailContent(
    state: MovieDetailUiState,
    modifier: Modifier = Modifier,
    onBackArrowClick: () -> Unit = {},
    onFavoriteMovieClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.movie_detail_label)) },
                navigationIcon = {
                    IconButton(onClick = { onBackArrowClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.movie_detail_arrow_back_label),
                        )
                    }
                },
            )
        },
        bottomBar = {
            // TODO: implement when new features are introduced
        },
    ) {
        when {
            state.isLoading -> Loading()
            state.movieDetailData.movieDetailModel != null -> {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier =
                        modifier
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .fillMaxHeight()
                            .semantics { contentDescription = "Movie Detail Screen" },
                ) {
                    state.movieDetailData.movieDetailModel.let {
                        MovieOverview(
                            image = it.image,
                            title = it.title,
                            releaseDate = it.releaseDate,
                            runtime = it.runtimeStr,
                            isFavorite = state.movieDetailData.isFavorite,
                        ) {
                            onFavoriteMovieClick()
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = it.plot, modifier = modifier.fillMaxWidth(), style = MaterialTheme.typography.body1)
                    }
                }
            }
            state.isFailed -> EmptyContent(message = R.string.movie_detail_error)
        }
    }
}

@Composable
fun MovieOverview(
    image: String,
    title: String,
    releaseDate: String,
    runtime: String?,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteMovieClick: () -> Unit = {},
) {
    Row(modifier = modifier.fillMaxWidth()) {
        MovieImage(imageUrl = image)
        Spacer(modifier = Modifier.width(8.dp))
        MovieInfo(
            title = title,
            isFavorite = isFavorite,
            releaseDate = releaseDate,
            runtime = runtime,
            onFavoriteMovieClick = onFavoriteMovieClick,
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Composable
private fun MovieImage(imageUrl: String, modifier: Modifier = Modifier) {
    Card(border = BorderStroke(1.dp, colorResource(id = R.color.black)), modifier = modifier.height(100.dp).width(75.dp)) {
        SubcomposeAsyncImage(
            model = "$TMDB_IMAGE_API_BASE_URL$imageUrl",
            contentDescription = null,
            loading = { Loading() },
            error = { Placeholder(modifier = Modifier.width(25.dp).height(25.dp)) },
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
        )
    }
}

@Composable
fun MovieInfo(
    title: String,
    isFavorite: Boolean,
    releaseDate: String,
    runtime: String?,
    modifier: Modifier = Modifier,
    onFavoriteMovieClick: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row {
            Text(text = title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.h6)
            MovieFavorite(isFavorite, onFavoriteMovieClick)
        }
        MovieSecondaryInfo(year = releaseDate, runtime = runtime)
        // TODO: Text(text = budget, style = MaterialTheme.typography.subtitle2)
    }
}

@Composable
fun MovieFavorite(isFavorite: Boolean, onFavoriteMovieClick: () -> Unit = {}) {
    IconButton(modifier = Modifier.then(Modifier.size(24.dp)), onClick = { onFavoriteMovieClick() }) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(id = R.string.movie_detail_favorite_movie),
        )
    }
}

@Composable
fun MovieSecondaryInfo(year: String, runtime: String?, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = year, style = MaterialTheme.typography.subtitle1)
        runtime?.let { Text(text = "$runtime min", style = MaterialTheme.typography.subtitle1) }
    }
}

// region Previews
@UiModePreviews
@Composable
private fun MovieDetailContentPreview() {
    MoviesTheme {
        val movieDetail = MovieDetailDtoMapper().mapToDomainModel(fakeMovieDetailDto)
        MovieDetailContent(state = MovieDetailUiState(movieDetailData = MovieDetailData(movieDetail)))
    }
}

@Preview
@Composable
private fun MovieOverviewPreview() {
    MoviesTheme {
        Surface {
            with(MovieDetailDtoMapper().mapToDomainModel(fakeMovieDetailDto)) {
                MovieOverview(image = image, title = title, releaseDate = releaseDate, runtime = runtimeStr, isFavorite = false)
            }
        }
    }
}

@Preview
@Composable
private fun MovieInfoPreview() {
    MoviesTheme {
        Surface {
            with(MovieDetailDtoMapper().mapToDomainModel(fakeMovieDetailDto)) {
                MovieInfo(title = title, isFavorite = true, releaseDate = releaseDate, runtime = runtimeStr)
            }
        }
    }
}
// endregion
