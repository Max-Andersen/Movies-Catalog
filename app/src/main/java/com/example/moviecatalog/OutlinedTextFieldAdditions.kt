package com.example.moviecatalog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun getTextInputColorTheme(): TextFieldColors {
    return TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colorScheme.primary,
        cursorColor = MaterialTheme.colorScheme.secondary,
        focusedBorderColor = MaterialTheme.colorScheme.secondary,
        unfocusedBorderColor = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun SetOutlinedTextField(variable: MutableState<String>, name: String) {
    var passwordVisible by remember { mutableStateOf(false) }
    val textInputColorTheme = getTextInputColorTheme()

    if (name in setOf(
            stringResource(id = R.string.password),
            stringResource(id = R.string.passwordConfirmation)
        )
    ) {
        OutlinedTextField(
            value = variable.value,
            onValueChange = { newText -> variable.value = newText },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = name,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            colors = textInputColorTheme,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (!passwordVisible) PasswordVisualTransformation('*') else VisualTransformation.None,
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        description,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        )
    } else {
        OutlinedTextField(
            value = variable.value,
            onValueChange = { newText -> variable.value = newText },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = name,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            colors = textInputColorTheme,
        )
    }


}

fun isAllTextFieldsFull(vararg strings: MutableState<String>): Boolean {
    return strings.map { if (it.value.isNotBlank()) 1 else 0 }.sum() == strings.size
}