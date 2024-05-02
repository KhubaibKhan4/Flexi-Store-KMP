package org.flexi.app.presentation.ui.screens.setting.address

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.flexi.app.domain.model.user.User
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.AddressDetails
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.screens.payment.model.Order
import org.flexi.app.presentation.ui.screens.payment.model.PaymentMethodType
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.koin.compose.koinInject

class AddressScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: MainViewModel = koinInject()
        val navigator = LocalNavigator.current
        val scope = rememberCoroutineScope()
        var isEditAddress by remember { mutableStateOf(false) }
        var street by remember { mutableStateOf("") }
        var city by remember { mutableStateOf("") }
        var postalCode by remember { mutableStateOf("") }
        var country by remember { mutableStateOf("") }
        var userData by remember { mutableStateOf<User?>(null) }
        var updateAddress by remember { mutableStateOf<Boolean?>(null) }
        var orderPlacement by remember { mutableStateOf<Order?>(null) }
        var deleteCartItem by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(updateAddress) {
            viewModel.getUserData(1)
        }
        val userState by viewModel.userData.collectAsState()
        when (userState) {
            is ResultState.Error -> {
                val error = (userState as ResultState.Error).error
                ErrorBox(error)
            }

            is ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (userState as ResultState.Success).response
                userData = response
            }
        }
        val updateAddressState by viewModel.updateAddress.collectAsState()
        when (updateAddressState) {
            is ResultState.Error -> {
                val error = (updateAddressState as ResultState.Error).error
                //ErrorBox(error)
            }

            is ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (updateAddressState as ResultState.Success).response
                updateAddress = response
            }
        }

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
                        text = "My Address",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 0.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    AddressDetails(
                        cityName = "${userData?.city}, ${userData?.country}",
                        postalCode = userData?.postalCode ?: "Not Found",
                        address = userData?.address ?: "No Address Found",
                        onEditClicked = {
                            isEditAddress = !isEditAddress
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                if (isEditAddress) {
                    val sheetState = rememberModalBottomSheetState()
                    ModalBottomSheet(
                        onDismissRequest = {
                            isEditAddress = !isEditAddress
                        },
                        sheetState = sheetState
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(all = 12.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            TextField(
                                value = street,
                                onValueChange = { street = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .height(56.dp),
                                placeholder = { Text("Street #1 South East") },
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    focusedTextColor = Color.Black,
                                    unfocusedPlaceholderColor = Color.Gray,
                                    focusedPlaceholderColor = Color.Gray,
                                    focusedContainerColor = Color.LightGray,
                                    unfocusedContainerColor = Color.LightGray,
                                    unfocusedTextColor = Color.Black,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextField(
                                    value = city,
                                    onValueChange = { city = it },
                                    modifier = Modifier
                                        .fillMaxWidth(0.65f)
                                        .padding(vertical = 8.dp)
                                        .height(56.dp),
                                    placeholder = { Text("City") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(8.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color.Transparent,
                                        focusedTextColor = Color.Black,
                                        unfocusedPlaceholderColor = Color.Gray,
                                        focusedPlaceholderColor = Color.Gray,
                                        focusedContainerColor = Color.LightGray,
                                        unfocusedContainerColor = Color.LightGray,
                                        unfocusedTextColor = Color.Black,
                                        unfocusedIndicatorColor = Color.Transparent
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                TextField(
                                    value = postalCode,
                                    onValueChange = { postalCode = it },
                                    modifier = Modifier
                                        .fillMaxWidth(0.35f)
                                        .weight(1f)
                                        .padding(vertical = 8.dp)
                                        .height(56.dp),
                                    placeholder = { Text("Postal Code") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(8.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color.Transparent,
                                        focusedTextColor = Color.Black,
                                        unfocusedPlaceholderColor = Color.Gray,
                                        focusedPlaceholderColor = Color.Gray,
                                        focusedContainerColor = Color.LightGray,
                                        unfocusedContainerColor = Color.LightGray,
                                        unfocusedTextColor = Color.Black,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                                )
                            }
                            TextField(
                                value = country,
                                onValueChange = { country = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .height(56.dp)
                                    .align(Alignment.CenterHorizontally),
                                placeholder = { Text("Country") },
                                singleLine = true,
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    focusedTextColor = Color.Black,
                                    unfocusedPlaceholderColor = Color.Gray,
                                    focusedPlaceholderColor = Color.Gray,
                                    focusedContainerColor = Color.LightGray,
                                    unfocusedContainerColor = Color.LightGray,
                                    unfocusedTextColor = Color.Black,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            FilledIconButton(
                                onClick = {
                                    if (street.isNotEmpty() && city.isNotEmpty() && country.isNotEmpty() && postalCode.isNotEmpty()) {
                                        scope.launch {
                                            viewModel.updateAddress(
                                                address = street,
                                                city = city,
                                                country = country,
                                                postalCode = postalCode.toLong()
                                            )
                                            delay(300)
                                            viewModel.getUserData(1)
                                            isEditAddress = !isEditAddress
                                        }

                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth(.5f)
                                    .height(55.dp)
                                    .padding(top = 4.dp)
                                    .align(Alignment.CenterHorizontally),
                                enabled = true,
                                shape = RoundedCornerShape(24.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color(0xFF5821c4),
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    text = "Save Now",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}