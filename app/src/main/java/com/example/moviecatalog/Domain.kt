package com.example.moviecatalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.mainScreen.profileScreen.ProfileViewModel
import com.example.moviecatalog.signUp.SignUpViewModel

@Composable
fun getTextInputColorTheme(): TextFieldColors{
    return TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colorScheme.primary,
        cursorColor = MaterialTheme.colorScheme.secondary,
        focusedBorderColor = MaterialTheme.colorScheme.secondary,
        unfocusedBorderColor = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun SetOutlinedTextField(variable: MutableState<String>, name: String){
    var passwordVisible by remember { mutableStateOf(false) }
    val textInputColorTheme = getTextInputColorTheme()

    if(name in setOf("Пароль", "Подтвердите пароль") ){
        OutlinedTextField(
            value = variable.value,
            onValueChange = {newText -> variable.value = newText} ,
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = name, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodySmall) },
            colors = textInputColorTheme,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (!passwordVisible) PasswordVisualTransformation('*') else VisualTransformation.None,
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description, tint = MaterialTheme.colorScheme.secondary)
                }
            }
        )
    }
    else{
        OutlinedTextField(
            value = variable.value,
            onValueChange = {newText -> variable.value = newText} ,
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = name, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodySmall) },
            colors = textInputColorTheme,
        )
    }


}

fun isAllTextFieldsFull(vararg strings: MutableState<String>): Boolean {
    return strings.map{ if(it.value.isNotBlank()) 1 else 0 }.sum() == strings.size
}

fun LazyListLayoutInfo.normalizedItemPosition(key: Any): Float =
    visibleItemsInfo
        .firstOrNull { it.key == key }
        ?.let {
            val center = (viewportEndOffset + viewportStartOffset - it.size * 2.5f) / 2F
            (it.offset.toFloat() - center) / center
        }
        ?: 0F

@Composable
fun ChoseGender(model: SignUpViewModel, gender: MutableState<String>) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(1.dp))
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
            .height(47.dp)
    )
    {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {

            OutlinedButton(
                onClick = { model.ChangeGerder(gender, "0") },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (gender.value == "0") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.background,
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    bottomStart = 8.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            {
                Text(
                    text = "Мужчина",
                    color = if (gender.value == "0") MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedButton(
                onClick = { model.ChangeGerder(gender, "1") },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (gender.value == "1") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.background,
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    bottomStart = 0.dp,
                    topEnd = 8.dp,
                    bottomEnd = 8.dp
                )
            ) {
                Text(
                    text = "Женщина",
                    color = if (gender.value == "1") MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun ChoseGender(model: ProfileViewModel, gender: MutableState<String>) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(1.dp))
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
            .height(47.dp)
    )
    {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {

            OutlinedButton(
                onClick = { model.ChangeGerder(gender, "0") },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (gender.value == "0") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.background,
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    bottomStart = 8.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            {
                Text(
                    text = "Мужчина",
                    color = if (gender.value == "0") MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedButton(
                onClick = { model.ChangeGerder(gender, "1") },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (gender.value == "1") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.background,
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    bottomStart = 0.dp,
                    topEnd = 8.dp,
                    bottomEnd = 8.dp
                )
            ) {
                Text(
                    text = "Женщина",
                    color = if (gender.value == "1") MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}