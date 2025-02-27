package com.xavier_carpentier.realestatemanager.ui.compose.createAndModified

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.ui.compose.listProperty.loadImageAsByteArray
import com.xavier_carpentier.realestatemanager.ui.compose.spinner.Spinner
import com.xavier_carpentier.realestatemanager.ui.compose.utils.PhotoWithDescription
import com.xavier_carpentier.realestatemanager.ui.create.CreatePropertyViewModel
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyUi
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun CreateAndModifiedScreen(
    create : Boolean,
    onBackNavigation: () -> Unit,
    viewModel: CreatePropertyViewModel = hiltViewModel()
){

    LaunchedEffect(create) {
        viewModel.initialised(create)
    }
    val propertyState by viewModel.property.collectAsState()
    val property by remember { derivedStateOf { propertyState } }
    val picturesState = viewModel.pictures.collectAsState()
    val pictures by remember { derivedStateOf { picturesState.value } }
    val createMode by viewModel.createMode.collectAsState()

    LaunchedEffect(property) {
        println("DEBUG: Propriété chargée - ${property}")
    }

    CreateAndModifiedContent(
        create = createMode,
        property = property,
        pictures = pictures,
        agentList = viewModel.getAgentList(),
        typeList = viewModel.getTypeList(),
        onPropertyChange = { propertyUi -> viewModel.createOrModifyProperty(propertyUi) },
        onAddPicture = { viewModel.addPicture(it) },
        onDeletePicture = { viewModel.deletePicture(it) }
    )

    
    val propertyCreated by viewModel.propertyCreated.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(propertyCreated) {
        propertyCreated?.let { isSuccess ->
            coroutineScope.launch {
                if (isSuccess) {
                    snackbarHostState.showSnackbar("Propriété créée avec succès !")
                    onBackNavigation()
                } else {
                    snackbarHostState.showSnackbar("Échec de la création de la propriété.")
                }
                viewModel.clearPropertyCreatedState()
            }
        }
    }
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun CreateAndModifiedContent(
    create : Boolean,
    property: PropertyUi,
    pictures: List<PictureUi>,
    agentList: List<Pair<Int, String>>,
    typeList: List<Triple<Int, String, Int>>,
    onPropertyChange: (PropertyUi) -> Unit,
    onAddPicture: (PictureUi) -> Unit,
    onDeletePicture: (PictureUi) -> Unit
) {
    var selectedAgent by remember(property) { mutableStateOf(agentList.first { it.second == property.agent }) }
    var selectedType by remember(property) { mutableStateOf(typeList.first { it.second == property.type }) }
    var price by remember(property) { mutableStateOf(TextFieldValue(property.price.toString())) }
    var address by remember(property) { mutableStateOf(TextFieldValue(property.address)) }
    var latitude by remember(property) { mutableDoubleStateOf(property.latitude) }
    var longitude by remember(property) { mutableDoubleStateOf(property.longitude) }
    var surface by remember(property) { mutableStateOf(TextFieldValue(property.surface.toString())) }
    var room by remember(property) { mutableStateOf(TextFieldValue(property.room.toString())) }
    var bedroom by remember(property) { mutableStateOf(TextFieldValue(property.bedroom.toString())) }
    var description by remember(property) { mutableStateOf(TextFieldValue(property.description)) }
    var sold by remember(property) { mutableStateOf(property.sold) }

    // Variables for the interest
    var interestNearbySchool by remember(property) { mutableStateOf(property.interestNearbySchool) }
    var interestNearbyShop by remember(property) { mutableStateOf(property.interestNearbyShop) }
    var interestNearbyPark by remember(property) { mutableStateOf(property.interestNearbyPark) }
    var interestNearbyRestaurant by remember(property) { mutableStateOf(property.interestNearbyRestaurant) }
    var interestNearbyPublicTransportation by remember(property) { mutableStateOf(property.interestNearbyPublicTransportation) }
    var interestNearbyPharmacy by remember(property) { mutableStateOf(property.interestNearbyPharmacy) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if (create){
            Text("Créer Propriété", style = MaterialTheme.typography.titleLarge)
        }else{
            Text("Éditer Propriété", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Spinner(
            nameList = "Type de propriété",
            list = typeList,
            preselected = selectedType,
            onSelectionChanged = { selectedType = it }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Prix") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Adresse") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            OutlinedTextField(
                value = latitude.toString(),
                onValueChange = { latitude=it.toDouble() },
                label = { Text("Latitude") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = longitude.toString(),
                onValueChange = { longitude= it.toDouble()},
                label = { Text("Longitude") },
                modifier = Modifier.weight(1f)
            )

        }

        OutlinedTextField(
            value = surface,
            onValueChange = { surface = it },
            label = { Text("Surface (m²)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = room,
            onValueChange = { room = it },
            label = { Text("Nombre de pièces") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = bedroom,
            onValueChange = { bedroom = it },
            label = { Text("Nombre de chambres") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spinner(
            nameList = "Agent immobilier",
            list = agentList,
            preselected = selectedAgent,
            onSelectionChanged = { selectedAgent = it },
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = sold, onCheckedChange = { sold = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("Vendu")
        }

        Text("Centres d'intérêt")

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = interestNearbySchool, onCheckedChange = { interestNearbySchool = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("École")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = interestNearbyShop, onCheckedChange = { interestNearbyShop = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("Magasin")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = interestNearbyPark, onCheckedChange = { interestNearbyPark = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("Parc")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = interestNearbyRestaurant, onCheckedChange = { interestNearbyRestaurant = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("Restaurant")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = interestNearbyPublicTransportation, onCheckedChange = { interestNearbyPublicTransportation = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("Transport public")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = interestNearbyPharmacy, onCheckedChange = { interestNearbyPharmacy = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text("Pharmacie")
        }
        

        Text("Photos", modifier = Modifier.padding(top= 16.dp, bottom = 16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
        ) {
            println("DEBUG: LazyRow contient ${pictures.size} photos") // Log pour vérifier le nombre d'éléments


            items(pictures) { picture ->
                Box(modifier= Modifier
                    .width(100.dp)
                    .height(166.dp)
                ){
                    println("DEBUG: Affichage de la photo avec description: ${picture.description}") // Vérifier que chaque photo passe bien ici
                    PhotoWithDescription(picture = picture)
                    IconButton(
                        onClick = { onDeletePicture(picture)},
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Supprimer")
                    }

                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            CapturePhotoWithDescriptionButton(property.id, onAddPicture)

            AddPhotoGaleryWithDescriptionButton(property.id, onAddPicture)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (pictures.isEmpty()) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Veuillez ajouter au moins une photo")
                }
            } else {
                val propertyUi = PropertyUi(
                    type = selectedType.second,
                    price = price.text.toLongOrNull() ?: 0,
                    address = address.text,
                    surface = surface.text.toLongOrNull() ?: 0,
                    room = room.text.toIntOrNull() ?: 0,
                    bedroom = bedroom.text.toIntOrNull() ?: 0,
                    description = description.text,
                    agent = selectedAgent.second,
                    sold = sold,
                    interestNearbySchool = interestNearbySchool,
                    interestNearbyShop = interestNearbyShop,
                    interestNearbyPark = interestNearbyPark,
                    interestNearbyRestaurant = interestNearbyRestaurant,
                    interestNearbyPublicTransportation = interestNearbyPublicTransportation,
                    interestNearbyPharmacy = interestNearbyPharmacy,
                    latitude = latitude,
                    longitude = longitude,
                    entryDate = property.entryDate,
                    soldDate = if (property.sold && property.soldDate == null) Calendar.getInstance() else property.soldDate
                )
                onPropertyChange(propertyUi)
            }
        }) {
            Text("Sauvegarder")
        }
    }
}


@Preview(showBackground = true, heightDp = 1250)
@Composable
fun CreateAndModifiedScreenPreview() {

    val context = LocalContext.current
    val fakeImage = loadImageAsByteArray(context, R.drawable.image_property_picture)
    val agentList = listOf(
        Pair(1, "Jean Dupont"),
        Pair(2, "Marie Curie"),
        Pair(3, "Albert Einstein")
    )

    val typeList = listOf(
        Triple(1, "House", R.string.house),
        Triple(2, "lama", R.string.flat),
        Triple(3, "villa", R.string.villa)
    )

    CreateAndModifiedContent(
        create = false,
        property = PropertyUi(
            id = 1,
            type = "House",
            price = 250000,
            address = "123 Rue Imaginaire",
            latitude = 0.0,
            longitude = 0.0,
            surface = 120,
            room = 5,
            bedroom = 3,
            description = "Une belle maison.",
            entryDate = Calendar.getInstance(),
            soldDate = null,
            sold = false,
            agent = "Jean Dupont",
            interestNearbySchool = true,
            interestNearbyShop = false,
            interestNearbyPark = true,
            interestNearbyRestaurant = false,
            interestNearbyPublicTransportation = true,
            interestNearbyPharmacy = true
        ),
        pictures = listOf(
            PictureUi(
                id = 1,
                propertyId = 1,
                description = "Salon",
                image = fakeImage),
            PictureUi(
                id = 2,
                propertyId = 1,
                description = "Chambre",
                image = fakeImage),
            PictureUi(
                id = 3,
                propertyId = 1,
                description = "Salle de bain",
                image = fakeImage)
        ),
        agentList = agentList,
        typeList = typeList,
        onPropertyChange = {},
        onAddPicture = {},
        onDeletePicture = {}
    )
}
