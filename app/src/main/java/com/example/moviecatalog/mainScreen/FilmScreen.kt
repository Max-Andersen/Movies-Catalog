package com.example.moviecatalog.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun FilmScreen(filmId: String, navController: NavController){
    MovieCatalogTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top=50.dp)) {
            Column() {
                Button(onClick = { navController.navigateUp() }, modifier = Modifier.size(50.dp)) {
                    Text("Back")
                }
                Text("MY  FILMM", modifier = Modifier.padding(70.dp))
            }

        }
    }
}