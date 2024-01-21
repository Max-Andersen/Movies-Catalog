package com.example.moviecatalog.signIn

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.mainScreen.profileScreen.UserData
import com.example.moviecatalog.network.Auth.LoginRequestBody
import com.example.moviecatalog.repository.AuthRepository
import com.example.moviecatalog.signUp.SignUpScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    suspend fun signInButtonPressed(screenState: SignInScreenState): Pair<Int, String> {
        var success = 0
        var answer = ""

        authRepository.login(
            LoginRequestBody(
                username = screenState.login,
                password = screenState.password
            )
        ).collect { token ->
            token.onSuccess {
                answer = it.token
                success = 1

            }.onFailure {
                answer = it.message.toString()
            }
        }

        println("RESULT  $success   $answer")
        return Pair(success, answer)
    }
}
