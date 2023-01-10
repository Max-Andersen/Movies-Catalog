package com.example.moviecatalog.signUp

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.network.Auth.RegisterRequestBody
import com.example.moviecatalog.repository.AuthRepository
import java.util.*


class SignUpViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    fun changeGender(
        currentGender: MutableState<String>,
        buttonType: String
    ) { // 0 - male   1 - female
        if (currentGender.value != buttonType) {
            currentGender.value = buttonType
        }
    }

    private fun normalizeDate(date: String): String {
        var result = ""
        result += date.slice(6..9) + "-"
        result += date.slice(3..4) + "-"
        result += date.slice(0..1)
        result += "T18:14:23.985Z"
        return result
    }

    suspend fun register(
        login: MutableState<String>,
        email: MutableState<String>,
        name: MutableState<String>,
        password: MutableState<String>,
        passwordConfirmation: MutableState<String>,
        dateOfBirthday: MutableState<String>,
        gender: MutableState<String>, context: Context
    ): Pair<Int, String> {  // the fields are not empty, because then the button would be inactive

        val emailCorrect =
            !TextUtils.isEmpty(email.value) && Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
        var resultMessage = ""

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)


        val enteredDay = dateOfBirthday.value.substringBefore('.').toInt()
        val enteredMonth = dateOfBirthday.value.substringAfter('.').substringBefore(".").toInt()
        val enteredYear = dateOfBirthday.value.substringAfterLast('.').toInt()


        if (!emailCorrect) {
            resultMessage += "\nНеверная почта!"
        }

        if (password.value != passwordConfirmation.value) {
            resultMessage += "\nПароли не совпадают!"
        }

        if ((enteredYear > currentYear) ||
            (enteredYear == currentYear && enteredMonth > currentMonth) ||
            (enteredYear == currentYear && enteredMonth == currentMonth && enteredDay >= currentDay)
        ) {
            resultMessage += "\nДата рождения должна быть меньше текущего дня!"
        }

        var success = 0
        var answer = ""

        val normalizedDate = normalizeDate(dateOfBirthday.value)

        if (resultMessage.isEmpty()) {
            authRepository.register(
                RegisterRequestBody(
                    login.value,
                    name.value,
                    password.value,
                    email.value,
                    normalizedDate,
                    gender.value.toInt()
                )
            ).collect { token ->
                token.onSuccess {
                    answer = it.token
                    success = 1

                }.onFailure {
                    answer = it.message.toString()
                }
            }

        } else {
            answer = resultMessage
        }

        return Pair(success, answer)
    }
}