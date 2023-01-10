package com.example.moviecatalog.mainScreen.movieScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviecatalog.R
import com.example.moviecatalog.checkUserAlive
import com.example.moviecatalog.clearUserData
import com.example.moviecatalog.mainScreen.calculateColor
import com.example.moviecatalog.mainScreen.movieData.ReviewsDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun refreshScreen(navController: NavController, movieId: String) {
    navController.popBackStack()
    navController.navigate("movie/${movieId}")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDialog(
    navController: NavController,
    model: MovieScreenViewModel,
    openReviewDialog: MutableState<Boolean>
) {
    val isAnonymous = remember {
        mutableStateOf(false)
    }

    val reviewText = remember {
        mutableStateOf("")
    }

    val starAmount = remember {
        mutableStateOf(0)
    }

    if (openReviewDialog.value) {
        if (model.myReview != null) {
            reviewText.value = model.myReview!!.reviewText
            starAmount.value = model.myReview!!.rating
            isAnonymous.value = model.myReview!!.isAnonymous
        }

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

                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                        val (anonymousText, checkBox) = createRefs()

                        Text(
                            text = stringResource(id = R.string.anonymousReview),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.constrainAs(anonymousText) {
                                start.linkTo(parent.start)
                            }
                        )

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .constrainAs(checkBox) {
                                    end.linkTo(parent.end)
                                }
                                .clickable {
                                    isAnonymous.value = !isAnonymous.value
                                }
                        ) {
                            Image(
                                painter = painterResource(id = if (isAnonymous.value) R.drawable.full_checkbox else R.drawable.empty_checkbox),
                                contentDescription = null
                            )
                        }

                    }


                    Spacer(modifier = Modifier.size(16.dp))
                    val context = LocalContext.current
                    Button(
                        onClick = {
                            openReviewDialog.value = false

                            CoroutineScope(Dispatchers.IO).launch {
                                if (checkUserAlive()) {
                                    if (model.myReview != null) {
                                        try {
                                            model.editReview(
                                                model.movieData.id,
                                                reviewText.value,
                                                starAmount.value,
                                                isAnonymous.value,
                                                model.myReview!!.id
                                            )
                                            launch(Dispatchers.Main) {
                                                refreshScreen(navController, model.movieData.id)
                                            }
                                        } catch (e: Exception) {
                                            launch(Dispatchers.Main) {
                                                Toast.makeText(
                                                    context,
                                                    "Косяк api! нельзя изменить отзыв на анонимный!",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }

                                    } else {
                                        model.addReview(
                                            model.movieData.id,
                                            reviewText.value,
                                            starAmount.value,
                                            isAnonymous.value
                                        )
                                        launch(Dispatchers.Main) {
                                            refreshScreen(navController, model.movieData.id)
                                        }
                                    }
                                } else {
                                    launch(Dispatchers.Main) {
                                        navController.navigate("sign-In") {
                                            popUpTo(navController.graph.id)
                                        }
                                        clearUserData()
                                    }
                                }
                                //model.getMyReview()
                            }
                        },
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
                        onClick = {
                            openReviewDialog.value = false
                        },
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
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReviewBox(
    review: ReviewsDetails,
    model: MovieScreenViewModel,
    navController: NavController,
    openReviewDialog: MutableState<Boolean>? = null,
) {
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
                    model = if (review.author == null) R.drawable.empty_profile_photo else (if (review.author.avatar == null || review.author.avatar == "") R.drawable.empty_profile_photo else review.author.avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .constrainAs(avatar) {
                            top.linkTo(parent.top, 8.dp)
                            start.linkTo(parent.start, 8.dp)
                        },
                    contentScale = ContentScale.Crop
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
                    if (review.author != null) {
                        if (review.author.userId == model.myId) {
                            Text(
                                text = stringResource(id = R.string.myReview),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp
                            )
                        }
                    }

                }
                Box(
                    modifier = Modifier
                        .background(
                            calculateColor(review.rating.toFloat()),
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

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (date, editButton, deleteButton) = createRefs()

                Text(
                    text = review.createDateTime.slice(8..9) + "." + review.createDateTime.slice(5..6) + "." + review.createDateTime.slice(
                        0..3
                    ),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                    modifier = Modifier.constrainAs(date) {//.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 8.dp)
                        start.linkTo(parent.start, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
                )

                if (review.author != null) {
                    if (review.author.userId == model.myId) {
                        Image(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = null,
                            modifier = Modifier
                                .constrainAs(deleteButton) {
                                    end.linkTo(parent.end, 8.dp)
                                    bottom.linkTo(parent.bottom, 8.dp)
                                }
                                .clickable {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        if (checkUserAlive()) {
                                            model.deleteReview(
                                                model.movieData.id,
                                                model.myReview!!.id
                                            )
                                            //model.getMyReview()
                                            launch(Dispatchers.Main) {
                                                refreshScreen(navController, model.movieData.id)
                                            }
                                        } else {
                                            launch(Dispatchers.Main) {
                                                navController.navigate("sign-In") {
                                                    popUpTo(navController.graph.id)
                                                }
                                                clearUserData()
                                            }
                                        }

                                    }
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = null,
                            modifier = Modifier
                                .constrainAs(editButton) {
                                    end.linkTo(deleteButton.start, 8.dp)
                                    bottom.linkTo(parent.bottom, 8.dp)
                                }
                                .clickable {
                                    if (openReviewDialog != null) {
                                        openReviewDialog.value = true
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}