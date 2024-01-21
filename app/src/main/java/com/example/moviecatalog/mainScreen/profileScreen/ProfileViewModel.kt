package com.example.moviecatalog.mainScreen.profileScreen

import UserDataResponse
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.network.Network
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

    private fun normalizeDate(date: String): String {
        var result = ""
        result += date.slice(6..9) + "-"
        result += date.slice(3..4) + "-"
        result += date.slice(0..1)
        result += "T18:14:23.985Z"
        return result
    }

    private fun unNormalizeDate(date: String): String {
        var result = ""
        result += date.slice(8..9) + "."
        result += date.slice(5..6) + "."
        result += date.slice(0..3)
        return result
    }

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


            if ((enteredYear > currentYear) ||
                (enteredYear == currentYear && enteredMonth > currentMonth) ||
                (enteredYear == currentYear && enteredMonth == currentMonth && enteredDay >= currentDay)
            ) {
                resultMessage += "\nДата рождения должна быть меньше текущего дня!"
            }

            val normalizedDate = normalizeDate(dateOfBirthday)

            var answer = ""

            if (resultMessage.isEmpty()) {
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
                answer = resultMessage
            }
            return answer
        }

    }

    fun logout() {
        authRepository.logout()
    }
}