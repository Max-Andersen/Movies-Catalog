package com.example.moviecatalog.signIn

import androidx.lifecycle.ViewModel
import com.example.moviecatalog.network.Auth.AuthResponse
import com.example.moviecatalog.network.Auth.LoginRequestBody
import com.example.moviecatalog.repository.AuthRepository

class SignInViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    suspend fun signInButtonPressed(screenState: SignInScreenState): AuthResponse {
        return authRepository.login(
            LoginRequestBody(
                username = screenState.login,
                password = screenState.password
            )
        )
    }
}
