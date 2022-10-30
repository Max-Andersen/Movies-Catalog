package com.example.moviecatalog.mainScreen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.mainScreen.navBarItems.NavBarItems
import com.example.moviecatalog.mainScreen.profileScreen.ProfileScreen


@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun MainScreenController(externalNavController: NavController) {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        NavBarItems.BarItems.forEach { barItem ->
                            BottomNavigationItem(
                                selected = currentRoute == barItem.route,
                                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
                                onClick = {
                                    navController.navigate(barItem.route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                },
                                label = {
                                    Text(
                                        barItem.title,
                                        color = if (currentRoute == barItem.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                    )
                                },
                                icon = {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = barItem.image),
                                        contentDescription = barItem.title,
                                        tint = if (currentRoute == barItem.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                    )
                                })
                        }

                    }
                }
            ) {
                Text(text = "", modifier = Modifier.padding(it))

                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(externalNavController) }
                    composable("profile") { ProfileScreen() }
                }
            }
        }
    }


}