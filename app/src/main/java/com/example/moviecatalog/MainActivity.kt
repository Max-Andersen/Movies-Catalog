package com.example.moviecatalog

import android.content.res.Configuration
import android.media.Image
import android.os.Bundle
import android.os.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import com.example.moviecatalog.ui.theme.MyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme
                SingInScreen()

        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun show(){
    SingInScreen()
    //essageCard(messages = MakeMessages())
}

@Composable
fun SingInScreen(){
    MyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            start = 55.dp,
                            top = 32.dp,
                            end = 55.dp),


                ){
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Image(
                        painter = painterResource(id = R.drawable.moviecatalog),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.size(48.dp))
                Column(
                    modifier = Modifier.padding(
                        top = 48.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),

                ) {
                    var login by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }

                    val textInputColorTheme = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.secondary,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                    )

                    OutlinedTextField(
                        value = login,
                        onValueChange = {newText -> login = newText},
                        textStyle = MaterialTheme.typography.bodySmall,
                        singleLine = true,
                        modifier = Modifier.size(428.dp, 55.dp),
                        placeholder = { Text(text = "Логин", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodySmall) },
                        colors = textInputColorTheme
                    )
                    
                    Spacer(modifier = Modifier.size(14.dp))
                    
                    OutlinedTextField(
                        value = password,
                        onValueChange = {newPassword -> password = newPassword},
                        textStyle = MaterialTheme.typography.bodySmall,
                        singleLine = true,
                        modifier = Modifier.size(428.dp, 55.dp),
                        placeholder = { Text(text = "Пароль", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodySmall) },
                        colors = textInputColorTheme
                    )

                    Spacer(modifier = Modifier.size(164.dp))

                    fun isAllFieldsFull(): Boolean {
                        return !login.isBlank() && !password.isBlank()
                    }

                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        enabled = isAllFieldsFull(),
                        modifier = Modifier.size(428.dp, 55.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = if (isAllFieldsFull()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.background
                        ),
                        border = BorderStroke(1.dp, if (isAllFieldsFull()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondary)

                        ) {
                        Text(text = "Войти",
                            color = if (isAllFieldsFull()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                        )
                    }

                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(428.dp, 55.dp),
                        ) {
                        Text(text = "Регистрация", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}


