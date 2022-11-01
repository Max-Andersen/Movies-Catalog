package com.example.moviecatalog.mainScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.moviecatalog.R
import com.example.moviecatalog.mainScreen.movieData.MoviePreView
import com.example.moviecatalog.normalizedItemPosition
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import kotlin.math.absoluteValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavController, model: MainScreenViewModel = viewModel()) {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                val superLazyMovieItems = model.movies.collectAsLazyPagingItems()

                val listState = rememberLazyListState()
                val coroutineScope = rememberCoroutineScope()
                val showUpButton by remember {
                    derivedStateOf {
                        listState.firstVisibleItemIndex > 0
                    }
                }
                val configuration = LocalConfiguration.current
                val screenHeight = configuration.screenHeightDp.dp
                val screenWidth = configuration.screenWidthDp.dp



                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    item {
                        if (superLazyMovieItems.itemCount > 0){ PromotedFilm(navController, superLazyMovieItems[0]!!)}
                    }
                    item { Spacer(modifier = Modifier.size(10.dp)) }

                    item { Favorite(navController = navController, model = model) }

                    item {
                        Text(
                            stringResource(id = R.string.gallery),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    items(superLazyMovieItems) { newMovie ->
                        if (newMovie != null) {
                            GalleryMovie(navController = navController, movie = newMovie)
                        }
                    }
                }

                Icon(painter = painterResource(id = R.drawable.button_up),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = screenWidth - 100.dp, top = screenHeight - 40.dp)
                        .clickable {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index = 0)
                            }
                        }
                        .size(40.dp)
                        .alpha(if (showUpButton) 0.7f else 0f)
                )

            }


        }
    }
}



@Composable
fun PromotedFilm(navController: NavController, movie: MoviePreView) {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = movie.TEMP_IMG),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
                Button(
                    onClick = { navController.navigate("movie/1") },
                    modifier = Modifier
                        .padding(
                            start = LocalConfiguration.current.screenWidthDp.dp / 3,
                            end = LocalConfiguration.current.screenWidthDp.dp / 3,
                            //bottom = 63.dp,
                            top = 249.dp
                        )
                        .size(160.dp, 44.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(stringResource(id = R.string.watch), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun Favorite(navController: NavController, model: MainScreenViewModel) {
    val movies = model.getFavouriteMovies()

    if (movies.isNotEmpty()) {
        MovieCatalogTheme {
            Surface(modifier = Modifier.height(212.dp)) {
                Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    Text(
                        text = stringResource(id = R.string.favourite),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    val state = rememberLazyListState()
                    LazyRow(
                        state = state, horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(
                            start = 30.dp,
                            end = 180.dp,
                        ),
                    ) {
                        items(movies, key = { it.id }) { movie ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        var value =
                                            1 - (state.layoutInfo.normalizedItemPosition(movie.id).absoluteValue * 0.04F)
                                        value = if (value < 0.9f) 0.9f else value
                                        alpha = value
                                        scaleX = value
                                        scaleY = value
                                    },
                            ) {
                                Image(
                                    painter = painterResource(id = movie.TEMP_IMG), //TODO()
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(120.dp, 172.dp)
                                        .background(MaterialTheme.colorScheme.background)
                                        .clickable { navController.navigate("movie/1") }
                                        .clip(
                                            RoundedCornerShape(8.dp)
                                        ),
                                    contentScale = ContentScale.FillHeight,
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.close_button),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(
                                            start = 104.dp,
                                            top = 4.dp,
                                            end = 4.dp,
                                            bottom = 156.dp
                                        )
                                        .clickable { } // TODO(удаление из избранного)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GalleryMovie(navController: NavController, movie: MoviePreView) {

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, top = 16.dp)
        .height(144.dp)
        .background(MaterialTheme.colorScheme.background)
        .clickable { navController.navigate("movie/1") }) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)

        ) {
            Image(
                painter = painterResource(id = movie.TEMP_IMG),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp, 172.dp),
                contentScale = ContentScale.FillHeight

            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = movie.name,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${movie.year} • ${movie.country}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = movie.genres.joinToString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
