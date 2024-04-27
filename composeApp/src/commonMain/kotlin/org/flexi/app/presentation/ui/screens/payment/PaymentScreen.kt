package org.flexi.app.presentation.ui.screens.payment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.flexi.app.domain.model.products.Products
import org.flexi.app.presentation.ui.components.AddressDetails
import org.flexi.app.presentation.ui.components.PaymentProductList
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.koin.compose.koinInject

class PaymentScreen
    (
    private val products: List<Products>,
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: MainViewModel = koinInject()
        val navigator = LocalNavigator.current
        var isEditAddress by remember { mutableStateOf(false) }
        var street by remember { mutableStateOf("") }
        var city by remember { mutableStateOf("") }
        var postalCode by remember { mutableStateOf("") }
        var country by remember { mutableStateOf("") }
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
                        text = "My Cart",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                }
            }
        ) { paddingValue ->
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = paddingValue.calculateTopPadding(), bottom = 34.dp),
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
                        cityName = "New York",
                        postalCode = "10001",
                        address = "1234 Elm Street, Apt 5A",
                        onEditClicked = {
                            isEditAddress = !isEditAddress
                        }
                    )
                    PaymentProductList(products)
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(all = 6.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Payment Method",
                            fontSize =20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier
                                .padding(6.dp)
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, color = Color.Gray)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircleOutline,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(14.dp))
                                Text(
                                    text = "Add Payment Method",
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total amount",
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            color = Color.Gray
                        )
                        val price = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    baselineShift = BaselineShift.Superscript
                                )
                            ) {
                                append("\$")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("${1400}.00")
                            }
                        }
                        Text(
                            text = price,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledIconButton(
                        onClick = {
                        },
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .height(55.dp)
                            .padding(top = 4.dp),
                        enabled = true,
                        shape = RoundedCornerShape(24.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0xFF5821c4),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Checkout Now",
                            color = Color.White
                        )
                    }
                }

            }
            if (isEditAddress){
                val sheetState= rememberModalBottomSheetState()
                ModalBottomSheet(
                    onDismissRequest = {
                        isEditAddress = !isEditAddress
                    },
                    sheetState = sheetState
                ){
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
                                unfocusedTextColor = Color.White,
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
                                    unfocusedTextColor = Color.White,
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
                                    unfocusedTextColor = Color.White,
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
                                unfocusedTextColor = Color.White,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FilledIconButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .height(55.dp)
                                .padding(top = 4.dp)
                                .align(Alignment.CenterHorizontally)
                            ,
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