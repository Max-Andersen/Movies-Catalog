package com.example.moviecatalog.signUp

import androidx.compose.runtime.MutableState
import com.example.moviecatalog.network.User.Gender

data class SignUpScreenState(
    val login: String = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val dateOfBirthday: String = "",
    val gender: Gender = Gender.MALE
)
