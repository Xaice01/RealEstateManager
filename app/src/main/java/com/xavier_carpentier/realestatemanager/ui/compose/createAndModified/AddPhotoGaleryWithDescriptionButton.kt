package com.xavier_carpentier.realestatemanager.ui.compose.createAndModified

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi
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
            Text("Sélectionner une photo")
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
                        val imageBytes = inputStream?.readBytes() ?: ByteArray(0)

                        val newPicture = PictureUi(
                            id = 0,
                            propertyId = propertyId,
                            description = description.text,
                            image = imageBytes
                        )
                        //onAddPicture(newPicture)
                        imageUri = null
                        description = TextFieldValue("")
                        isExpanded = false


                        onAddPicture(newPicture) // On appelle l'ajout après la mise à jour de l'état

                    }
                }) {
                    Text("Ajouter la photo via la galery")
                }
            }
        }
    }
}