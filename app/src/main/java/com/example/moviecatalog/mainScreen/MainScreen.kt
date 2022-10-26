package com.example.moviecatalog.mainScreen

import android.widget.Gallery
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun MainScreen(navController: NavController, model: MainScreenViewModel = viewModel()) {

    MovieCatalogTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {

                PromotedFilm(navController, model)
                Spacer(modifier = Modifier.size(10.dp))

                Favorite(navController = navController, model = model)

                Gallery(navController = navController, model = model)
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
                .height(370.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.the_magicians),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate("movie/1") }
            )
        }
    }
}


@Composable
fun TestMovie(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.the_magicians),
        contentDescription = null,
        modifier = Modifier
            .height(144.dp)
            .padding(10.dp)
            .clickable { navController.navigate("movie/1") },
    )
}

@Composable
fun GalleryMovie(navController: NavController, model: MainScreenViewModel) {
    val movie = model.getPreView()
    Surface(modifier = Modifier
        .fillMaxSize()
        .clickable { navController.navigate("movie/1") }) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = movie.TEMP_IMG),
                contentDescription = null,
                modifier = Modifier
                    .height(144.dp)
                    .padding(10.dp)
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
    MovieCatalogTheme {
        Surface(modifier = Modifier.height(212.dp)) {
            Column {
                Text(
                    text = "Избранное",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
                LazyRow() {
                    item() { TestMovie(navController = navController) }
                    item() { TestMovie(navController = navController) }
                    item() { TestMovie(navController = navController) }
                    item() { TestMovie(navController = navController) }
                    item() { TestMovie(navController = navController) }
                }
            }
        }
    }
}

@Composable
fun Gallery(navController: NavController, model: MainScreenViewModel) {
    MovieCatalogTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    "Галерея",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
                LazyColumn() {
                    item { GalleryMovie(navController = navController, model = model) }
                    item { GalleryMovie(navController = navController, model = model) }
                    item { GalleryMovie(navController = navController, model = model) }
                    item { GalleryMovie(navController = navController, model = model) }
                    item { GalleryMovie(navController = navController, model = model) }

                }
            }
        }
    }
}