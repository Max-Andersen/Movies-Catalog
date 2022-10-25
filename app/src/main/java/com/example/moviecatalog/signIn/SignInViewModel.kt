package com.example.moviecatalog.signIn

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.repository.AccountRepository

class SignInViewModel : ViewModel() {
    private val accountRepository = AccountRepository()

    fun signInButtonPressed(login: MutableState<String>, password: MutableState<String>): String{
       return accountRepository.Login(login.value, password.value) // check for token or error message
    }
}