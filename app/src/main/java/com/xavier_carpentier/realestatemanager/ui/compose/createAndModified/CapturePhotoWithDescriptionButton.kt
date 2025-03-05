package com.xavier_carpentier.realestatemanager.ui.compose.createAndModified

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi
import java.io.ByteArrayOutputStream
import java.io.InputStream

@Composable
fun CapturePhotoWithDescriptionButton(
    propertyId: Int,
    onAddPicture: (PictureUi) -> Unit
) {
    val context = LocalContext.current
    val cameraPermission = android.Manifest.permission.CAMERA
    var hasCameraPermission by remember { mutableStateOf(
        ContextCompat.checkSelfPermission(context, cameraPermission) == PackageManager.PERMISSION_GRANTED
    ) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }

    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var description by remember { mutableStateOf(TextFieldValue("")) }


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoUri?.let { uri ->
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                capturedBitmap = BitmapFactory.decodeStream(inputStream)
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            if (!hasCameraPermission) {
                requestPermissionLauncher.launch(cameraPermission)
            } else {
                photoUri = createImageUri(context)
                photoUri?.let { cameraLauncher.launch(it) }
            }
        }) {
            Text("Prendre une photo")
        }

        capturedBitmap?.let { bitmap ->

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )

            Button(onClick = {
                val compressedBytes = compressBitmap(bitmap)

                val newPicture = PictureUi(
                    id = 0,
                    propertyId = propertyId,
                    description = description.text,
                    image = compressedBytes
                )
                onAddPicture(newPicture)
                capturedBitmap = null
                description = TextFieldValue("")
                photoUri = null
            }) {
                Text("Ajouter la photo via la caméra")
            }
        }
    }
}

fun createImageUri(context: Context): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}

private fun compressBitmap(bitmap: Bitmap, quality: Int = 50): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}