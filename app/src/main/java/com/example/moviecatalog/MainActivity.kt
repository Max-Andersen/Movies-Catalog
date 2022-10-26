package com.example.moviecatalog

import android.os.Build
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.mainScreen.MovieScreen
import com.example.moviecatalog.mainScreen.MainScreenController
import com.example.moviecatalog.signIn.SignInScreen
import com.example.moviecatalog.signUp.SignUpScreen
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import kotlinx.coroutines.delay

fun NavController.navigate(route: String, params: Bundle?, builder: NavOptionsBuilder.() -> Unit = {}) {
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
                Surface(modifier = Modifier) {

                    NavHost(navController = navController, startDestination = "splashScreen") {
                        composable("splashScreen"){ SplashScreen(navController = navController)}
                        composable("tokenCheck"){ TODO() }
                        composable("sign-In"){ SignInScreen(navController = navController)}
                        composable("sign-Up"){ SignUpScreen(navController = navController)}
                        composable("mainScreen"){ MainScreenController(navController) }
                        composable("movie" + "/{id}"){ navBackStack ->
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
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.2f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(3f).getInterpolation(it)
                }))

        delay(1500L)
        navController.navigate("sign-In"){
            popUpTo(navController.graph.id)
        }
    }

    // Image
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),

        ) {
        Image(painter = painterResource(id = R.drawable.logo_group),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value))
    }
}
