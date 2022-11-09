package com.example.moviecatalog.mainScreen

import com.example.moviecatalog.mainScreen.movieData.Movies
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import com.example.moviecatalog.R
import com.example.moviecatalog.normalizedItemPosition
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import kotlin.math.absoluteValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviecatalog.mainScreen.movieData.Reviews
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
                        .background(MaterialTheme.colorScheme.background),
                    contentPadding = PaddingValues(
                        bottom = 100.dp,
                    ),
                ) {
                    item {
                        if (superLazyMovieItems.itemCount > 0) {
                            PromotedFilm(navController, superLazyMovieItems[0]!!)

                        }
                        Log.d("available movie count", superLazyMovieItems.itemCount.toString())
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


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PromotedFilm(navController: NavController, movie: Movies) {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                GlideImage(
                    model = movie.poster, contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
                Button(
                    onClick = { navController.navigate("movie/${movie.id}") },
                    modifier = Modifier
                        .padding(
                            start = LocalConfiguration.current.screenWidthDp.dp / 3,
                            end = LocalConfiguration.current.screenWidthDp.dp / 3,
                            top = 249.dp
                        )
                        .size(160.dp, 44.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        stringResource(id = R.string.watch),
                        style = MaterialTheme.typography.bodyMedium
                    )
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
                                        //alpha = value
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

fun calculateRating(reviews: List<Reviews>): String {
    var summ = 0f
    for (review in reviews) {
        summ += review.rating
    }
    return String.format("%.1f", summ / reviews.size)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GalleryMovie(navController: NavController, movie: Movies) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, top = 16.dp)
        .height(144.dp)
        .background(MaterialTheme.colorScheme.background)
        .clickable {
            if (movie != null) {
                navController.navigate("movie/${movie.id}")
            }
        }) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)

        ) {

            GlideImage(
                model = movie.poster, contentDescription = null,
                modifier = Modifier
                    .size(120.dp, 172.dp),
                contentScale = ContentScale.FillHeight
            )


            ConstraintLayout(modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp)) {
                val (title, about, genresList) = createRefs()

                val genres = mutableListOf<String>()

                for (i in movie.genres) {
                    genres.add(i.name)
                }

                Text(
                    text = movie.name,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.constrainAs(title){
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }

                )
                Text(
                    text = "${movie.year} • ${movie.country}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.constrainAs(about){
                        start.linkTo(parent.start)
                        top.linkTo(title.bottom)
                    }
                )
                Text(
                    text = genres.joinToString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.constrainAs(genresList){
                        start.linkTo(parent.start)
                        top.linkTo(about.bottom)
                    }
                )

            }

//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(start = 16.dp)
//            ) {
//
//
//
//
//
//            }
        }
    }
}
