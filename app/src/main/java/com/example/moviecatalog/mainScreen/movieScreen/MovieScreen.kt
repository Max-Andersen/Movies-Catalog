package com.example.moviecatalog.mainScreen.movieScreen


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviecatalog.R
import com.example.moviecatalog.mainScreen.movieData.MovieDetails
import com.example.moviecatalog.mainScreen.movieData.Review
import com.example.moviecatalog.repository.MovieRepository
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun MovieScreen(filmId: String, navController: NavController) {

    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            FilmContent(navController = navController, filmId)
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
fun FilmContent(navController: NavController, filmId: String) {
    val scroll: ScrollState = rememberScrollState(0)

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    val movieRepository = MovieRepository()
    val movieData = movieRepository.loadMovieDetails(filmId)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ImageHeader(scroll, headerHeightPx, movieData)
        Body(scroll, movieData)
        Toolbar(scroll, headerHeightPx, navController)
        Title(scroll, headerHeightPx, toolbarHeightPx, movieData)

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImageHeader(scroll: ScrollState, headerHeightPx: Float, movieData: MovieDetails) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f // Parallax effect
                alpha = (-1f / headerHeightPx) * scroll.value + 1
            }
    ) {

        GlideImage(
            model = movieData.poster,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun AboutFilmDescriptionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.width(100.dp)
    )
}

@Composable
fun DataDescriptionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Body(scroll: ScrollState, movieData: MovieDetails) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
    ) {
        Spacer(Modifier.height(headerHeight))
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = movieData.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.size(1.dp))

                Text(
                    text = "О фильме",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.size(1.dp))

                if (movieData.year != "") {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = "Год")
                        DataDescriptionText(text = movieData.year)
                    }
                }
                if (movieData.country != "") {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = "Страна")
                        DataDescriptionText(text = movieData.country)
                    }
                }
                if (movieData.time != 0) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = "Время")
                        DataDescriptionText(text = "${movieData.time} мин.")
                    }
                }
                if (movieData.tagline != "") {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = "Слоган")
                        DataDescriptionText(text = "«${movieData.tagline}»")
                    }
                }
                if (movieData.director != "") {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = "Режисёр")
                        DataDescriptionText(text = movieData.director)
                    }
                }
                if (movieData.budget != 0) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = "Бюджет")
                        DataDescriptionText(text = "\$${movieData.budget}")
                    }
                }
                if (movieData.fees != 0) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = "Сборы в мире")
                        DataDescriptionText(text = "\$${movieData.fees}")
                    }
                }
                if (movieData.ageLimit != 0) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = "Возраст")
                        DataDescriptionText(text = "${movieData.ageLimit}+")
                    }
                }

                Spacer(modifier = Modifier.size(1.dp))

                Text(
                    text = "Жанры",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                FlowRow(
                    mainAxisAlignment = MainAxisAlignment.Start,
                    mainAxisSize = SizeMode.Expand,
                    crossAxisSpacing = 8.dp,
                    mainAxisSpacing = 8.dp
                ) {
                    movieData.genres.toList().forEach { genre ->
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(8.dp)
                                )
                                .height(27.dp)
                        ) {
                            Text(
                                text = genre.name,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    top = 6.dp,
                                    bottom = 6.dp,
                                    end = 16.dp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Отзывы",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = null
                    )
                }

                for (review in movieData.reviews) {
                    ReviewBox(review = review)
                }

                Spacer(modifier = Modifier.size(180.dp))
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReviewBox(review: Review) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    model = review.avatar,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(
                        text = if (!review.isAnonymous) review.nickName else "Анонимный пользователь",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,

                        )
                }
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, start = 220.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        text = review.rating.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 4.dp,
                            bottom = 4.dp
                        )
                    )
                }
            }
            Text(
                text = review.reviewText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Text(
                text = review.createDateTime,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 8.dp)
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
fun Title(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float,
    movieData: MovieDetails
) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    val titleHeightDp = with(LocalDensity.current) { titleHeightPx.toDp() }

    Text(
        text = movieData.name,
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
            },
        color = MaterialTheme.colorScheme.onPrimary

    )
}