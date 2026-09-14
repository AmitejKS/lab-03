package com.example.listycity3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.OutlinedButton

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {

    var cityNameInput by remember { mutableStateOf("") }
    var provinceNameInput by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf<City?>(null) }

    Column() {
        LazyColumn(modifier = Modifier.weight(1f).padding(top = 40.dp)) {
            itemsIndexed(cities) { index, city ->
                CityRow(
                    city = city,
                    onEditClick = {
                        selectedCity = city
                        cityNameInput = city.name
                        provinceNameInput = city.province
                    }
                )

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = cityNameInput,
                onValueChange = { cityNameInput = it },
                label = { Text("City Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = provinceNameInput,
                onValueChange = { provinceNameInput = it },
                label = { Text("Province") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectedCity != null) {
                    OutlinedButton(
                        onClick = {
                            selectedCity = null
                            cityNameInput = ""
                            provinceNameInput = ""
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                }

                Button(
                    onClick = {
                        if (cityNameInput.isNotBlank() && provinceNameInput.isNotBlank()) {
                            val newCity = City(cityNameInput, provinceNameInput)
                            val currentSelection = selectedCity

                            if (currentSelection != null) {
                                onUpdateCity(currentSelection, newCity)
                            } else {
                                onAddCity(newCity)
                            }
                            selectedCity = null
                            cityNameInput = ""
                            provinceNameInput = ""
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (selectedCity != null) "Update City" else "Add City")
                }
            }
        }
    }

}

@Composable
fun CityRow(
    city: City,
    onEditClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = city.name,
            fontSize = 24.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 24.sp,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onEditClick) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit ${city.name}"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onUpdateCity = { _, _ -> }
        )
    }
}