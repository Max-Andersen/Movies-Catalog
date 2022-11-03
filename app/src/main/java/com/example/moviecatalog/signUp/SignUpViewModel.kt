package com.example.moviecatalog.signUp

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.moviecatalog.repository.AccountRepository
import java.util.*


class SignUpViewModel : ViewModel() {
    private val accountRepository = AccountRepository()

    fun ChangeGerder(currentGender: MutableState<String>, buttonType: String){ // 0 - male   1 - female
        if (currentGender.value != buttonType){
            currentGender.value = buttonType
        }
    }

    fun Register(login: MutableState<String>,
                 email: MutableState<String>,
                 name: MutableState<String>,
                 password: MutableState<String>,
                 passwordConfirmation: MutableState<String>,
                 dateOfBirthday: MutableState<String>,
                 gender: MutableState<String>
                 , context: Context): String {  // the fields are not empty, because then the button would be inactive

        val emailCorrect = !TextUtils.isEmpty(email.value) && Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
        var resultMessage = ""

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)


        val enteredDay = dateOfBirthday.value.substringBefore('.').toInt()
        val enteredMonth = dateOfBirthday.value.substringAfter('.').substringBefore(".").toInt()
        val enteredYear = dateOfBirthday.value.substringAfterLast('.').toInt()


        if (!emailCorrect){  //email incorrect
            resultMessage += "\nНеверная почта!"
        }

        if (password.value != passwordConfirmation.value){   //password incorrect
            resultMessage += "\nПароли не совпадают!"
        }

        if (!(enteredYear <= currentYear && enteredMonth <= currentMonth && enteredDay < currentDay)){
            resultMessage += "\nДата рождения должна быть меньше текущего дня!" // date of birthday incorrect
        }

        return if (resultMessage.isEmpty()){
            accountRepository.register(login.value, name.value, password.value, email.value, dateOfBirthday.value, gender.value)
            //""  //token
        } else{
            Toast.makeText(context, "Ответ от валидирующих котиков:$resultMessage", Toast.LENGTH_LONG).show()
            ""
        }
    }
}