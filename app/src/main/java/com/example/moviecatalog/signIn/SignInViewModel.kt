package com.example.moviecatalog.signIn

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.repository.AccountRepository

class SignInViewModel : ViewModel() {
    val accountRepository = AccountRepository()

    fun signInButtonPressed(login: MutableState<String>, password: MutableState<String>){
        println(accountRepository.Login(login.value, password.value)) // check for token or error message
    }
}