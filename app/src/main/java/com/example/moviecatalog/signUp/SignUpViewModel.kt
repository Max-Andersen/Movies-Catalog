package com.example.moviecatalog.signUp

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.network.Auth.AuthResponse
import com.example.moviecatalog.network.Auth.RegisterRequestBody
import com.example.moviecatalog.normalizeDate
import com.example.moviecatalog.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar


class SignUpViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _userData = MutableStateFlow(SignUpScreenState())

    val userData: StateFlow<SignUpScreenState> = _userData

    suspend fun register(screenState: SignUpScreenState): AuthResponse {  // the fields are not empty, because then the button would be inactive
        with(screenState) {
            val emailCorrect =
                !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            var validationMessage = ""

            if (!emailCorrect) {
                validationMessage += "\nНеверная почта!"
            }

            if (password != passwordConfirmation) {
                validationMessage += "\nПароли не совпадают!"
            }

            if (isEnteredDateBeforeCurrent(dateOfBirthday)) {
                validationMessage += "\nДата рождения должна быть меньше текущего дня!"
            }

            if (password.length < 6) {
                validationMessage += "\nПароль доджен быть не меньше 6 символов"
            }

            val normalizedDate = dateOfBirthday.normalizeDate()

            return if (validationMessage.isEmpty()) {
                authRepository.register(
                    RegisterRequestBody(
                        login,
                        name,
                        password,
                        email,
                        normalizedDate,
                        gender.serverId
                    )
                )
            } else {
                AuthResponse.Fail(validationMessage)
            }
        }

    }

    private fun isEnteredDateBeforeCurrent(dateOfBirthday: String): Boolean {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)


        val enteredDay = dateOfBirthday.substringBefore('.').toInt()
        val enteredMonth = dateOfBirthday.substringAfter('.').substringBefore(".").toInt()
        val enteredYear = dateOfBirthday.substringAfterLast('.').toInt()

        return (enteredYear > currentYear) ||
                (enteredYear == currentYear && enteredMonth > currentMonth) ||
                (enteredYear == currentYear && enteredMonth == currentMonth && enteredDay >= currentDay)

    }
}