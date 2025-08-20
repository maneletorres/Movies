package com.manishsputnikcorporation.movies.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.FirebaseUser
import com.manishsputnikcorporation.movies.R
import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.domain.model.base.Model
import com.manishsputnikcorporation.movies.network.util.TMDB_IMAGE_API_BASE_URL
import com.manishsputnikcorporation.movies.ui.navigation.DetailArgs
import com.manishsputnikcorporation.movies.ui.screens.common.AlertDialog
import com.manishsputnikcorporation.movies.ui.screens.common.EmptyContent
import com.manishsputnikcorporation.movies.ui.screens.common.Loading
import com.manishsputnikcorporation.movies.ui.screens.common.Placeholder
import com.manishsputnikcorporation.movies.ui.screens.home.HomeViewModel.MoviesHomeUiState.Data
import com.manishsputnikcorporation.movies.ui.screens.home.HomeViewModel.MoviesHomeUiState.Idle
import com.manishsputnikcorporation.movies.ui.screens.home.HomeViewModel.MoviesHomeUiState.Loading
import com.manishsputnikcorporation.movies.ui.theme.MediaTheme
import com.manishsputnikcorporation.movies.utils.annotations.UiModePreviews
import com.manishsputnikcorporation.movies.utils.enums.Type
import com.manishsputnikcorporation.movies.utils.extensions.toggleSearchType
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieModel
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieModels
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MoviesHomeScreen(homeViewModel: HomeViewModel, onClickSignOut: () -> Unit = {}, onClickMovie: (DetailArgs) -> Unit = {}) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val hotState: SnackbarHostState = remember { SnackbarHostState() }
    var searchType by rememberSaveable { mutableStateOf(value = Type.MOVIE) }

    homeViewModel.trackScreen()

    val moviesHomeUIState by homeViewModel.moviesHomeUiState.collectAsState()
    moviesHomeUIState.let {
        when (it) {
            Idle -> homeViewModel.loadData()
            Loading -> Loading()
            is Data ->
                MoviesHomeContent(
                    scaffoldState = scaffoldState,
                    hostState = hotState,
                    searchType = searchType,
                    user = it.user,
                    movies = it.mediaModelList,
                    onClickFavorites = { homeViewModel.getFavorites(searchType) },
                    onClickSearchType = {
                        searchType = searchType.toggleSearchType()
                        homeViewModel.loadData(searchType)
                    },
                    onClickSignOut = onClickSignOut,
                    onClickMovie = { movieId -> onClickMovie(movieId) },
                )
        }
    }

    val resources = LocalResources.current
    LaunchedEffect(key1 = Unit) {
        homeViewModel.event.collectLatest { event ->
            when (event) {
                is HomeViewModel.Event.Error -> {
                    hotState.showSnackbar(
                        message = resources.getString(R.string.movies_home_error, event.searchType),
                        actionLabel = resources.getString(R.string.close_message),
                        duration = SnackbarDuration.Long,
                    )
                }
            }
        }
    }
}

@Composable
fun MoviesHomeContent(
    scaffoldState: ScaffoldState,
    hostState: SnackbarHostState,
    searchType: Type,
    user: FirebaseUser?,
    movies: List<Model>,
    modifier: Modifier = Modifier,
    onClickFavorites: () -> Unit = {},
    onClickSearchType: () -> Unit = {},
    onClickSignOut: () -> Unit = {},
    onClickMovie: (DetailArgs) -> Unit = {},
) {
    val gridState = rememberLazyGridState()
    val showButton by remember { derivedStateOf { gridState.firstVisibleItemIndex > 0 } }

    // region Sign out
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val onDismissRequest: () -> Unit = {
        expanded = false
        showDialog = false
    }
    val onSignOutConfirmed: () -> Unit = {
        expanded = false
        showDialog = false
        onClickSignOut()
    }
    // endregion

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(hostState = hostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                        if (user?.photoUrl != null) {
                            // TODO: Google case
                        } else {
                            Image(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = stringResource(id = R.string.movies_home_default_profile_photo_description),
                                modifier = Modifier.padding(end = 8.dp).size(size = 40.dp).clip(CircleShape),
                            )
                        }
                        Column {
                            val displayName = user?.displayName
                            val email = user?.email

                            val greetingText =
                                if (displayName.isNullOrEmpty()) {
                                    stringResource(id = R.string.movies_home_generic_greeting)
                                } else {
                                    stringResource(id = R.string.movies_home_custom_greeting, displayName)
                                }
                            Text(text = greetingText, fontSize = 20.sp, maxLines = 1, overflow = Ellipsis)

                            val nameText = if (email.isNullOrEmpty()) stringResource(id = R.string.movies_home_generic_name) else email
                            Text(text = nameText, fontSize = 12.sp, maxLines = 1, overflow = Ellipsis)
                        }
                    }
                    // TODO: Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { onClickFavorites() }) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = stringResource(id = R.string.movies_home_favorites))
                    }
                    IconButton(onClick = { onClickSearchType() }) {
                        Crossfade(targetState = searchType) { searchType ->
                            Icon(
                                painter =
                                    painterResource(
                                        id = if (searchType == Type.MOVIE) R.drawable.ic_series_search else R.drawable.ic_movie_search
                                    ),
                                contentDescription = stringResource(id = R.string.movies_home_search_type),
                            )
                        }
                    }
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(id = R.string.movies_home_more_options),
                        )
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(
                                onClick = { showDialog = true },
                                content = { Text(text = stringResource(id = R.string.movies_home_sign_out)) },
                            )
                        }
                    }
                },
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
                    onClick = { coroutineScope.launch { gridState.scrollToItem(index = 0) } },
                ) {
                    Text(text = stringResource(id = R.string.movies_home_up_button_text))
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { contentPadding ->
        Box(modifier = modifier.padding(paddingValues = contentPadding)) {
            AnimatedContent(targetState = movies.isEmpty()) { isEmpty ->
                if (isEmpty)
                    EmptyContent(
                        when (searchType) {
                            Type.MOVIE -> R.string.movies_home_empty_movies
                            Type.TV -> R.string.movies_home_empty_series
                        }
                    )
                else Grid(movies, gridState = gridState) { movieId -> onClickMovie(movieId) }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { onDismissRequest() },
                    onConfirmation = { onSignOutConfirmed() },
                    dialogTitle = R.string.movies_home_sign_out,
                    dialogText = R.string.sign_out_dialog_description,
                )
            }
        }
    }
}

@Composable
fun Grid(
    movies: List<Model>,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    onClickMovie: (DetailArgs) -> Unit = {},
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = modifier, state = gridState) {
        items(items = movies, key = { it.id }) { media ->
            val rememberedMovie = rememberSaveable { media } // TODO: to check
            with(receiver = rememberedMovie) {
                val type: Type? =
                    when (this) {
                        is MovieModel -> Type.MOVIE
                        is TVModel -> Type.TV
                        else -> null
                    }
                type?.let { type ->
                    val detailArgs = DetailArgs(id = id, type = type)
                    Media(detailArgs = detailArgs, image = posterPath) { onClickMovie(detailArgs) }
                }
            }
        }
    }
}

@Composable
fun Media(detailArgs: DetailArgs, image: String, onClickMedia: (DetailArgs) -> Unit = {}) {
    SubcomposeAsyncImage(
        model = "$TMDB_IMAGE_API_BASE_URL$image",
        contentDescription = null,
        modifier =
            Modifier.height(height = 200.dp)
                .clickable { onClickMedia(detailArgs) }
                .semantics { contentDescription = "Movie Detail" }, // TODO: to check
        loading = { Loading() },
        error = { Placeholder(modifier = Modifier.size(size = 50.dp)) },
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
    )
}

// region Previews
@UiModePreviews
@Preview(showSystemUi = true) // TODO: to check
@Composable
private fun MoviesHomeContentPreview() {
    MediaTheme {
        val hostState: SnackbarHostState = remember { SnackbarHostState() }
        MoviesHomeContent(
            scaffoldState = rememberScaffoldState(),
            hostState = hostState,
            searchType = Type.MOVIE,
            user = null,
            movies = fakeMovieModels,
        )
    }
}

@UiModePreviews
@Composable
private fun MoviesGridPreview() {
    MediaTheme { Surface { Grid(movies = fakeMovieModels) } }
}

@UiModePreviews
@Composable
private fun MoviePreview() {
    MediaTheme { Surface { with(receiver = fakeMovieModel) { Media(DetailArgs(id = id, type = Type.MOVIE), posterPath) } } }
}
// endregion
