package com.xavier_carpentier.realestatemanager.ui.compose.spinner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xavier_carpentier.realestatemanager.R

@Composable
fun Spinner(
    nameList:String,
    list: List<Pair<Int, String>>,
    preselected: Pair<Int, String>,
    onSelectionChanged: (selection: Pair<Int, String>) -> Unit
) {

    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value

    Box {
        Column {
            OutlinedTextField(
                value = (selected.second),
                onValueChange = {  },
                label = { Text(text = nameList) },
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                readOnly = true
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                list.forEach { entry ->

                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selected = entry
                            onSelectionChanged(selected)
                            expanded = false
                        },
                        text = {
                            Text(
                                text = (entry.second),
                                modifier = Modifier.wrapContentWidth().align(Alignment.Start))
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )
    }
}

// the Triple<id , databaseName , stringRes :Int>
@Composable
fun Spinner(
    nameList:String,
    list: List<Triple<Int, String, Int>>,
    preselected: Triple<Int, String, Int>,
    onSelectionChanged: (selection: Triple<Int, String, Int>) -> Unit
) {

    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value

    Box {
        Column {
            OutlinedTextField(
                value = (stringResource(selected.third)),
                onValueChange = {  },
                label = { Text(text = nameList) },
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                readOnly = true
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                list.forEach { entry ->

                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selected = entry
                            onSelectionChanged(selected)
                            expanded = false
                        },
                        text = {
                            Text(
                                text = (stringResource(entry.third)),
                                modifier = Modifier.wrapContentWidth().align(Alignment.Start))
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Spinner_Pair_Preview() {
    MaterialTheme {

        val entry1 = Pair(1, "Entry1")
        val entry2 = Pair(2, "Entry2")
        val entry3 = Pair(3, "Entry3")

        Spinner(
            "list Key Entry",
            listOf(entry1, entry2, entry3),
            preselected = entry2,
            onSelectionChanged = { selected -> /* do something with selected */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Spinner_Triple_Preview() {
    MaterialTheme {

        val entry1 = Triple(1, "house", R.string.house)
        val entry2 = Triple(2, "lama", R.string.flat)
        val entry3 = Triple(3, "villa", R.string.villa)

        Spinner(
            "list Key Entry",
            listOf(entry1, entry2, entry3),
            preselected = entry2,
            onSelectionChanged = { selected -> /* do something with selected */ }
        )
    }
}