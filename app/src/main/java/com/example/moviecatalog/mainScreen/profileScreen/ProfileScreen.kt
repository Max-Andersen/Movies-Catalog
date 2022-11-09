package com.example.moviecatalog.mainScreen.profileScreen

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.moviecatalog.ChoseGender
import com.example.moviecatalog.R
import com.example.moviecatalog.SetOutlinedTextField
import com.example.moviecatalog.isAllTextFieldsFull
import com.example.moviecatalog.signUp.DatePickerView
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(navController: NavController, model: ProfileViewModel = viewModel(), ) {
    val email = remember {
        mutableStateOf("")
    }
    val avatarLink = remember {
        mutableStateOf("")
    }
    val name = remember {
        mutableStateOf("")
    }
    val dateOfBirthday = remember {
        mutableStateOf("")
    }
    val gender = remember {
        mutableStateOf("")
    }

    val currentName = remember {
        mutableStateOf("")
    }

    val allFieldsFull = isAllTextFieldsFull(email, name, dateOfBirthday, gender)

    LaunchedEffect(key1 = true) {
        model.getInformation()
        email.value = model.email
        avatarLink.value = model.avatarLink
        name.value = model.name
        dateOfBirthday.value = model.dateOfBirthday
        gender.value = model.gender.toString()
        currentName.value = model.name
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
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.height(110.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        model = if (avatarLink.value == "") R.drawable.empty_profile_photo else avatarLink.value,
                        contentDescription = null
                    )
                    Text(
                        text = currentName.value,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }

                Text(
                    text = stringResource(id = R.string.E_Mail),
                    style = MaterialTheme.typography.bodyMedium
                )
                SetOutlinedTextField(variable = email, name = "E-Mail")

                Text(
                    text = stringResource(id = R.string.avatarLink),
                    style = MaterialTheme.typography.bodyMedium
                )
                SetOutlinedTextField(variable = avatarLink, name = "url")

                Text(
                    text = stringResource(id = R.string.name),
                    style = MaterialTheme.typography.bodyMedium
                )
                SetOutlinedTextField(variable = name, name = "Имя(name)")

                Text(
                    text = stringResource(id = R.string.dateOfBirthday),
                    style = MaterialTheme.typography.bodyMedium
                )
                DatePickerView(date = dateOfBirthday)

                Text(
                    text = stringResource(id = R.string.gender),
                    style = MaterialTheme.typography.bodyMedium
                )
                ChoseGender(model = model, gender = gender)

                Spacer(modifier = Modifier.size(10.dp))

                val context = LocalContext.current
                OutlinedButton(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val answer = model.putInformation(
                                email,
                                avatarLink,
                                name,
                                dateOfBirthday,
                                gender
                            )
                            if (answer.isNotEmpty()) {
                                launch(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Ответ от валидирующих котиков:${answer}",
                                        Toast.LENGTH_LONG
                                    ).show()
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
                            model.logout()
                            launch(Dispatchers.Main) {
                                navController.navigate("sign-In"){
                                    popUpTo(navController.graph.id)
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