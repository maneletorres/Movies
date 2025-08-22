@file:OptIn(ExperimentalMaterial3Api::class)

package com.manishsputnikcorporation.movies.ui.screens.detail

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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.domain.model.TVDetailModel
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.util.TMDB_IMAGE_API_BASE_URL
import com.manishsputnikcorporation.movies.ui.screens.common.EmptyContent
import com.manishsputnikcorporation.movies.ui.screens.common.Loading
import com.manishsputnikcorporation.movies.ui.screens.common.Placeholder
import com.manishsputnikcorporation.movies.ui.screens.detail.DetailViewModel.DetailUiState
import com.manishsputnikcorporation.movies.ui.screens.detail.DetailViewModel.DetailUiState.DetailData
import com.manishsputnikcorporation.movies.ui.theme.MediaTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailDto

@Composable
fun DetailScreen(detailViewModel: DetailViewModel, onBackArrowClick: () -> Unit = {}) {
    detailViewModel.trackScreen()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val detailUiState by detailViewModel.detailUiState.collectAsState()
    detailUiState.let { state ->
        when {
            state.isIdle -> detailViewModel.loadData()
            else ->
                DetailContent(
                    state = state,
                    scrollBehavior = scrollBehavior,
                    onBackArrowClick = { onBackArrowClick() },
                    onFavoriteClick = { detailViewModel.setFavoriteStatus() },
                )
        }
    }
}

@Composable
fun DetailContent(
    state: DetailUiState,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onBackArrowClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    // TODO:
                    Text(text = stringResource(id = R.string.detail_label))
                },
                navigationIcon = {
                    IconButton(onClick = { onBackArrowClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.detail_arrow_back_label),
                        )
                    }
                },
            )
        },
        bottomBar = {
            // TODO: implement when new features are introduced
        },
    ) { contentPadding ->
        when {
            state.isLoading -> Loading()
            state.detailData.detailModel != null -> {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier =
                        modifier.verticalScroll(state = rememberScrollState()).padding(contentPadding).fillMaxHeight().semantics {
                            contentDescription = "Detail Screen"
                        },
                ) {
                    with(receiver = state.detailData.detailModel) {
                        val (title, runtime) =
                            when (this) {
                                is MovieDetailModel -> title to runtime
                                is TVDetailModel -> name to null
                                else -> "" to null
                            }

                        Overview(
                            image = posterPath,
                            title = title,
                            releaseDate = releaseDate,
                            runtime = runtime,
                            isFavorite = state.detailData.isFavorite,
                        ) {
                            onFavoriteClick()
                        }
                        Spacer(modifier = Modifier.height(height = 8.dp))
                        Text(text = overview, modifier = modifier.fillMaxWidth(), style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            // TODO:
            state.isFailed -> EmptyContent(message = R.string.detail_error)
        }
    }
}

@Composable
fun Overview(
    image: String,
    title: String,
    releaseDate: String,
    runtime: Int?,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit = {},
) {
    Row(modifier = modifier.fillMaxWidth()) {
        MediaImage(posterPath = image)
        Spacer(modifier = Modifier.width(width = 8.dp))
        Info(title = title, isFavorite = isFavorite, releaseDate = releaseDate, runtime = runtime, onFavoriteClick = onFavoriteClick)
        Spacer(modifier = Modifier.width(width = 8.dp))
    }
}

@Composable
private fun MediaImage(posterPath: String, modifier: Modifier = Modifier) {
    Card(
        border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.black)),
        modifier = modifier.height(height = 100.dp).width(width = 75.dp),
    ) {
        SubcomposeAsyncImage(
            model = "$TMDB_IMAGE_API_BASE_URL$posterPath",
            contentDescription = null,
            loading = { Loading() },
            error = { Placeholder(modifier = Modifier.width(width = 25.dp).height(height = 25.dp)) },
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
        )
    }
}

@Composable
fun Info(
    title: String,
    isFavorite: Boolean,
    releaseDate: String,
    runtime: Int?,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row {
            Text(text = title, modifier = Modifier.weight(weight = 1f), style = MaterialTheme.typography.headlineSmall)
            Favorite(isFavorite, onFavoriteClick)
        }
        SecondaryInfo(year = releaseDate, runtime = runtime)
        // TODO: Text(text = budget, style = MaterialTheme.typography.subtitle2)
    }
}

@Composable
fun Favorite(isFavorite: Boolean, onFavoriteClick: () -> Unit = {}) {
    IconButton(modifier = Modifier.then(other = Modifier.size(size = 24.dp)), onClick = { onFavoriteClick() }) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            // TODO:
            contentDescription = stringResource(id = R.string.movie_detail_favorite_movie),
        )
    }
}

@Composable
fun SecondaryInfo(year: String, runtime: Int?, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = year, style = MaterialTheme.typography.titleMedium)
        runtime?.let { Text(text = "$runtime min", style = MaterialTheme.typography.titleMedium) }
    }
}

// region Previews
@UiModePreviews
@Composable
private fun DetailContentPreview() {
    MediaTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val movieDetail = MovieDetailDtoMapper().mapToDomainModel(dto = fakeMovieDetailDto)
        DetailContent(state = DetailUiState(detailData = DetailData(detailModel = movieDetail)), scrollBehavior = scrollBehavior)
    }
}

@Preview
@Composable
private fun OverviewPreview() {
    MediaTheme {
        Surface {
            with(receiver = MovieDetailDtoMapper().mapToDomainModel(dto = fakeMovieDetailDto)) {
                Overview(image = posterPath, title = title, releaseDate = releaseDate, runtime = runtime, isFavorite = false)
            }
        }
    }
}

@Preview
@Composable
private fun InfoPreview() {
    MediaTheme {
        Surface {
            with(receiver = MovieDetailDtoMapper().mapToDomainModel(dto = fakeMovieDetailDto)) {
                Info(title = title, isFavorite = true, releaseDate = releaseDate, runtime = runtime)
            }
        }
    }
}
// endregion
