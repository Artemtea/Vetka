package com.example.vetka.ui.components

import com.example.vetka.R
import com.example.vetka.navigation.CAMERA_ROUTE
import com.example.vetka.navigation.GROUPS_ROUTE
import com.example.vetka.navigation.PROFILE_ROUTE

data class BottomBarItem(
    val title: String,
    val icon: Int,
    val route: String
)

val bottomBarItems = listOf(
    BottomBarItem(
        title = "Ветки",
        icon = R.drawable.ic_house,
        route = GROUPS_ROUTE
    ),
    BottomBarItem(
        title = "Камера",
        icon = R.drawable.ic_circle_plus,
        route = CAMERA_ROUTE
    ),
    BottomBarItem(
        title = "Профиль",
        icon = R.drawable.ic_user_round,
        route = PROFILE_ROUTE
    )
)