package com.example.moviecatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviecatalog.signIn.SignInScreen
import com.example.moviecatalog.signUp.SignUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                SignUpScreen()

        }
    }

}

@Preview
@Composable
fun Show(){
    //SignInScreen()
    //SignUpScreen()
}