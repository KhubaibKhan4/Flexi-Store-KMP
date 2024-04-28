package org.flexi.app.presentation.ui.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Discount
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.no_item_found
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import org.flexi.app.domain.model.cart.CartItem
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.components.ErrorBox
import org.flexi.app.presentation.ui.screens.cart.model.ProductDetails
import org.flexi.app.presentation.ui.screens.payment.PaymentScreen
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.flexi.app.utils.Constant.BASE_URL
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

class CartList(
    private var cartItem: List<CartItem>,
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: MainViewModel = koinInject()
        var product by remember { mutableStateOf<List<Products>?>(null) }
        val ids by remember { mutableStateOf(cartItem.map { it.productId.toLong() }) }
        val quantityMap = cartItem.associate { it.productId to it.quantity }.toMutableMap()
        var isBottomSheetVisible by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()
        var deleteResponse by remember { mutableStateOf<Boolean?>(null) }
        var totalAmount by remember { mutableStateOf(0.0) }
        LaunchedEffect(Unit) {
            if (ids.isNotEmpty()) {
                viewModel.getProductById(ids)
            }
        }

        val productState by viewModel.productItem.collectAsState()
        when (productState) {
            is ResultState.Error -> {
                val error = (productState as ResultState.Error).error
                ErrorBox(error)
            }

            is ResultState.Loading -> {
                if (ids.isNotEmpty()) {
                    //LoadingBox()
                }
            }

            is ResultState.Success -> {
                val response = (productState as ResultState.Success).response
                product = response
            }
        }
        val deleteState by viewModel.deleteItem.collectAsState()
        when (deleteState) {
            is ResultState.Error -> {
                val error = (deleteState as ResultState.Error).error
                ErrorBox(error)
            }

            is ResultState.Loading -> {
                // LoadingBox()
            }

            is ResultState.Success -> {
                val response = (deleteState as ResultState.Success).response
                deleteResponse = response
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
                    Text(
                        text = "My Cart",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            scope.launch {
                                isBottomSheetVisible = !isBottomSheetVisible
                            }
                        }
                    )
                }
            }
        ) { it ->
            if (cartItem.isEmpty() || ids.isEmpty() || product?.isEmpty() == true) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = it.calculateTopPadding(), bottom = 34.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.no_item_found),
                            contentDescription = null,
                            modifier = Modifier.size(250.dp)
                        )
                        Text(
                            text = "No Product available",
                            modifier = Modifier.padding(12.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = it.calculateTopPadding(), bottom = 34.dp)
                ) {
                    product?.let { list ->
                        items(list) { pro ->
                            val quantity = quantityMap[pro.id] ?: 0
                            var productQuantity by remember { mutableStateOf(quantity) }
                            var totalPrice by remember { mutableStateOf(pro.price * quantity) }

                            LaunchedEffect(productQuantity) {
                                totalAmount = cartItem.sumOf { cartItem ->
                                    val product = product?.find { it.id == cartItem.productId }
                                    product?.price?.times(quantityMap[cartItem.productId] ?: 0)
                                        ?.toDouble() ?: 0.0
                                }
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .height(220.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                        .height(145.dp)
                                        .padding(all = 6.dp)
                                ) {
                                    val image: Resource<Painter> =
                                        asyncPainterResource(BASE_URL + pro?.imageUrl.toString())
                                    KamelImage(
                                        resource = image,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxWidth()
                                            .clip(RoundedCornerShape(6.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Row(
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .width(84.dp)
                                            .height(34.dp)
                                            .align(Alignment.BottomStart)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                color = Color.LightGray.copy(
                                                    alpha = 0.45f
                                                )
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Remove,
                                            contentDescription = "Decrease",
                                            modifier = Modifier.size(25.dp)
                                                .clip(CircleShape)
                                                .background(Color.White)
                                                .clickable {
                                                    if (productQuantity > 1) {
                                                        productQuantity--
                                                        totalPrice = pro.price * productQuantity
                                                        quantityMap[pro.id] = productQuantity
                                                    }
                                                }
                                        )
                                        Text(
                                            text = productQuantity.toString(),
                                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Increase",
                                            modifier = Modifier
                                                .size(25.dp)
                                                .clip(CircleShape)
                                                .background(Color.White)
                                                .clickable {
                                                    productQuantity++
                                                    totalPrice = pro.price * productQuantity
                                                    quantityMap[pro.id] = productQuantity
                                                }
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .size(30.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                            .align(Alignment.TopEnd),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.DeleteOutline,
                                            contentDescription = null,
                                            tint = Color.Red,
                                            modifier = Modifier.padding(2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                            .weight(1f),
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            text = pro?.name.toString(),
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        val color = buildAnnotatedString {
                                            withStyle(
                                                SpanStyle(
                                                    color = Color.Gray,
                                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                                                )
                                            ) {
                                                append("Color: ")
                                            }
                                            withStyle(
                                                SpanStyle(
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                                                )
                                            ) {
                                                append(pro?.colors)
                                            }
                                        }
                                        Text(color)
                                    }
                                    val price = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = Color(0xFF5821c4),
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
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        ) {
                                            append("${totalPrice}.00")
                                        }
                                    }
                                    Text(text = price)
                                }
                            }
                            if (isBottomSheetVisible) {
                                val shipping by remember { mutableStateOf(7.0) }
                                var isPromo by remember { mutableStateOf("") }
                                ModalBottomSheet(
                                    onDismissRequest = {
                                        isBottomSheetVisible = !isBottomSheetVisible
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    sheetState = sheetState
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(all = 12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        TextField(
                                            value = isPromo,
                                            onValueChange = { value ->
                                                isPromo = value
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(0.88f)
                                                .clip(
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .border(
                                                    width = 2.dp,
                                                    color = if (isPromo.isEmpty()) Color.LightGray else Color.Gray,
                                                    shape = RoundedCornerShape(12.dp)
                                                ),
                                            singleLine = true,
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = Icons.Outlined.Discount,
                                                    contentDescription = null,
                                                    tint = Color.Gray
                                                )
                                            },
                                            trailingIcon = {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                                    contentDescription = null,
                                                    tint = Color.Gray
                                                )
                                            },
                                            placeholder = {
                                                Text("Enter your promo code")
                                            },
                                            colors = TextFieldDefaults.colors(
                                                focusedTextColor = Color.Gray,
                                                unfocusedPlaceholderColor = Color.Gray,
                                                focusedPlaceholderColor = Color.Gray,
                                                unfocusedLeadingIconColor = Color.Gray,
                                                focusedLeadingIconColor = Color.Gray,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent
                                            )
                                        )
                                        Spacer(Modifier.height(12.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Subtotal",
                                                color = Color.Gray,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                                            )
                                            val subTotalPrice = buildAnnotatedString {
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
                                                    append("$totalAmount")
                                                }
                                            }
                                            Text(
                                                text = subTotalPrice
                                            )
                                        }
                                        Spacer(Modifier.height(12.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Shipping",
                                                color = Color.Gray,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                                            )
                                            val shippingPrice = buildAnnotatedString {
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
                                                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                ) {
                                                    append("$shipping")
                                                }
                                            }
                                            Text(
                                                text = shippingPrice
                                            )
                                        }
                                        Spacer(Modifier.height(8.dp))
                                        HorizontalDivider(
                                            modifier = Modifier.fillMaxWidth(),
                                            color = Color.LightGray,
                                            thickness = 2.dp
                                        )
                                        Spacer(Modifier.height(12.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Total amount",
                                                color = Color.Gray,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                                            )
                                            val totalProductPrice = buildAnnotatedString {
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
                                                        fontSize = 21.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                ) {
                                                    append("${totalAmount + shipping}")
                                                }
                                            }
                                            Text(
                                                text = totalProductPrice
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(12.dp))
                                        FilledIconButton(
                                            onClick = {
                                                val productsDetailsList = product?.map { pro ->
                                                    ProductDetails(
                                                        imageUrl = pro.imageUrl,
                                                        itemCount = quantityMap[pro.id] ?: 0,
                                                        itemPrice = pro.price.toDouble(),
                                                        totalPrice = totalAmount+shipping,
                                                        colors = pro.colors,
                                                        title = pro.name
                                                    )
                                                }
                                                navigator?.push(PaymentScreen(productsDetailsList!!))
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
                                                text = "Checkout",
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
        }
    }
}