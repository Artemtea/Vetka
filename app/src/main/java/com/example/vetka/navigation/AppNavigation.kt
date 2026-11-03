package com.example.vetka.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vetka.ui.camera.CameraScreen
import com.example.vetka.ui.components.VetkaBottomBar
import com.example.vetka.ui.groups.GroupScreen
import com.example.vetka.ui.groups.GroupsScreen
import com.example.vetka.ui.profile.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val currentEntry by navController.currentBackStackEntryAsState()
    val isGroupScreen =
        currentEntry?.destination?.route == "group/{groupId}"

    val backgroundColor =
        if (isGroupScreen) Color.White else Color(0xFFFFABAE)

    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            VetkaBottomBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = GROUPS_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = GROUPS_ROUTE) {
                GroupsScreen(
                    onGroupClick = { groupId ->
                        navController.navigate("group/$groupId")
                    }
                )
            }

            composable(route = "group/{groupId}") { backStackEntry ->
                val groupId = backStackEntry.arguments?.getString("groupId")

                if (groupId != null) {
                    GroupScreen(
                        groupId = groupId,
                        navController = navController
                    )
                }
            }

            composable(route = CAMERA_ROUTE) {
                CameraScreen()
            }

            composable(route = PROFILE_ROUTE) {
                ProfileScreen()
            }
        }
    }
}