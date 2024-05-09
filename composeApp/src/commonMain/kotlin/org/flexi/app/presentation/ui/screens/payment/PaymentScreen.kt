package org.flexi.app.presentation.ui.screens.payment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CommentBank
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
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
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.order_placed_successfully
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.flexi.app.domain.model.user.User
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.AddressDetails
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.components.PaymentProductList
import org.flexi.app.presentation.ui.screens.cart.model.ProductDetails
import org.flexi.app.presentation.ui.navigation.tabs.orders.MyOrders
import org.flexi.app.presentation.ui.screens.order.MyPaymentOrderScreen
import org.flexi.app.presentation.ui.screens.order.MyProfileOrders
import org.flexi.app.presentation.ui.screens.payment.model.Order
import org.flexi.app.presentation.ui.screens.payment.model.PaymentMethodType
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

class PaymentScreen(
    private var productsDetailsList: List<ProductDetails>,
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: MainViewModel = koinInject()
        val navigator = LocalNavigator.current
        val scope = rememberCoroutineScope()
        var isEditAddress by remember { mutableStateOf(false) }
        var isPaymentMethods by remember { mutableStateOf(false) }
        var street by remember { mutableStateOf("") }
        var city by remember { mutableStateOf("") }
        var postalCode by remember { mutableStateOf("") }
        var country by remember { mutableStateOf("") }
        var userData by remember { mutableStateOf<User?>(null) }
        var updateAddress by remember { mutableStateOf<Boolean?>(null) }
        var orderPlacement by remember { mutableStateOf<Order?>(null) }
        var deleteCartItem by remember { mutableStateOf<String?>(null) }
        var totalPrice by remember { mutableStateOf(0.0) }
        var selectedPaymentMethod by remember { mutableStateOf(PaymentMethodType.NONE) }

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
        val orderState by viewModel.placeOrder.collectAsState()
        when (orderState) {
            is ResultState.Error -> {
                val error = (orderState as ResultState.Error).error
                ErrorBox(error)
            }

            is ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (orderState as ResultState.Success).response
                orderPlacement = response
            }
        }
        val deleteState by viewModel.deleteCart.collectAsState()
        when (deleteState) {
            is ResultState.Error -> {
                val error = (deleteState as ResultState.Error).error
                ErrorBox(error)
            }

            is ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (deleteState as ResultState.Success).response
                deleteCartItem = response
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
                    PaymentProductList(productsDetailsList)
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(all = 6.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Payment Method",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(75.dp)
                                .padding(6.dp)
                                .clickable {
                                    isPaymentMethods = !isPaymentMethods
                                },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, color = Color.LightGray)
                        ) {

                            when (selectedPaymentMethod) {
                                PaymentMethodType.CREDIT_CARD -> {
                                    PaymentMethod(
                                        icon = Icons.Default.CreditCard,
                                        name = "Credit Card",
                                        details = "xxxx xxxx xxxx 1234",
                                        isSelected = selectedPaymentMethod == PaymentMethodType.CREDIT_CARD,
                                        onSelected = {
                                            selectedPaymentMethod = PaymentMethodType.CREDIT_CARD
                                        }
                                    )

                                }

                                PaymentMethodType.PAYPAL -> {
                                    PaymentMethod(
                                        icon = Icons.Default.CommentBank,
                                        name = "PayPal",
                                        details = "example@gmail.com",
                                        isSelected = selectedPaymentMethod == PaymentMethodType.PAYPAL,
                                        onSelected = {
                                            selectedPaymentMethod = PaymentMethodType.PAYPAL
                                        }
                                    )

                                }

                                PaymentMethodType.ON_DELIVERY -> {
                                    PaymentMethod(
                                        icon = Icons.Default.LocalShipping,
                                        name = "On Delivery",
                                        details = "Cash on delivery",
                                        isSelected = selectedPaymentMethod == PaymentMethodType.ON_DELIVERY,
                                        onSelected = {
                                            selectedPaymentMethod = PaymentMethodType.ON_DELIVERY
                                        }
                                    )
                                }

                                PaymentMethodType.NONE -> {
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(14.dp),
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
                                productsDetailsList.map {
                                    totalPrice = it.totalPrice
                                }
                                append("${totalPrice}")
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
                            if (selectedPaymentMethod == PaymentMethodType.NONE) {

                            } else {
                                productsDetailsList.forEach { product ->
                                    val productId = product.productId
                                    val itemCount = product.itemCount.toString()
                                    val total_Price = product.itemPrice * product.itemCount
                                    val paymentType = "ON_DELIVERY"
                                    val selectedColor = "None"

                                    viewModel.placeOrderNow(
                                        userId = 1,
                                        productIds = productId.toInt(),
                                        totalQuantity = itemCount,
                                        totalPrice = total_Price.toInt(),
                                        paymentType = paymentType,
                                        selectedColor = selectedColor
                                    )
                                }
                            }
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
                            text = if (selectedPaymentMethod == PaymentMethodType.NONE) "Checkout Now" else "Confirm Payment",
                            color = Color.White
                        )
                    }
                }

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
                                        delay(200)
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
            if (orderPlacement != null) {
                LaunchedEffect(Unit) {
                    viewModel.deleteCart(1)
                    productsDetailsList = emptyList()
                }
                val sheetState = rememberModalBottomSheetState()
                ModalBottomSheet(
                    onDismissRequest = {
                        orderPlacement == null
                    },
                    sheetState = sheetState,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Image(
                            painter = painterResource(Res.drawable.order_placed_successfully),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp)
                        )
                        Text(
                            text = "Order Placed Successfully",
                            modifier = Modifier.padding(12.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Your order will be packed by the cart, will\n arrive at your home in 3-7 working days.",
                            modifier = Modifier.padding(12.dp),
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        FilledIconButton(
                            onClick = {
                                navigator?.push(MyPaymentOrderScreen(orderType = "Orders"))
                            },
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .height(55.dp)
                                .align(Alignment.CenterHorizontally),
                            enabled = true,
                            shape = RoundedCornerShape(24.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color(0xFF5821c4),
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Order Tracking",
                                color = Color.White
                            )
                        }
                    }
                }
            }
            if (isPaymentMethods) {
                val sheetState = rememberModalBottomSheetState()
                ModalBottomSheet(
                    onDismissRequest = {
                        isPaymentMethods = !isPaymentMethods
                    },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(all = 12.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Add Payment Method",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        PaymentMethod(
                            icon = Icons.Default.CreditCard,
                            name = "Credit Card",
                            details = "xxxx xxxx xxxx 1234",
                            isSelected = selectedPaymentMethod == PaymentMethodType.CREDIT_CARD,
                            onSelected = { selectedPaymentMethod = PaymentMethodType.CREDIT_CARD }
                        )
                        PaymentMethod(
                            icon = Icons.Default.CommentBank,
                            name = "PayPal",
                            details = "example@gmail.com",
                            isSelected = selectedPaymentMethod == PaymentMethodType.PAYPAL,
                            onSelected = { selectedPaymentMethod = PaymentMethodType.PAYPAL }
                        )
                        PaymentMethod(
                            icon = Icons.Default.LocalShipping,
                            name = "On Delivery",
                            details = "Cash on delivery",
                            isSelected = selectedPaymentMethod == PaymentMethodType.ON_DELIVERY,
                            onSelected = { selectedPaymentMethod = PaymentMethodType.ON_DELIVERY }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentMethod(
    icon: ImageVector,
    name: String,
    details: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 16.dp)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = name, fontWeight = FontWeight.Bold)
            Text(text = details, color = Color.Gray)
        }

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onSelected() },
            colors = CheckboxDefaults.colors(checkedColor = Color.Blue)
        )
    }
}