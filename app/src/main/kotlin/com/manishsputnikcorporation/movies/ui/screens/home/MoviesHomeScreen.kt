package com.manishsputnikcorporation.movies.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import com.manishsputnikcorporation.movies.ui.screens.generic.EmptyContent
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericLoading
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericPlaceholder
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.*
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.extensions.toggleSearchType
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MoviesHomeScreen(
    moviesHomeViewModel: MoviesHomeViewModel,
    onClickMovie: (String) -> Unit = {}
) {
  val scaffoldState: ScaffoldState = rememberScaffoldState()
  var searchType by rememberSaveable { mutableStateOf(Type.MOVIE) }
  val moviesHomeUIState by moviesHomeViewModel.moviesHomeUiState.collectAsState()
  moviesHomeUIState.let {
    when (it) {
      Idle -> Unit
      Loading -> GenericLoading()
      is Data ->
          MoviesHomeContent(
              scaffoldState,
              searchType,
              movies = it.movieList,
              onClickFavorites = { moviesHomeViewModel.getFavorites() },
              onClickSearchType = {
                searchType = searchType.toggleSearchType()
                moviesHomeViewModel.loadData(searchType) // TODO: to review!
              },
              onClickMovie = { movieId -> onClickMovie(movieId) })
    }
  }

  val context = LocalContext.current
  LaunchedEffect(Unit) {
    moviesHomeViewModel.event.collectLatest { event ->
      when (event) {
        is MoviesHomeViewModel.Event.Error -> {
          scaffoldState.snackbarHostState.showSnackbar(
              message = context.resources.getString(R.string.movies_home_error, event.searchType),
              actionLabel = context.resources.getString(R.string.generic_close_message),
              duration = SnackbarDuration.Long)
        }
      }
    }
  }
}

@Composable
fun MoviesHomeContent(
    scaffoldState: ScaffoldState,
    searchType: Type,
    movies: List<MovieModel>,
    modifier: Modifier = Modifier,
    onClickFavorites: () -> Unit = {},
    onClickSearchType: () -> Unit = {},
    onClickMovie: (String) -> Unit = {},
) {
  val gridState = rememberLazyGridState()
  val showButton by remember { derivedStateOf { gridState.firstVisibleItemIndex > 0 } }

  Scaffold(
      scaffoldState = scaffoldState,
      topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
              IconButton(onClick = { onClickFavorites() }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(id = R.string.movies_home_favorites))
              }
              IconButton(onClick = { onClickSearchType() }) {
                Icon(
                    painterResource(
                        id =
                            when (searchType) {
                              Type.MOVIE -> R.drawable.series_search
                              Type.SERIES -> R.drawable.movie_search
                            }),
                    contentDescription = stringResource(id = R.string.movies_home_search_type))
              }
            })
      },
      bottomBar = {
        // TODO: implement when new features are introduced
      },
      floatingActionButton = {
        if (showButton) {
          val coroutineScope = rememberCoroutineScope()
          FloatingActionButton(
              modifier = Modifier.navigationBarsPadding().padding(bottom = 8.dp, end = 8.dp),
              onClick = { coroutineScope.launch { gridState.scrollToItem(0) } }) {
                Text(stringResource(id = R.string.movies_home_up_button_text))
              }
        }
      },
      floatingActionButtonPosition = FabPosition.End) { contentPadding ->
        Box(
            modifier =
                modifier.padding(contentPadding).semantics {
                  contentDescription = "Movies Home Screen" // TODO:
                }) {
              if (movies.isEmpty())
                  EmptyContent(
                      when (searchType) {
                        Type.MOVIE -> R.string.movies_home_empty_movies
                        Type.SERIES -> R.string.movies_home_empty_series
                        // else -> R.string.movies_home_empty_favorites // TODO: CHECK THIS!
                      })
              else MoviesGrid(movies, gridState = gridState) { movieId -> onClickMovie(movieId) }
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
  LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = modifier, state = gridState) {
    items(movies, key = { it.id }) { movie ->
      val rememberedMovie = rememberSaveable { movie } // TODO: to check
      with(rememberedMovie) { Movie(id, imageUrl) { movieId -> onClickMovie(movieId) } }
    }
  }
}

@Composable
fun Movie(id: String, image: String, onClickMovie: (String) -> Unit = {}) {
  SubcomposeAsyncImage(
      model = image,
      contentDescription = null,
      modifier =
          Modifier.height(200.dp)
              .clickable { onClickMovie(id) }
              .semantics { contentDescription = "Movie Detail" }, // TODO: to check
      loading = { GenericLoading() },
      error = { GenericPlaceholder(modifier = Modifier.width(50.dp).height(50.dp)) },
      alignment = Alignment.Center,
      contentScale = ContentScale.Crop)
}

// region Previews
@UiModePreviews
@Preview(showSystemUi = true) // TODO: to check
@Composable
private fun MoviesHomeContentPreview() {
  val context = LocalContext.current
  MoviesTheme {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val movies = fakeMovieListDto(context).items.map { MovieDtoMapper().mapToDomainModel(it) }
    MoviesHomeContent(scaffoldState, Type.MOVIE, movies)
  }
}

@UiModePreviews
@Composable
private fun MoviesGridPreview() {
  val context = LocalContext.current
  MoviesTheme {
    Surface {
      val movies = fakeMovieListDto(context).items.map { MovieDtoMapper().mapToDomainModel(it) }
      MoviesGrid(movies = movies)
    }
  }
}

@UiModePreviews
@Composable
private fun MoviePreview() {
  val context = LocalContext.current
  MoviesTheme {
    Surface {
      with(MovieDtoMapper().mapToDomainModel(fakeMovieListDto(context).items[0])) {
        Movie(id, imageUrl)
      }
    }
  }
}
// endregion
