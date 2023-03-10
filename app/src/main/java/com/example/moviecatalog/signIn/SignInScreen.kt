package com.example.moviecatalog.signIn

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.moviecatalog.R
import com.example.moviecatalog.SetOutlinedTextField
import com.example.moviecatalog.isAllTextFieldsFull
import com.example.moviecatalog.repository.AuthRepository
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SignInScreen(model: SignInViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current

    MovieCatalogTheme {
        val coroutineScope = rememberCoroutineScope()

        val allTextFieldsFull = isAllTextFieldsFull(
            model.login,
            model.password
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_group),
                    contentDescription = null,
                    modifier = Modifier.padding(
                        start = 55.dp,
                        top = 56.dp,
                        end = 55.dp
                    )
                )

                Spacer(modifier = Modifier.size(48.dp))
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                    ),

                    ) {

                    SetOutlinedTextField(
                        variable = model.login,
                        stringResource(id = R.string.login)
                    )

                    Spacer(modifier = Modifier.size(14.dp))

                    SetOutlinedTextField(
                        variable = model.password,
                        stringResource(id = R.string.password)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 32.dp
                        ),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {

                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                val answer = model.signInButtonPressed()
                                if (answer.first == 1) {
                                    println("Success")
                                    launch(Dispatchers.Main) {
                                        navController.navigate("mainScreen") {
                                            popUpTo(
                                                navController.graph.id
                                            )
                                        }
                                    }

                                } else {
                                    println("Fail")
                                    launch(Dispatchers.Main) {
                                        Toast.makeText(
                                            context,
                                            answer.second,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        },
                        enabled = allTextFieldsFull,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = if (allTextFieldsFull) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.background
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (allTextFieldsFull) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondary
                        )

                    ) {
                        Text(
                            text = stringResource(id = R.string.enter),
                            color = if (allTextFieldsFull
                            ) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    TextButton(
                        onClick = { navController.navigate("sign-up") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.registration),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}


