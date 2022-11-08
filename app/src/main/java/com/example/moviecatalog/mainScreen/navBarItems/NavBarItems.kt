package com.example.moviecatalog.mainScreen.navBarItems


import com.example.moviecatalog.R

data class BarItem(
    val title: Int,
    val image: Int,
    val route: String
)

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = R.string.main,
            image = R.drawable.main,
            route = "main"
        ),
        BarItem(
            title = R.string.profile,
            image = R.drawable.profile,
            route = "profile"
        ),
    )
}