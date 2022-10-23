package com.example.moviecatalog.signUp

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    fun ChangeGerder(currentGender: MutableState<String>, buttonType: String){ // 0 - male   1 - female
        if (currentGender.value != buttonType){
            currentGender.value = buttonType
        }
    }
}