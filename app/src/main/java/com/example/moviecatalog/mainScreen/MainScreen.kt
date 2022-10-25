package com.example.moviecatalog.mainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun MainScreen(navController: NavController){
    MovieCatalogTheme {
        val a = 1
        Surface(modifier = Modifier.fillMaxSize()) {
            Text("MAIN SCREEN", modifier = Modifier.
            clickable{navController.navigate("film/$a")}
            )

            // FILMS ----> navController.navigate("film" + "$FILM_ID")


        }
    }

}