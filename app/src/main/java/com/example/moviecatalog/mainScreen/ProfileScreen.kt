package com.example.moviecatalog.mainScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun ProfileScreen(){
    MovieCatalogTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Text("PROFILE SCREEN")
        }
    }
}