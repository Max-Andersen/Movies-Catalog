package com.example.moviecatalog.mainScreen.movieScreen

import android.util.Log
import com.example.moviecatalog.network.Movie.MovieDetailsResponse
import com.example.moviecatalog.mainScreen.movieData.ReviewsDetails
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
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
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviecatalog.R
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
    val dataExist = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            model.loadMovieDetails(filmId)
            dataExist.value = true
        }
    }

    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (dataExist.value) {
                FilmContent(navController, model.movieData)
            }
            Log.d("data--->", model.movieData.poster.isNotBlank().toString())
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
fun FilmContent(navController: NavController, movieData: MovieDetailsResponse?) {
    val scroll: ScrollState = rememberScrollState(0)

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    if (movieData != null) {
        if (movieData.poster != "") {
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
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImageHeader(
    scroll: ScrollState,
    headerHeightPx: Float,
    movieData: MovieDetailsResponse
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

@Composable
fun SetStar(number: Int, amount: MutableState<Int>) {
    Image(
        painter = painterResource(id = if (amount.value >= number) R.drawable.full_star else R.drawable.star),
        contentDescription = null,
        modifier = Modifier
            .clickable { amount.value = number }
            .size(24.dp)
    )
}

fun separatedNumber(number: Int): String{
    var result = ""
    val x = number.toString()
    var index = 1
    for (char in x.reversed()){
        result += char
        if (index % 3 == 0 && index != x.length){
            result += " "
        }
        index++
    }
    return result.reversed()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Body(scroll: ScrollState, movieData: MovieDetailsResponse) {
    val openReviewDialog = remember {
        mutableStateOf(false)
    }

    val reviewText = remember {
        mutableStateOf("")
    }

    val starAmount = remember {
        mutableStateOf(0)
    }

    if (openReviewDialog.value) {
        AlertDialog(
            onDismissRequest = { openReviewDialog.value = false },
            title = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.makeReview),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        for (i in 1..10) {
                            SetStar(number = i, amount = starAmount)
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(
                        value = reviewText.value,
                        onValueChange = { newText -> reviewText.value = newText },
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onPrimary,
                                RoundedCornerShape(8.dp)
                            )
                            .height(120.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            textColor = MaterialTheme.colorScheme.background
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.someText),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        onClick = { openReviewDialog.value = false }, // TODO(сохранение)
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(4.dp)
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.save),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    TextButton(
                        onClick = { openReviewDialog.value = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                }

            },
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            buttons = { }
        )
    }

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
                    text = stringResource(id = R.string.aboutMovie),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.size(1.dp))

                if (movieData.year != 0) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = stringResource(id = R.string.year))
                        DataDescriptionText(text = movieData.year.toString())
                    }
                }
                if (movieData.country != "") {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = stringResource(id = R.string.country))
                        DataDescriptionText(text = movieData.country)
                    }
                }
                if (movieData.time != 0) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = stringResource(id = R.string.time))
                        DataDescriptionText(text = "${movieData.time} ${stringResource(id = R.string.minutes)}")
                    }
                }
                if (movieData.tagline != "") {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = stringResource(id = R.string.tagline))
                        DataDescriptionText(text = "«${movieData.tagline}»")
                    }
                }
                if (movieData.director != "") {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = stringResource(id = R.string.director))
                        DataDescriptionText(text = movieData.director)
                    }
                }
                if (movieData.budget != 0) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = stringResource(id = R.string.budget))
                        DataDescriptionText(text = "\$${separatedNumber(movieData.budget)}")
                    }
                }
                if (movieData.fees != 0) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = stringResource(id = R.string.fees))
                        DataDescriptionText(text = "\$${separatedNumber(movieData.fees)}")
                    }
                }
                if (movieData.ageLimit != 0) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AboutFilmDescriptionText(text = stringResource(id = R.string.ageLimit))
                        DataDescriptionText(text = "${movieData.ageLimit}+")
                    }
                }

                Spacer(modifier = Modifier.size(1.dp))

                Text(
                    text = stringResource(id = R.string.gender),
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
                        text = stringResource(id = R.string.reviews),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = null,
                        modifier = Modifier.clickable { openReviewDialog.value = true }
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
fun ReviewBox(review: ReviewsDetails) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (avatar, author, rating) = createRefs()
                GlideImage(
                    model = if (review.author.avatar == null || review.author.avatar == "") R.drawable.empty_profile_photo else review.author.avatar,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .constrainAs(avatar) {
                            top.linkTo(parent.top, 8.dp)
                            start.linkTo(parent.start, 8.dp)
                        }
                )
                Column(modifier = Modifier.constrainAs(author) {
                    start.linkTo(parent.start, 56.dp)
                    top.linkTo(parent.top, 8.dp)
                }) {
                    Text(
                        text = if (!review.isAnonymous) review.author.nickName else stringResource(
                            id = R.string.anonymous
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    //TODO(Надпись "Мой отзыв")
                }
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(16.dp)
                        )
                        .constrainAs(rating) {
                            top.linkTo(parent.top, 14.dp)
                            end.linkTo(parent.end, 8.dp)
                        }
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
                text = review.createDateTime.slice(8..9) + "." + review.createDateTime.slice(5..6) + "." + review.createDateTime.slice(
                    0..3
                ),
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
    movieData: MovieDetailsResponse
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
                //val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2

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