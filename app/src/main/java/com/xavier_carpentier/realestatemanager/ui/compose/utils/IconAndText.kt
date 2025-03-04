package com.xavier_carpentier.realestatemanager.ui.compose.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.ui.theme.AppTheme

@Composable
fun IconAndText(icon: Int,description: String , text: String){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painterResource(icon), contentDescription = description)
        Spacer(modifier= Modifier.width(4.dp))
        Text(text = text)
    }
}

@Composable
fun IconAndTextWithTitle(icon: Int,description: String , text: String, title: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(painterResource(icon), contentDescription = description)
        Spacer(modifier= Modifier.width(4.dp))
        Column(modifier = Modifier.width(200.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(bottom = 4.dp))
            Text(text = text, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
fun IconAndTextPreview() {
    AppTheme {
        IconAndText(R.drawable.baseline_location_pin_24, "Location", "123 Main Street NewYork")
    }
}

@Preview
@Composable
fun IconAndTextWithTitlePreview() {
    AppTheme {
        IconAndTextWithTitle(
            R.drawable.baseline_location_pin_24,
            "Location",
            "123 Main Street NewYork",
            "Address"
        )
    }
}
