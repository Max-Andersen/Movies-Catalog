package com.example.moviecatalog.mainScreen.movie


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.navigation.NavController
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun MovieScreen(filmId: String, navController: NavController) {

    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            FilmContent(navController = navController)
        }
    }
}

private val headerHeight = 320.dp
private val toolbarHeight = 56.dp

private val paddingMedium = 16.dp

private val titlePaddingStart = 16.dp
private val titlePaddingEnd = 40.dp

private const val titleFontScaleStart = 1f
private const val titleFontScaleEnd = 0.66f

@Composable
fun FilmContent(navController: NavController) {
    val scroll: ScrollState = rememberScrollState(0)

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ImageHeader(scroll, headerHeightPx)
        Body(scroll)
        Toolbar(scroll, headerHeightPx, navController)
        Title(scroll, headerHeightPx, toolbarHeightPx)
    }
}

@Composable
private fun ImageHeader(scroll: ScrollState, headerHeightPx: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f // Parallax effect
                alpha = (-1f / headerHeightPx) * scroll.value + 1
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.the_magicians),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
private fun Body(scroll: ScrollState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scroll)
    ) {
        Spacer(Modifier.height(headerHeight))
        repeat(5) {
            androidx.compose.material.Text(
                text = stringResource(R.string.test_string),
                color = MaterialTheme.colorScheme.secondary,
                style = androidx.compose.material.MaterialTheme.typography.body1,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun Toolbar(
    scroll: ScrollState,
    headerHeightPx: Float,
    navController: NavController
) {
    val isFavorite = remember {
        mutableStateOf(false)
    }

    val half = headerHeightPx / 4f
    val position = if (scroll.value > half) (scroll.value - half) / (half * 2.5f) else 0f
    println("$position  $half")
    TopAppBar(
        modifier = Modifier.height(80.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(position)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = if (!isFavorite.value) R.drawable.heart else R.drawable.full_heart),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 40.dp, bottom = 16.dp, end = 16.dp)
                        .clickable { isFavorite.value = !isFavorite.value }
                )
            }
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 16.dp, start = 16.dp)
                    .clickable { navController.navigateUp() }
            )

        }
    }

}

@Composable
fun Title(scroll: ScrollState, headerHeightPx: Float, toolbarHeightPx: Float) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    val titleHeightDp = with(LocalDensity.current) { titleHeightPx.toDp() }

    Text(
        text = "NAME OF FILM",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .graphicsLayer {
                val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
                val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)
                val titleY = lerp(
                    headerHeight - titleHeightDp - paddingMedium, // start Y
                    toolbarHeight / 1.15f - titleHeightDp / 2f, // end Y
                    collapseFraction
                )

                val titleX = lerp(
                    titlePaddingStart,
                    titlePaddingEnd,
                    collapseFraction
                )

                val scaleXY = lerp(
                    titleFontScaleStart.dp,
                    titleFontScaleEnd.dp,
                    collapseFraction
                )

                translationY = titleY.toPx()
                translationX = titleX.toPx()

                scaleX = scaleXY.value
                scaleY = scaleXY.value

            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
            }

    )
}