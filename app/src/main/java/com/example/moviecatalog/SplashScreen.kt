package com.example.moviecatalog

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.moviecatalog.network.Network
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.2f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(3f).getInterpolation(it)
                })
        )

        delay(1500L)

        if (Network.token != ""){
            navController.navigate("mainScreen") {
                popUpTo(navController.graph.id)
            }
        }
        else{
            navController.navigate("sign-In") {
                popUpTo(navController.graph.id)
            }
        }


    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),

        ) {
        Image(
            painter = painterResource(id = R.drawable.logo_group),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}