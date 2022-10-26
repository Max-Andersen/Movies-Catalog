package com.example.moviecatalog.mainScreen.navBarItems


import com.example.moviecatalog.R

data class BarItem(
    val title: String,
    val image: Int,
    val route: String
)

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Главное",
            image = R.drawable.main,
            route = "main"
        ),
        BarItem(
            title = "Профиль",
            image = R.drawable.profile,
            route = "profile"
        ),
    )
}