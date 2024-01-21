package com.example.moviecatalog.mainScreen.profileScreen

import UserDataResponse
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.network.Network
import com.example.moviecatalog.normalizeDate
import com.example.moviecatalog.repository.AuthRepository
import com.example.moviecatalog.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import toUiModel
import java.util.Calendar

class ProfileViewModel() : ViewModel() {
    private val authRepository = AuthRepository()
    private val userRepository = UserRepository()

    private val _userData = MutableStateFlow(UserData())

    val userData: StateFlow<UserData> = _userData

    suspend fun getInformation() {
        userRepository.getData().collect {
            it.onSuccess { data ->
                _userData.value = data.toUiModel()
            }
        }
    }

    suspend fun putInformation(
        userData: UserData
    ): String {
        with(userData){
            val emailCorrect =
                !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches()

            var validationMessage = ""

            if (!emailCorrect) {
                validationMessage += "\nНеверная почта!"
            }

            if (isEnteredDateBeforeCurrent(dateOfBirthday)) {
                validationMessage += "\nДата рождения должна быть меньше текущего дня!"
            }

            val normalizedDate = dateOfBirthday.normalizeDate()

            var answer = ""

            if (validationMessage.isEmpty()) {
                userRepository.putData(
                    UserDataResponse(
                        id = Network.userId,
                        nickName = nickName,
                        email = email,
                        avatarLink = avatarLink,
                        name = name,
                        birthDate = normalizedDate,
                        gender = gender.serverId
                    )
                )
            } else {
                answer = validationMessage
            }
            return answer
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

    fun logout() {
        authRepository.logout()
    }
}