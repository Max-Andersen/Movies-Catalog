package com.example.moviecatalog.mainScreen.profileScreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviecatalog.ChoiceGender
import com.example.moviecatalog.R
import com.example.moviecatalog.SetOutlinedTextField
import com.example.moviecatalog.checkUserAlive
import com.example.moviecatalog.clearUserData
import com.example.moviecatalog.isAllTextFieldsFull
import com.example.moviecatalog.network.User.Gender
import com.example.moviecatalog.signUp.DatePickerView
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(navController: NavController, model: ProfileViewModel = viewModel()) {
    val userData = model.userData.collectAsState() as MutableState<UserData>

    val allFieldsFull = isAllTextFieldsFull(
        userData.value.email,
        userData.value.name,
        userData.value.dateOfBirthday
    )

    LaunchedEffect(Unit) {
        model.getInformation()
    }
    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .padding(start = 16.dp, top = 40.dp, end = 16.dp)
                .fillMaxSize()

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background),   //mutableStateListOf()
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.height(110.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        model = if (userData.value.avatarLink == "") R.drawable.empty_profile_photo else userData.value.avatarLink,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(88.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = userData.value.currentName,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }

                Text(
                    text = stringResource(id = R.string.E_Mail),
                    style = MaterialTheme.typography.bodyMedium
                )
                SetOutlinedTextField(value = userData.value.email, name = "E-Mail") {
                    userData.value = userData.value.copy(email = it)
                }

                Text(
                    text = stringResource(id = R.string.avatarLink),
                    style = MaterialTheme.typography.bodyMedium
                )
                SetOutlinedTextField(value = userData.value.avatarLink, name = "url") {
                    userData.value = userData.value.copy(avatarLink = it)
                }

                Text(
                    text = stringResource(id = R.string.name),
                    style = MaterialTheme.typography.bodyMedium
                )
                SetOutlinedTextField(value = userData.value.name, name = "Имя(name)") {
                    userData.value = userData.value.copy(name = it)
                }

                Text(
                    text = stringResource(id = R.string.dateOfBirthday),
                    style = MaterialTheme.typography.bodyMedium
                )
                DatePickerView(date = userData.value.dateOfBirthday) {
                    userData.value = userData.value.copy(dateOfBirthday = it)
                }

                Text(
                    text = stringResource(id = R.string.gender),
                    style = MaterialTheme.typography.bodyMedium
                )

                ChoiceGender(gender = userData.value.gender) { gender: Gender ->
                    userData.value = userData.value.copy(gender = gender)
                }

                Spacer(modifier = Modifier.size(10.dp))

                val context = LocalContext.current
                val catsAnswer = stringResource(id = R.string.validationAnswer)
                OutlinedButton(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (checkUserAlive()) {
                                try {
                                    val answer = model.putInformation(
                                        userData.value
                                    )

                                    if (answer.isNotEmpty()) {
                                        launch(Dispatchers.Main) {
                                            Toast.makeText(
                                                context,
                                                catsAnswer + answer,
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                } catch (e: Exception) {
                                    launch(Dispatchers.Main) {
                                        Toast.makeText(
                                            context,
                                            "HTTP 400 Bad Request",
                                            Toast.LENGTH_LONG
                                        ).show()
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
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (allFieldsFull) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.background
                    ),
                    border = BorderStroke(
                        1.dp, if (allFieldsFull) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSecondary
                    ),
                    enabled = allFieldsFull

                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        color = if (allFieldsFull) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                TextButton(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (checkUserAlive()) {
                                model.logout()
                                launch(Dispatchers.Main) {
                                    navController.navigate("sign-In") {
                                        popUpTo(navController.graph.id)
                                    }
                                    clearUserData()
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
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.logout),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.size(60.dp))

            }

        }
    }
}