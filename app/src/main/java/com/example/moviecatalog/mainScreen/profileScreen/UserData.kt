package com.example.moviecatalog.mainScreen.profileScreen

import com.example.moviecatalog.network.User.Gender

data class UserData(
    val email: String = "",
    val avatarLink: String = "",
    val name: String = "",
    val nickName: String = "",
    val dateOfBirthday: String = "",
    val gender: Gender = Gender.MALE,
    val currentName: String = "",
)
