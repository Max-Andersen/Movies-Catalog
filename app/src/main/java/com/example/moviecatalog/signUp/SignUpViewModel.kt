package com.example.moviecatalog.signUp

import android.text.TextUtils
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.mainScreen.profileScreen.UserData
import com.example.moviecatalog.network.Auth.RegisterRequestBody
import com.example.moviecatalog.network.User.Gender
import com.example.moviecatalog.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar


class SignUpViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _userData = MutableStateFlow(SignUpScreenState())

    val userData: StateFlow<SignUpScreenState> = _userData

    private fun normalizeDate(date: String): String {
        var result = ""
        result += date.slice(6..9) + "-"
        result += date.slice(3..4) + "-"
        result += date.slice(0..1)
        result += "T18:14:23.985Z"
        return result
    }

    suspend fun register(screenState: SignUpScreenState): Pair<Int, String> {  // the fields are not empty, because then the button would be inactive
        with(screenState){
            val emailCorrect =
                !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            var resultMessage = ""

            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH) + 1
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)


            val enteredDay = dateOfBirthday.substringBefore('.').toInt()
            val enteredMonth = dateOfBirthday.substringAfter('.').substringBefore(".").toInt()
            val enteredYear = dateOfBirthday.substringAfterLast('.').toInt()


            if (!emailCorrect) {
                resultMessage += "\nНеверная почта!"
            }

            if (password != passwordConfirmation) {
                resultMessage += "\nПароли не совпадают!"
            }

            if ((enteredYear > currentYear) ||
                (enteredYear == currentYear && enteredMonth > currentMonth) ||
                (enteredYear == currentYear && enteredMonth == currentMonth && enteredDay >= currentDay)
            ) {
                resultMessage += "\nДата рождения должна быть меньше текущего дня!"
            }

            if (password.length < 6) {
                resultMessage += "\nПароль доджен быть не меньше 6 символов"
            }

            var success = 0
            var answer = ""

            val normalizedDate = normalizeDate(dateOfBirthday)

            if (resultMessage.isEmpty()) {
                authRepository.register(
                    RegisterRequestBody(
                        login,
                        name,
                        password,
                        email,
                        normalizedDate,
                        gender.serverId
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
}