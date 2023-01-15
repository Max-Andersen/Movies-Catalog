package com.example.moviecatalog.mainScreen.movieScreen

import com.example.moviecatalog.network.Movie.MovieDetailsResponse
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviecatalog.R
import com.example.moviecatalog.mainScreen.movieData.Genres
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MovieScreen(
    filmId: String,
    navController: NavController,
    model: MovieScreenViewModel = viewModel()
) {
    val data = model.getData(filmId).collectAsState(initial = null)

    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (data.value != null) {
                FilmContent(navController, model.movieData, model)
            }
        }
    }
}

private val headerHeight = 320.dp
private val toolbarHeight = 56.dp

private val paddingMedium = 16.dp

private val titlePaddingStart = 16.dp
private val titlePaddingEnd = 49.dp

private const val titleFontScaleStart = 1f
private const val titleFontScaleEnd = 0.66f

@Composable
fun FilmContent(
    navController: NavController,
    movieData: MovieDetailsResponse,
    model: MovieScreenViewModel
) {
    val scroll: ScrollState = rememberScrollState(0)

    val scrollCompleted = remember {
        mutableStateOf(false)
    }

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ImageHeader(scroll, headerHeightPx, movieData.poster)
        Body(scroll, model, navController)
        Toolbar(scroll, headerHeightPx, navController, model, scrollCompleted)
        Title(scroll, headerHeightPx, toolbarHeightPx, movieData.name, scrollCompleted)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImageHeader(
    scroll: ScrollState,
    headerHeightPx: Float,
    poster: String
) {
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
            model = poster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp))
        )
    }
}

fun separatedNumber(number: Int): String {
    var result = ""
    val x = number.toString()
    var index = 1
    for (char in x.reversed()) {
        result += char
        if (index % 3 == 0 && index != x.length) {
            result += " "
        }
        index++
    }
    return result.reversed()
}

@Composable
fun MovieDataDescriptionItem(itemName: String, itemData: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = itemName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = itemData,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun MovieDataDescription(movieData: MovieDetailsResponse) {
    Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        if (movieData.year != 0) {
            MovieDataDescriptionItem(
                itemName = stringResource(id = R.string.year),
                itemData = movieData.year.toString()
            )
        }
        if (movieData.country != "") {
            MovieDataDescriptionItem(
                itemName = stringResource(id = R.string.country),
                itemData = movieData.country
            )
        }
        if (movieData.time != 0) {
            MovieDataDescriptionItem(
                itemName = stringResource(id = R.string.time),
                itemData = "${movieData.time} ${stringResource(id = R.string.minutes)}"
            )
        }
        if (movieData.tagline != "") {
            MovieDataDescriptionItem(
                itemName = stringResource(id = R.string.tagline),
                itemData = "«${movieData.tagline}»"
            )
        }
        if (movieData.director != "") {
            MovieDataDescriptionItem(
                itemName = stringResource(id = R.string.director),
                itemData = movieData.director
            )
        }
        if (movieData.budget != 0) {
            MovieDataDescriptionItem(
                itemName = stringResource(id = R.string.budget),
                itemData = "\$${separatedNumber(movieData.budget)}"
            )
        }
        if (movieData.fees != 0) {
            MovieDataDescriptionItem(
                itemName = stringResource(id = R.string.fees),
                itemData = "\$${separatedNumber(movieData.fees)}"
            )
        }
        if (movieData.ageLimit != 0) {
            MovieDataDescriptionItem(
                itemName = stringResource(id = R.string.ageLimit),
                itemData = "${movieData.ageLimit}+"
            )
        }
    }
}

@Composable
fun PlaceGenres(genres: List<Genres>) {
    FlowRow(
        mainAxisAlignment = MainAxisAlignment.Start,
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 8.dp,
        mainAxisSpacing = 8.dp
    ) {
        genres.toList().forEach { genre ->
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
}

@Composable
private fun Body(
    scroll: ScrollState,
    model: MovieScreenViewModel,
    navController: NavController
) {
    val openReviewDialog = remember {
        mutableStateOf(false)
    }

    val movieData = model.movieData

    ReviewDialog(navController = navController, model = model, openReviewDialog = openReviewDialog)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
    ) {
        Spacer(Modifier.height(headerHeight))
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = movieData.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.size(0.dp))

                Text(
                    text = stringResource(id = R.string.aboutMovie),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                MovieDataDescription(movieData = movieData)

                Spacer(modifier = Modifier.size(0.dp))

                Text(
                    text = stringResource(id = R.string.genres),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                PlaceGenres(genres = movieData.genres)

                Spacer(modifier = Modifier.size(0.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.reviews),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { openReviewDialog.value = true }
                            .size(24.dp)
                    )
                }

                model.myReview?.let {
                    ReviewBox(
                        review = it,
                        model = model,
                        navController,
                        openReviewDialog
                    )
                }

                for (review in movieData.reviews) {
                    if (review != model.myReview) {
                        ReviewBox(review = review, model, navController)
                    }
                }
                Spacer(modifier = Modifier.size(100.dp))
            }
        }
    }
}

@Composable
fun HeartAnimation(
    isChanged: MutableState<Boolean>,
    state: Boolean
) {
    val likeComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.heart_like_animation))
    val likeProgress by animateLottieCompositionAsState(
        likeComposition,
    )

    val unlikeComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.heart_unlike_animation))
    val unlikeProgress by animateLottieCompositionAsState(
        unlikeComposition,
    )
    if (state) {
        LottieAnimation(
            likeComposition,
            likeProgress,
            alignment = Alignment.CenterEnd,
            modifier = Modifier.padding(top = 17.dp)
        )
    } else {
        LottieAnimation(
            unlikeComposition,
            unlikeProgress,
            alignment = Alignment.CenterEnd,
            modifier = Modifier.padding(top = 17.dp)
        )
    }
    if (likeProgress == 1f || unlikeProgress == 1f) {
        isChanged.value = false
    }
}

@Composable
private fun Toolbar(
    scroll: ScrollState,
    headerHeightPx: Float,
    navController: NavController,
    model: MovieScreenViewModel,
    scrollCompleted: MutableState<Boolean>
) {
    val isFavorite = remember {
        mutableStateOf(model.movieData.id in model.favoriteMovies)
    }

    val half = headerHeightPx / 4f
    val position = if (scroll.value > half) (scroll.value - half) / (half * 2.5f) else 0f

    TopAppBar(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
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
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = model.movieData.name,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .padding(start = 49.dp, end = 48.dp, top = 33.dp, bottom = 12.dp)
                            .alpha(if (scrollCompleted.value) 1f else 0f)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    val isChanged = remember {
                        mutableStateOf(false)
                    }

                    if (isChanged.value) {
                        HeartAnimation(isChanged, isFavorite.value)
                    } else {
                        val image = if (isFavorite.value) {
                            R.drawable.heart_transformed_full
                        } else {
                            R.drawable.heart_transformed
                        }

                        Box(
                            modifier = Modifier
                                .padding(top = 40.dp, bottom = 16.dp, end = 16.dp),
                        ) {
                            Image(
                                painter = painterResource(id = image),
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        isChanged.value = true
                                        CoroutineScope(Dispatchers.IO).launch {
                                            if (isFavorite.value) {
                                                model.deleteFromFavorite(model.movieData.id)
                                            } else {
                                                model.addToFavorite(model.movieData.id)
                                            }
                                            isFavorite.value = !isFavorite.value
                                        }
                                    }
                            )
                        }
                    }
                }


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
    filmName: String,
    scrollCompleted: MutableState<Boolean>
) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    var titleWidthPx by remember { mutableStateOf(0f) }
    val titleHeightDp = with(LocalDensity.current) { titleHeightPx.toDp() }

    var scaleXY by remember {
        mutableStateOf(0.dp)
    }

    Text(
        text = filmName,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .graphicsLayer {
                val collapseRange: Float =
                    (headerHeightPx - toolbarHeightPx) // text should move to center of image header
                val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

                val titleY = lerp(
                    headerHeight - titleHeightDp - paddingMedium, // start Y
                    toolbarHeight / 1.15f - titleHeightDp / 2, // end Y
                    collapseFraction
                )

                scaleXY = lerp(
                    titleFontScaleStart.dp,
                    titleFontScaleEnd.dp,
                    collapseFraction
                )

                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2

                val titleX = lerp(
                    titlePaddingStart, // start X
                    titlePaddingEnd - titleExtraStartPadding, // end X
                    collapseFraction
                )

                translationY = titleY.toPx()
                translationX = titleX.toPx()

                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()

                scrollCompleted.value = scroll.value >= headerHeightPx / 2
            }
            .alpha(if (scrollCompleted.value) 0f else 1f),
        color = MaterialTheme.colorScheme.onPrimary,
    )
}