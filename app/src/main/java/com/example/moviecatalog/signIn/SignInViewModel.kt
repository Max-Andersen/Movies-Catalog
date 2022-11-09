package com.example.moviecatalog.signIn

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.network.Auth.LoginRequestBody
import com.example.moviecatalog.repository.AuthRepository

class SignInViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    suspend fun signInButtonPressed(
        login: MutableState<String>,
        password: MutableState<String>
    ): Pair<Int, String> {

        var success = 0
        var answer = ""

        authRepository.login(
            LoginRequestBody(
                username = login.value,
                password = password.value
            )
        ).collect { token ->
            token.onSuccess {
                answer = it.token
                success = 1

            }.onFailure {
                answer = it.message.toString()
            }
        }

        println("RESULTTT  $success   $answer")
        return Pair(success, answer)
    }
}
