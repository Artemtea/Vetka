package com.example.vetka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import java.io.File
import java.util.UUID
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}

private const val GROUPS_ROUTE = "groups"
private const val CAMERA_ROUTE = "camera"
private const val PROFILE_ROUTE = "profile"

@Composable
fun VetkaBottomBar(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 15.dp, vertical = 8.dp)
            .height(112.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(percent = 50),
            color = Color.White
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomBarItems.forEach { item ->
                    if (item.route == CAMERA_ROUTE) {
                        Spacer(
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .height(96.dp)
                                .clickable {
                                    navController.navigate(item.route) {
                                        launchSingleTop = true
                                    }
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = null,
                                tint = Color(0xFFFF7080),
                                modifier = Modifier.size(30.dp)
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = item.title,
                                color = Color(0xFFFF7080)
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(96.dp)
                .clip(CircleShape)
                .background(Color(0xFFFF7080))
                .clickable {
                    navController.navigate(CAMERA_ROUTE) {
                        launchSingleTop = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_circle_plus),
                contentDescription = "Сделать фото",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    Scaffold(
        containerColor = Color(0xFFFFABAE),
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
                GroupsScreen()
            }
            composable(route = CAMERA_ROUTE) {
                CameraPractice()
            }
            composable(route = PROFILE_ROUTE) {
                ProfileScreen()
            }
        }
    }

}

@Composable
fun GroupsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Группы")
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Профиль")
    }
}

@Composable
fun CameraPractice() {
    val context = LocalContext.current

    val photos = remember {
        mutableStateListOf<Uri>()
    }

    var pendingPhotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            pendingPhotoUri?.let { uri ->
                photos.add(uri)
            }
        }
        pendingPhotoUri = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                val directory = File(
                    context.filesDir,
                    "photos"
                ).apply { mkdirs() }

                val file = File(
                    directory,
                    "photo_${UUID.randomUUID()}.jpg"
                )

                file.createNewFile()

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )

                pendingPhotoUri = uri
                cameraLauncher.launch(uri)
            }
        ) {
            Text("Сделать фото")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(photos) { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Фотография",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

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