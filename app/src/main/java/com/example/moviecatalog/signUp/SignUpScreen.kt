package com.example.moviecatalog.signUp

import android.app.Activity
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
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
import com.example.moviecatalog.isAllTextFieldsFull
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


@Preview()
@Composable
fun Show(){
    SignUpScreen()
}


@Composable
fun DatePickerView( mDate: MutableState<String> ) {
    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        R.style.datepicker,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth.${mMonth+1}.$mYear"
        }, mYear, mMonth, mDay
    )



    Box(
        modifier = Modifier
            .height(53.dp)
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(4.dp))
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
                text = if(mDate.value == "") "Дата рождения" else mDate.value,
                color = if(mDate.value == "") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
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
        val mDate = remember { mutableStateOf("") }

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



                DatePickerView(mDate)

                OutlinedButton(
                    onClick = { /*TODO*/ },
                    enabled = isAllTextFieldsFull(login, email, name, password, passwordConfirmation, mDate),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = if (isAllTextFieldsFull(login, email, name, password, passwordConfirmation, mDate)) MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.background
                    ),
                    border = BorderStroke(1.dp, if (isAllTextFieldsFull(login, email, name, password, passwordConfirmation, mDate)) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.onSecondary)

                ) {
                    Text(text = "Зарегистрироваться",
                        color = if (isAllTextFieldsFull(login, email, name, password, passwordConfirmation, mDate)) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.primary,
                    )
                }

                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                ) {
                    Text(text = "У меня уже есть аккаунт", color = MaterialTheme.colorScheme.primary)
                }

            }
        }
    }
}

