package com.xavier_carpentier.realestatemanager.ui.compose.createAndModified

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi
import java.io.ByteArrayOutputStream
import java.io.InputStream

@Composable
fun AddPhotoGaleryWithDescriptionButton(
    propertyId: Int,
    onAddPicture: (PictureUi) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var isExpanded by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        isExpanded = uri != null
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { galleryLauncher.launch("image/*") }) {
            Text(stringResource(R.string.Select_picture))
        }

        AnimatedVisibility(visible = isExpanded) {
            Column {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Button(onClick = {
                    imageUri?.let { uri ->
                        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)

                        val compressedBytes = compressBitmap(bitmap)
                        val newPicture = PictureUi(
                            id = 0,
                            propertyId = propertyId,
                            description = description.text,
                            image = compressedBytes
                        )
                        //onAddPicture(newPicture)
                        imageUri = null
                        description = TextFieldValue("")
                        isExpanded = false


                        onAddPicture(newPicture)

                    }
                }) {
                    Text(stringResource(R.string.Add_the_photo_via_the_gallery))
                }
            }
        }
    }
}

private fun compressBitmap(bitmap: Bitmap, quality: Int = 50): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}