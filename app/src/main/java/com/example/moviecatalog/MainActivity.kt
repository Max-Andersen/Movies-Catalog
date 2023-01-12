package com.example.moviecatalog

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import com.example.moviecatalog.mainScreen.MainScreenController
import com.example.moviecatalog.mainScreen.movieScreen.MovieScreen
import com.example.moviecatalog.signIn.SignInScreen
import com.example.moviecatalog.signUp.SignUpScreen
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

fun NavController.navigate(
    route: String,
    params: Bundle?,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.currentBackStackEntry?.arguments?.putAll(params)

    navigate(route, builder)
}


@ExperimentalFoundationApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.N)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieCatalogTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "splashScreen") {
                    composable("splashScreen") { SplashScreen(navController = navController) }
                    composable("sign-In") { SignInScreen(navController = navController) }
                    composable("sign-Up") { SignUpScreen(navController = navController) }
                    composable("mainScreen") { MainScreenController(navController) }
                    composable("movie" + "/{id}") { navBackStack ->
                        val filmId = navBackStack.arguments?.getString("id")
                        if (filmId != null) {
                            MovieScreen(filmId, navController)
                        }
                    }
                }
            }
        }
    }
}




