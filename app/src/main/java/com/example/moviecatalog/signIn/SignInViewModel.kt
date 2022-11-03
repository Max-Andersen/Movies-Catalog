package com.example.moviecatalog.signIn

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.network.Auth.LoginRequestBody
import com.example.moviecatalog.repository.AccountRepository
import kotlinx.coroutines.*

class SignInViewModel : ViewModel() {
    private val accountRepository = AccountRepository()

    suspend fun signInButtonPressed(
        login: MutableState<String>,
        password: MutableState<String>
    ): Pair<Int, String> {

        var success = 0
        var answer = ""

        accountRepository.loggin(
            LoginRequestBody(
                username = login.value,
                password = password.value
            )
        ).collect { token ->
            token.onSuccess {
                answer = it.token
                success = 1
                Log.d("ВСЁ КРУТО!!!!!!!!!!!", answer)

            }.onFailure {
                answer = it.message.toString()
                Log.d("/////////", it.message.toString())
            }
        }

        println("RESULTTT  $success   $answer")
        return Pair(success, answer)
    }
}
