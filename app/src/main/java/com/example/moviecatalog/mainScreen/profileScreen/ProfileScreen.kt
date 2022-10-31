package com.example.moviecatalog.mainScreen.profileScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviecatalog.ChoseGender
import com.example.moviecatalog.R
import com.example.moviecatalog.SetOutlinedTextField
import com.example.moviecatalog.mainScreen.profileScreen.ProfileViewModel
import com.example.moviecatalog.signUp.DatePickerView
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun ProfileScreen(model: ProfileViewModel = viewModel()) {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier
                .padding(start = 16.dp, top = 40.dp, end = 16.dp)
                .fillMaxSize()

        ) {
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
                    Image(
                        painter = painterResource(id = R.drawable.empty_profile_photo),
                        contentDescription = null
                    )
                    Text(
                        text = "Test",
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }

                Text(text = "E-mail", style = MaterialTheme.typography.bodyMedium)
                SetOutlinedTextField(variable = email, name = "E-Mail")

                Text(text = "Ссылка на аватарку", style = MaterialTheme.typography.bodyMedium)
                SetOutlinedTextField(variable = avatarLink, name = "url")

                Text(text = "Имя", style = MaterialTheme.typography.bodyMedium)
                SetOutlinedTextField(variable = name, name = "Имя(name)")

                Text(text = "Дата рождения", style = MaterialTheme.typography.bodyMedium)
                DatePickerView(date = dateOfBirthday)

                Text(text = "Пол", style = MaterialTheme.typography.bodyMedium)
                ChoseGender(model = model, gender = gender)

                Spacer(modifier = Modifier.size(10.dp))

                val context = LocalContext.current
                OutlinedButton(
                    onClick = { },  //TODO(валидация + отправка на сервер)
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.background
                    ),
                    border = BorderStroke(
                        1.dp, MaterialTheme.colorScheme.primary
                    )

                ) {
                    Text(
                        text = "Сохранить",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                TextButton(
                    onClick = { },  //TODO(logout)
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                ) {
                    Text(
                        text = "Выйти из аккаунта",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.size(60.dp))

            }

        }
    }
}