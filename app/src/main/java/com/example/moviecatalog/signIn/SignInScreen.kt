package com.example.moviecatalog.signIn

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviecatalog.R
import com.example.moviecatalog.SetOutlinedTextField
import com.example.moviecatalog.getTextInputColorTheme
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun SignInScreen(model: SignInViewModel = viewModel()){
    MovieCatalogTheme {
        val login = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(painter = painterResource(id = R.drawable.logo_group) ,
                    contentDescription = null,
                    modifier = Modifier.padding(
                        start = 55.dp,
                        top = 32.dp,
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


                    SetOutlinedTextField(variable = login, "Логин",textInputColorTheme = getTextInputColorTheme())

                    Spacer(modifier = Modifier.size(14.dp))

                    SetOutlinedTextField(variable = password, "Пароль",textInputColorTheme = getTextInputColorTheme())
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        ),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {


                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        enabled = isAllFieldsInSignInFull(login, password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = if (isAllFieldsInSignInFull(login, password)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.background
                        ),
                        border = BorderStroke(1.dp, if (isAllFieldsInSignInFull(login, password)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondary)

                    ) {
                        Text(text = "Войти",
                            color = if (isAllFieldsInSignInFull(login, password)) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                        )
                    }

                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                    ) {
                        Text(text = "Регистрация", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

fun isAllFieldsInSignInFull(login: MutableState<String>, password: MutableState<String>): Boolean {
    return login.value.isNotBlank() && password.value.isNotBlank()
}
