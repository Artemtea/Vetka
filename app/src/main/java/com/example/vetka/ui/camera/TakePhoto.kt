package com.example.vetka.ui.camera

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun rememberTakePhotoLauncher(
    onPhotoTaken: (Uri) -> Unit
): () -> Unit {
    val context = LocalContext.current

    var pendingPhotoUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        val uri = pendingPhotoUri
        pendingPhotoUri = null

        if (uri != null) {
            if (success) {
                onPhotoTaken(uri)
            } else {
                context.contentResolver.delete(uri, null, null)
            }
        }
    }

    return {
        if (pendingPhotoUri == null) {
            var photoFile: File? = null

            try {
                val directory = File(context.filesDir, "photos")
                check(directory.isDirectory || directory.mkdirs())

                val file = File.createTempFile(
                    "photo_",
                    ".jpg",
                    directory
                )
                photoFile = file

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )

                pendingPhotoUri = uri
                cameraLauncher.launch(uri)
            } catch (_: Exception) {
                pendingPhotoUri = null
                photoFile?.delete()

                Toast.makeText(
                    context,
                    "Не удалось открыть камеру",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}