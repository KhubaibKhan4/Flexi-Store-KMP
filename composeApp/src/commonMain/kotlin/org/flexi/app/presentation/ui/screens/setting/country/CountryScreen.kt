package org.flexi.app.presentation.ui.screens.setting.country

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.flexi.app.utils.getCountries

class CountryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var searchQuery by remember { mutableStateOf("") }
        val countries = getCountries()
        var selectedCountryIndex by remember { mutableStateOf(-1) }
        var isDoneSelected by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 2.dp, end = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            navigator?.pop()
                        }
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "Country",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                    AnimatedVisibility (isDoneSelected) {
                        Text(
                            text = "Done",
                            modifier = Modifier.clickable { isDoneSelected = true },
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue
                        )
                    }
                }
            }
        ) { paddingValue ->
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = paddingValue.calculateTopPadding(), bottom = 34.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { search ->
                        searchQuery = search
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null,
                        )
                    },
                    placeholder = {
                        Text("Search Something")
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(8.dp)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedPlaceholderColor = Color.LightGray,
                        unfocusedLeadingIconColor = Color.Black,
                        focusedLeadingIconColor = Color.Black,
                        unfocusedTrailingIconColor = Color.Black,
                        focusedPlaceholderColor = Color.Gray,
                        focusedTrailingIconColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {

                    }
                    )
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                        .height(900.dp)
                ) {
                    items(countries.filter { it.contains(searchQuery, ignoreCase = true) }) { country ->
                        CountryListItem(
                            country = country,
                            isSelected = selectedCountryIndex != -1 && countries[selectedCountryIndex] == country,
                            onClick = {
                                val index = countries.indexOf(country)
                                if (index != -1) {
                                    selectedCountryIndex = index
                                    isDoneSelected = !isDoneSelected
                                }
                            }
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun CountryListItem(country: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = country,
                fontSize = 16.sp,
                color = if (isSelected) Color.Blue else Color.Black,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}