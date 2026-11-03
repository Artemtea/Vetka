package com.example.vetka.ui.camera

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun TakePhoto(){
    val context = LocalContext.current

    var pendingPhotoUri by remember {
        mutableStateOf<Uri?>(null)
    }
}