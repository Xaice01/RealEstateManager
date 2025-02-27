package com.xavier_carpentier.realestatemanager.ui.compose.filtre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.ui.compose.utils.LoadingScreen
import com.xavier_carpentier.realestatemanager.ui.filter.FilterUiState
import com.xavier_carpentier.realestatemanager.ui.filter.FilterViewModel
import com.xavier_carpentier.realestatemanager.ui.model.FilterType
import com.xavier_carpentier.realestatemanager.ui.model.FilterUi
import com.xavier_carpentier.realestatemanager.ui.model.PropertyTypeUiCheckable

@Composable
fun FilterScreen(viewModel :FilterViewModel  = hiltViewModel(),onFilterApplied: ()-> Unit)
{
    val filterUiState = viewModel.filterUiState.collectAsState()
    val filter by viewModel.filter.collectAsState()

    when(filterUiState.value){
        is FilterUiState.Loading -> LoadingScreen()
        else ->{
            FilterContent(
                filter = filter,
                listOfPropertyType = viewModel.listOfPropertyType,
                checkedStates = viewModel.checkedStates,
                onCheckedPropertyType = { propertyTypeUiCheckable, checked ->
                    viewModel.onCheckedPropertyType(propertyTypeUiCheckable, checked)
                },
                onChangeValue = { label, value -> viewModel.onChangeValue(label, value) },
                onFilterApplied = {
                    applied -> viewModel.onFilterApplied(applied)
                    onFilterApplied()
                }
            )

        }

    }

}

@Composable
fun FilterContent(
    filter: FilterUi,
    listOfPropertyType: List<PropertyTypeUiCheckable>,
    checkedStates: List<Triple<FilterType, Boolean?, Int>>,
    onCheckedPropertyType: (PropertyTypeUiCheckable, Boolean) -> Unit,
    onChangeValue: (label:FilterType,Any) -> Unit,
    onFilterApplied: (Boolean?)-> Unit,
    modifier :Modifier= Modifier.background(MaterialTheme.colorScheme.background)
) {
    Surface(
        modifier = modifier
    ){
        //var propertyTypeCheckable by remember {
        // mutableStateOf(listOfPropertyType.map { it -> PropertyTypeUiCheckable(it, false) })}
        Column(
            modifier = Modifier
                .fillMaxSize()

        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),

                ) {

                Text(
                    text = "Type of property",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    modifier = Modifier.height(100.dp)
                ) {
                    items(
                        items = listOfPropertyType,
                        key = { propertyTypeCheckable -> propertyTypeCheckable.propertyTypeUi.first }
                    ) { propertyTypeCheckable ->
                        FilterAttributeCheckboxItem(
                            label = stringResource(id = propertyTypeCheckable.propertyTypeUi.third),
                            checked = propertyTypeCheckable.checked,
                            onCheckedChange = { checked ->
                                onCheckedPropertyType(propertyTypeCheckable, checked)}
                        )

                    }
                }

                TwoOutlinedTextFieldInRow(
                    firstValue = filter.minPrice.toString(),
                    secondValue = filter.maxPrice.toString(),
                    onFirstValueChange = { onChangeValue(FilterType.MIN_PRICE, it) },
                    onSecondValueChange = { onChangeValue(FilterType.MAX_PRICE, it) },
                    firstLabel = "Min Price",
                    secondLabel = "Max Price"
                )

                TwoOutlinedTextFieldInRow(
                    firstValue = filter.minSurface.toString(),
                    secondValue = filter.maxSurface.toString(),
                    onFirstValueChange = { onChangeValue(FilterType.MIN_SURFACE, it) },
                    onSecondValueChange = { onChangeValue(FilterType.MAX_SURFACE, it) },
                    firstLabel = "Min Surface",
                    secondLabel = "Max Surface"
                )

                Text(
                    text = "Property attribute",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    modifier = Modifier.height(100.dp)
                ) {
                    items(
                        items = checkedStates,
                        key = { item -> item.first }
                    ) { item ->
                        FilterAttributeCheckboxItem(
                            label = stringResource(id = item.third),
                            checked = item.second ?: false,
                            onCheckedChange = {
                                onChangeValue(item.first, it)
                            }
                        )
                    }
                }
            }

            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(
                    onClick = {onFilterApplied(null)},
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(
                        "Cancel",
                        style= MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.wrapContentSize()
                    )
                }
                OutlinedButton(
                    onClick = {onFilterApplied(false)},
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(
                        "Reset",
                        style= MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Button(
                    onClick = {onFilterApplied(true)},
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(
                        "Applied",
                        style= MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
    
}

@Composable
fun FilterAttributeCheckboxItem(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier=Modifier
){
    Row(
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) })
        Text(label)
    }
}

@Composable
fun TwoOutlinedTextFieldInRow(
    firstValue: String,
    secondValue: String,
    onFirstValueChange: (String) -> Unit,
    onSecondValueChange: (String) -> Unit,
    firstLabel: String,
    secondLabel: String,
    modifier: Modifier=Modifier
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = firstValue,
            onValueChange = { onFirstValueChange(it) },
            label = { Text(firstLabel) },
            modifier = modifier.weight(1f)
        )
        OutlinedTextField(
            value = secondValue,
            onValueChange = { onSecondValueChange(it) },
            label = { Text(secondLabel) },
            modifier = Modifier.weight(1f)
        )
    }
}


@Preview
@Composable
fun FilterContentPreview() {
    val PropertyTypeUiCheckable = listOf(
        PropertyTypeUiCheckable(Triple(0, "Maison", R.string.house), false),
        PropertyTypeUiCheckable(Triple(1, "Flat", R.string.flat), false),
        PropertyTypeUiCheckable(Triple(2, "Villa", R.string.villa), false),
        PropertyTypeUiCheckable(Triple(3, "Manor", R.string.manor), false),
        PropertyTypeUiCheckable(Triple(4, "Duplex", R.string.duplex), false),
        PropertyTypeUiCheckable(Triple(5, "Penthouse", R.string.penthouse), false)
    )

    val filter = FilterUi(listOf(Triple(0, "Maison", R.string.house),Triple(1, "Appartement", R.string.flat)), 100000, 200000, 50, 100, true, true, false, false, true, true, true)

    val checkedStates = listOf(
        Triple<FilterType, Boolean?, Int>(FilterType.SOLD, filter.sold, R.string.sold),
        Triple<FilterType, Boolean?, Int>(FilterType.NEARBY_SCHOOL, filter.nearbySchool, R.string.school),
        Triple<FilterType, Boolean?, Int>(FilterType.NEARBY_SHOP, filter.nearbyShop, R.string.shop),
        Triple<FilterType, Boolean?, Int>(FilterType.NEARBY_PARK, filter.nearbyPark, R.string.park),
        Triple<FilterType, Boolean?, Int>(FilterType.NEARBY_RESTAURANT, filter.nearbyRestaurant, R.string.restaurant),
        Triple<FilterType, Boolean?, Int>(FilterType.NEARBY_PUBLIC_TRANSPORTATION, filter.nearbyPublicTransportation, R.string.transport),
        Triple<FilterType, Boolean?, Int>(FilterType.NEARBY_PHARMACY, filter.nearbyPharmacy, R.string.pharmacy)
    )

    FilterContent(filter, PropertyTypeUiCheckable, checkedStates, { _, _ -> }, { _, _ -> }, { _ -> }, Modifier)
}