package com.example.moviecatalog.mainScreen.navBarItems


import com.example.moviecatalog.R

data class BarItem(
    val title: String,
    val image: Int,
    val imageSelected: Int,
    val route: String
)

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Главное",
            image = R.drawable.main,
            imageSelected = R.drawable.main_selected,
            route = "main"
        ),
        BarItem(
            title = "Профиль",
            image = R.drawable.profile,
            imageSelected = R.drawable.profile_selected,
            route = "profile"
        ),
    )
}