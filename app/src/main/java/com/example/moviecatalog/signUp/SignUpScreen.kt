package com.example.moviecatalog.signUp

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviecatalog.ChoiceGender
import com.example.moviecatalog.R
import com.example.moviecatalog.SetOutlinedTextField
import com.example.moviecatalog.isAllTextFieldsFull
import com.example.moviecatalog.network.Auth.AuthResponse
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date


@Composable
fun DatePickerView(date: String, onDateChanged: (String) -> Unit) {
    val context = LocalContext.current

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()

    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        context,
        R.style.datepicker,
        { _: DatePicker, mYear: Int, mMonth: Int, dayOfMonth: Int ->
            if (dayOfMonth < 10 && mMonth < 9) onDateChanged("0$dayOfMonth.0${mMonth + 1}.$mYear")
            else if (dayOfMonth < 10) onDateChanged("0$dayOfMonth.${mMonth + 1}.$mYear")
            else if (mMonth < 9) onDateChanged("$dayOfMonth.0${mMonth + 1}.$mYear")
            else onDateChanged("$dayOfMonth.${mMonth + 1}.$mYear")
        }, year, month, day
    )

    Box(
        modifier = Modifier
            .height(53.dp)
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
            .clickable {
                mDatePickerDialog.show()
            }
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            val (label, iconView) = createRefs()

            Text(
                text = if (date == "") stringResource(id = R.string.dateOfBirthday) else date,
                color = if (date == "") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(label) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    },
                style = MaterialTheme.typography.bodySmall,

                )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.calendar),
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
fun SignUpScreen(model: SignUpViewModel = viewModel(), navController: NavController) {
    MovieCatalogTheme {

        val state = model.userData.collectAsState() as MutableState<SignUpScreenState>

        val allTextsFull = isAllTextFieldsFull(
            state.value.login,
            state.value.email,
            state.value.name,
            state.value.password,
            state.value.passwordConfirmation,
            state.value.dateOfBirthday,
        )
        val scale = remember {
            Animatable(1f)
        }

        LaunchedEffect(Unit) {
            scale.animateTo(
                targetValue = 0.6f,
                animationSpec = tween(
                    durationMillis = 180,
                    easing = LinearOutSlowInEasing
                )
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),

                ) {

                Image(
                    painter = painterResource(id = R.drawable.logo_group),
                    contentDescription = null,
                    modifier = Modifier.padding(
                        top = scale.value.dp * (-40) + 40.dp,
                        start = scale.value.dp * (-127.5f) + 166.5f.dp,
                        end = scale.value.dp * (-127.5f) + 166.5f.dp
                    )
                )

                Text(
                    text = stringResource(id = R.string.registration),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineLarge,
                )

                SetOutlinedTextField(state.value.login, stringResource(id = R.string.login)) {
                    state.value = state.value.copy(login = it)
                }
                SetOutlinedTextField(state.value.email, stringResource(id = R.string.E_Mail)) {
                    state.value = state.value.copy(email = it)
                }
                SetOutlinedTextField(state.value.name, stringResource(id = R.string.name)) {
                    state.value = state.value.copy(name = it)
                }
                SetOutlinedTextField(state.value.password, stringResource(id = R.string.password)) {
                    state.value = state.value.copy(password = it)
                }
                SetOutlinedTextField(
                    state.value.passwordConfirmation,
                    stringResource(id = R.string.passwordConfirmation)
                ) {
                    state.value = state.value.copy(passwordConfirmation = it)
                }

                DatePickerView(state.value.dateOfBirthday) {
                    state.value = state.value.copy(dateOfBirthday = it)
                }

                ChoiceGender(gender = state.value.gender) {
                    state.value = state.value.copy(gender = it)
                }

                Spacer(modifier = Modifier.size(16.dp))

                val context = LocalContext.current
                OutlinedButton(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val answer = model.register(state.value)

                            launch(Dispatchers.Main) {
                                when(answer){
                                    is AuthResponse.Success ->
                                        navController.navigate("mainScreen") {
                                            popUpTo(
                                                navController.graph.id
                                            )
                                        }
                                    is AuthResponse.Fail -> Toast.makeText(
                                        context,
                                        "Ответ от валидирующих котиков:${answer.cause}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    },
                    enabled = allTextsFull,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (allTextsFull) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.background
                    ),
                    border = BorderStroke(
                        1.dp, if (allTextsFull) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSecondary
                    )

                ) {
                    Text(
                        text = stringResource(id = R.string.register),
                        color = if (allTextsFull) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                TextButton(
                    onClick = { navController.navigate("sign-in") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.iHaveAccountAlready),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.size(32.dp))
            }
        }
    }
}

