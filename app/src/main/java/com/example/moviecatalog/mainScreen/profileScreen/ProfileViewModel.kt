package com.example.moviecatalog.mainScreen.profileScreen

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.repository.AccountRepository

class ProfileViewModel(): ViewModel() {
    private val accountRepository = AccountRepository()

    fun ChangeGerder(currentGender: MutableState<String>, buttonType: String){ // 0 - male   1 - female
        if (currentGender.value != buttonType){
            currentGender.value = buttonType
        }
    }
}