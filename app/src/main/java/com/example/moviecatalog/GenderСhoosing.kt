package com.example.moviecatalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.mainScreen.profileScreen.ProfileViewModel
import com.example.moviecatalog.network.User.Gender
import com.example.moviecatalog.signUp.SignUpViewModel

@Composable
fun ChoiceGender(gender: Gender, onGenderChanged: (Gender) -> Unit) {
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
                onClick = { onGenderChanged(Gender.MALE) },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (gender == Gender.MALE) MaterialTheme.colorScheme.primary
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
                    text = stringResource(id = R.string.male),
                    color = if (gender == Gender.MALE) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedButton(
                onClick = { onGenderChanged(Gender.FEMALE) },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (gender == Gender.FEMALE) MaterialTheme.colorScheme.primary
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
                    text = stringResource(id = R.string.female),
                    color = if (gender == Gender.FEMALE) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}