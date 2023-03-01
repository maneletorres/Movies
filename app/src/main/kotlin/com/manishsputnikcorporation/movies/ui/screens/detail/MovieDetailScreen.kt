package com.manishsputnikcorporation.movies.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
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
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailViewModel.MovieDetailUiState
import com.manishsputnikcorporation.movies.ui.screens.detail.MovieDetailViewModel.MovieDetailUiState.*
import com.manishsputnikcorporation.movies.ui.screens.generic.EmptyContent
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericLoading
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericPlaceholder
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailDto

@Composable
fun MovieDetailScreen(
    movieDetailViewModel: MovieDetailViewModel,
    movieId: String?,
    onBackArrowClick: () -> Unit = {}
) {
  val movieDetailUiState by movieDetailViewModel.movieDetailUiState.collectAsState()
  movieDetailUiState.let { state ->
    when {
      state.isIdle -> movieDetailViewModel.loadData(movieId)
      else ->
          MovieDetailContent(
              state = state,
              onBackArrowClick = { onBackArrowClick() },
              onFavoriteMovieClick = {
                movieId?.let { movieDetailViewModel.setMovieFavoriteStatus(it) }
              })
    }
  }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailContent(
    state: MovieDetailUiState,
    modifier: Modifier = Modifier,
    onBackArrowClick: () -> Unit = {},
    onFavoriteMovieClick: () -> Unit = {}
) {
  Scaffold(
      topBar = {
        TopAppBar(
            title = { Text(stringResource(id = R.string.movie_detail_label)) },
            navigationIcon = {
              IconButton(onClick = { onBackArrowClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription =
                        stringResource(id = R.string.movie_detail_arrow_back_label),
                )
              }
            })
      },
      bottomBar = {
        // TODO: implement when new features are introduced
      }) {
        when {
          state.isLoading -> GenericLoading()
          state.movieDetailData.movieDetailModel != null -> {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier =
                    modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxHeight()
                        .semantics { contentDescription = "Movie Detail Screen" }) {
                  state.movieDetailData.movieDetailModel.let {
                    MovieOverview(
                        image = it.image,
                        title = it.title,
                        directors = it.directors,
                        year = it.year,
                        runtime = it.runtimeStr,
                        isFavorite = state.movieDetailData.isFavorite) {
                          onFavoriteMovieClick()
                        }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it.plot,
                        modifier = modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body1,
                    )
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
    directors: String,
    year: String,
    runtime: String?,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteMovieClick: () -> Unit = {}
) {
  Row(modifier = modifier.fillMaxWidth()) {
    MovieImage(imageUrl = image)
    Spacer(modifier = Modifier.width(8.dp))
    MovieInfo(
        title = title,
        isFavorite = isFavorite,
        directors = directors,
        year = year,
        runtime = runtime,
        onFavoriteMovieClick = onFavoriteMovieClick)
    Spacer(modifier = Modifier.width(8.dp))
  }
}

@Composable
private fun MovieImage(imageUrl: String, modifier: Modifier = Modifier) {
  Card(
      border = BorderStroke(1.dp, colorResource(id = R.color.black)),
      modifier = modifier.height(100.dp).width(75.dp)) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "",
            loading = { GenericLoading() },
            error = { GenericPlaceholder(modifier = Modifier.width(25.dp).height(25.dp)) },
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit)
      }
}

@Composable
fun MovieInfo(
    title: String,
    isFavorite: Boolean,
    directors: String,
    year: String,
    runtime: String?,
    modifier: Modifier = Modifier,
    onFavoriteMovieClick: () -> Unit = {}
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Row {
      Text(text = title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.h6)
      MovieFavorite(isFavorite, onFavoriteMovieClick)
    }
    Text(text = directors, style = MaterialTheme.typography.subtitle1)
    MovieSecondaryInfo(year = year, runtime = runtime)
  }
}

@Composable
fun MovieFavorite(isFavorite: Boolean, onFavoriteMovieClick: () -> Unit = {}) {
  IconButton(modifier = Modifier.then(Modifier.size(24.dp)), onClick = { onFavoriteMovieClick() }) {
    Icon(
        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = stringResource(id = R.string.movie_detail_favorite_movie))
  }
}

@Composable
fun MovieSecondaryInfo(year: String, runtime: String?, modifier: Modifier = Modifier) {
  Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
    Text(
        text = year,
        style = MaterialTheme.typography.subtitle2,
    )
    runtime?.let { Text(text = runtime, style = MaterialTheme.typography.subtitle2) }
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
        MovieOverview(
            image = image,
            title = title,
            directors = directors,
            year = year,
            runtime = runtimeStr,
            isFavorite = false)
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
        MovieInfo(
            title = title,
            isFavorite = true,
            directors = directors,
            year = year,
            runtime = runtimeStr)
      }
    }
  }
}
// endregion
