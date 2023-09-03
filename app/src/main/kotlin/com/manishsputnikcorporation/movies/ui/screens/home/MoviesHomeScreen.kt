package com.manishsputnikcorporation.movies.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.manishsputnikcorporation.movies.domain.model.MediaItemModel
import com.manishsputnikcorporation.movies.network.model.MediaItemDtoMapper
import com.manishsputnikcorporation.movies.network.util.TMDB_IMAGE_API_BASE_URL
import com.manishsputnikcorporation.movies.ui.navigation.MoviesHome
import com.manishsputnikcorporation.movies.ui.screens.generic.EmptyContent
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericLoading
import com.manishsputnikcorporation.movies.ui.screens.generic.GenericPlaceholder
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.Data
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.Idle
import com.manishsputnikcorporation.movies.ui.screens.home.MoviesHomeViewModel.MoviesHomeUiState.Loading
import com.manishsputnikcorporation.movies.ui.theme.MoviesTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.extensions.toggleSearchType
import com.manishsputnikcorporation.movies.utils.fake.fakeMoviesDto
import com.manishsputnikcorporation.movies.utils.manager.AnalyticsManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MoviesHomeScreen(analyticsManager: AnalyticsManager, moviesHomeViewModel: MoviesHomeViewModel, onClickMovie: (String) -> Unit = {}) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    var searchType by rememberSaveable { mutableStateOf(Type.MOVIE) }

    analyticsManager.trackScreen(screenName = MoviesHome.route)

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
                    onClickFavorites = { moviesHomeViewModel.getFavorites(searchType) },
                    onClickSearchType = {
                        searchType = searchType.toggleSearchType()
                        moviesHomeViewModel.loadData(searchType) // TODO: to review!
                    },
                    onClickMovie = { movieId -> onClickMovie(movieId) }
                )
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
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }
}

@Composable
fun MoviesHomeContent(
    scaffoldState: ScaffoldState,
    searchType: Type,
    movies: List<MediaItemModel>,
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
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = stringResource(id = R.string.movies_home_favorites))
                    }
                    IconButton(onClick = { onClickSearchType() }) {
                        Icon(
                            painterResource(
                                id =
                                    when (searchType) {
                                        Type.MOVIE -> R.drawable.ic_series_search
                                        Type.TV_SHOW -> R.drawable.ic_movie_search
                                    }
                            ),
                            contentDescription = stringResource(id = R.string.movies_home_search_type)
                        )
                    }
                }
            )
        },
        bottomBar = {
            // TODO: implement when new features are introduced
        },
        floatingActionButton = {
            if (showButton) {
                val coroutineScope = rememberCoroutineScope()
                FloatingActionButton(
                    modifier = Modifier.navigationBarsPadding().padding(bottom = 8.dp, end = 8.dp),
                    onClick = { coroutineScope.launch { gridState.scrollToItem(0) } }
                ) {
                    Text(stringResource(id = R.string.movies_home_up_button_text))
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { contentPadding ->
        Box(
            modifier =
                modifier.padding(contentPadding).semantics {
                    contentDescription = "Movies Home Screen" // TODO:
                }
        ) {
            if (movies.isEmpty())
                EmptyContent(
                    when (searchType) {
                        Type.MOVIE -> R.string.movies_home_empty_movies
                        Type.TV_SHOW -> R.string.movies_home_empty_series
                    }
                )
            else MoviesGrid(movies, gridState = gridState) { movieId -> onClickMovie(movieId) }
        }
    }
}

@Composable
fun MoviesGrid(
    movies: List<MediaItemModel>,
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
        model = "$TMDB_IMAGE_API_BASE_URL$image",
        contentDescription = null,
        modifier =
            Modifier.height(200.dp).clickable { onClickMovie(id) }.semantics { contentDescription = "Movie Detail" }, // TODO: to check
        loading = { GenericLoading() },
        error = { GenericPlaceholder(modifier = Modifier.width(50.dp).height(50.dp)) },
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop
    )
}

// region Previews
@UiModePreviews
@Preview(showSystemUi = true) // TODO: to check
@Composable
private fun MoviesHomeContentPreview() {
    MoviesTheme {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val movies = fakeMoviesDto.results.map { MediaItemDtoMapper().mapToDomainModel(it) }
        MoviesHomeContent(scaffoldState, Type.MOVIE, movies)
    }
}

@UiModePreviews
@Composable
private fun MoviesGridPreview() {
    MoviesTheme {
        Surface {
            val movies = fakeMoviesDto.results.map { MediaItemDtoMapper().mapToDomainModel(it) }
            MoviesGrid(movies = movies)
        }
    }
}

@UiModePreviews
@Composable
private fun MoviePreview() {
    MoviesTheme { Surface { with(MediaItemDtoMapper().mapToDomainModel(fakeMoviesDto.results[0])) { Movie(id, imageUrl) } } }
}
// endregion
