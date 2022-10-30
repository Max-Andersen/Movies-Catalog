package com.example.moviecatalog.mainScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviecatalog.R
import com.example.moviecatalog.normalizedItemPosition
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import kotlin.math.absoluteValue

@Composable
fun MainScreen(navController: NavController, model: MainScreenViewModel = viewModel()) {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                item { PromotedFilm(navController, model) }
                item { Spacer(modifier = Modifier.size(10.dp)) }

                item { Favorite(navController = navController, model = model) }

                item {
                    Text(
                        "Галерея",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                item { GalleryMovie(navController = navController, model = model) }
                item { GalleryMovie(navController = navController, model = model) }
                item { GalleryMovie(navController = navController, model = model) }
                item { GalleryMovie(navController = navController, model = model) }
                item { GalleryMovie(navController = navController, model = model) }
                item { GalleryMovie(navController = navController, model = model) }
                item { GalleryMovie(navController = navController, model = model) }
                item { GalleryMovie(navController = navController, model = model) }

            }
        }
    }
}

@Composable
fun PromotedFilm(navController: NavController, model: MainScreenViewModel) {
    //val filmDescription = model.getMovie()

    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.the_magicians),
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
                    Text("Смотреть", style = MaterialTheme.typography.bodyMedium)
                }
            }

        }
    }
}

@Composable
fun GalleryMovie(navController: NavController, model: MainScreenViewModel) {
    val movie = model.getPreView()
    Surface(modifier = Modifier
        .fillMaxSize()
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
                    .size(100.dp, 144.dp)
                    .padding(10.dp),
                contentScale = ContentScale.FillHeight

            )
            Column(modifier = Modifier.fillMaxSize()) {
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


@Composable
fun Favorite(navController: NavController, model: MainScreenViewModel) {
    val movies = model.getFavouriteMovies()

    if (movies.isNotEmpty()) {
        MovieCatalogTheme {
            Surface(modifier = Modifier.height(212.dp)) {
                Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    Text(
                        text = "Избранное",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    val state = rememberLazyListState()
                    LazyRow(state = state, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(movies, key = { it.id }) { movie ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        var value =
                                            1 - (state.layoutInfo.normalizedItemPosition(movie.id).absoluteValue * 0.1F)
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
                                        .clickable { navController.navigate("movie/1") },
                                    contentScale = ContentScale.FillHeight,
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.close_button),
                                    contentDescription = null,
                                    modifier = Modifier.padding(
                                        start = 104.dp,
                                        top = 4.dp,
                                        end = 4.dp,
                                        bottom = 156.dp
                                    )
                                        .clickable {  } // TODO(удаление из избранного)
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
fun Gallery(navController: NavController, model: MainScreenViewModel) {
    Text(
        "Галерея",
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(start = 16.dp)
    )
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item { GalleryMovie(navController = navController, model = model) }
        item { GalleryMovie(navController = navController, model = model) }
        item { GalleryMovie(navController = navController, model = model) }
        item { GalleryMovie(navController = navController, model = model) }
        item { GalleryMovie(navController = navController, model = model) }

    }

}