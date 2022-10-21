package com.example.moviecatalog.signUp

import android.app.Activity
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviecatalog.SetOutlinedTextField
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


@Preview()
@Composable
fun Show(){
    SignUpScreen()
}


@Composable
fun DatePickerView( ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(4.dp))
            .clickable {
            }
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val (label, iconView) = createRefs()

            Text(
                text= "Дата рождения",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(label) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    },
                style = MaterialTheme.typography.bodySmall
            )

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = MaterialTheme.colorScheme.onSurface
            )

        }

    }
}

@Composable
fun SignUpScreen(model: SignUpViewModel = viewModel()){ //
    MovieCatalogTheme {
        val login = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val name = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val passwordConfirmation = remember { mutableStateOf("") }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 56.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                //horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo_group),
                    contentDescription = null,
                    modifier = Modifier.padding(
                        start = 90.dp,
                        end = 90.dp
                    )
                )

                Text(
                    text = "Регистрация",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineLarge,
                )

                SetOutlinedTextField(login, "Логин")
                SetOutlinedTextField(email, "E-mail")
                SetOutlinedTextField(name, "Имя")
                SetOutlinedTextField(password, "Пароль")
                SetOutlinedTextField(passwordConfirmation, "Подтвердите пароль")

                DatePickerView()

            }
        }
    }
}

