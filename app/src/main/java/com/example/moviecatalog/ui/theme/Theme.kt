package com.example.moviecatalog.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

private val MyColorScheme = darkColorScheme(
    primary = Accent,
    onPrimary = White,
    secondary = GrayFaded,
    onSecondary = Gray,

    background = NearBlack,
    onBackground = BottomSideColor,
    surface = SystemColor,
    onSurface = DescriptionColor,
    onSurfaceVariant = GenresColor,
    surfaceVariant = ReviewColor,

)

@Composable
fun MovieCatalogTheme(content: @Composable () -> Unit) {

    val currentActivity = LocalView.current.context as Activity         // Not working with Preview
    currentActivity.window.statusBarColor = Color.Transparent.toArgb()
    currentActivity.window.navigationBarColor = MyColorScheme.surface.toArgb()

    WindowInsetsControllerCompat(currentActivity.window, LocalView.current).let { controller ->
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.navigationBars())
    }

//    val windowInsetsController =          //Not working on my phone, but working on Emulator
//        ViewCompat.getWindowInsetsController(currentActivity.window.decorView) ?: return
//    // Configure the behavior of the hidden system bars
//    windowInsetsController.systemBarsBehavior =
//        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//    // Hide both the status bar and the navigation bar
//    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

    MaterialTheme(
        colorScheme = MyColorScheme,
        typography = Typography,
        content = content
    )
}