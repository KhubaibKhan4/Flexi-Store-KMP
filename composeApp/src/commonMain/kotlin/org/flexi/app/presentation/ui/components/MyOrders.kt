package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MapsHomeWork
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.no_item_found
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.usecase.ResultState
import org.flexi.app.presentation.ui.screens.payment.model.Order
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.flexi.app.utils.Constant.BASE_URL
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


class MyOrdersContent : Screen {
    @Composable
    override fun Content() {
        val viewModel: MainViewModel = koinInject()
        var myOrderData by remember { mutableStateOf<List<Order>?>(null) }
        var productIdsList by remember { mutableStateOf<List<Long>?>(null) }
        var productList by remember { mutableStateOf<List<Products>?>(null) }
        val tabs = listOf("My Orders", "Order History")
        var selectedTabIndex by remember { mutableStateOf(0) }
        LaunchedEffect(Unit) {
            viewModel.getMyOrders(1)
        }
        LaunchedEffect(productIdsList) {
            productIdsList?.let { viewModel.getProductById(it) }
        }
        val myOrderState by viewModel.myOrders.collectAsState()
        when (myOrderState) {
            is ResultState.Error -> {
                val error = (myOrderState as ResultState.Error).error
                // ErrorBox(error)
            }

            ResultState.Loading -> {
                LoadingBox()
            }

            is ResultState.Success -> {
                val response = (myOrderState as ResultState.Success).response
                myOrderData = response
                productIdsList = response.map { it.productIds.toLong() }.distinct()
            }
        }
        val productState by viewModel.productItem.collectAsState()
        when (productState) {
            is ResultState.Error -> {
                val error = (productState as ResultState.Error).error
                // ErrorBox(error)
            }

            ResultState.Loading -> {
                //LoadingBox()
            }

            is ResultState.Success -> {
                val response = (productState as ResultState.Success).response
                productList = response
            }
        }


        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "My Order",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = null
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = 34.dp,
                        start = 6.dp,
                        end = 6.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        contentColor = Color.Black,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                color = Color.Black,
                                height = 2.dp,
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Text(
                                        text = title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            )
                        }
                    }
                    when (selectedTabIndex) {
                        0 -> {
                            val activeOrders =
                                myOrderData?.filter { it.orderProgress == "On Progress" || it.orderProgress == "On The Way" }
                            if (activeOrders?.isEmpty() == true) {
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
                                            text = "No Active Order available",
                                            modifier = Modifier.padding(12.dp),
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Red
                                        )
                                    }
                                }
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(300.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalArrangement = Arrangement.Center,
                                    contentPadding = PaddingValues(6.dp)
                                ) {
                                    activeOrders?.let { orders ->
                                        items(orders) { order ->
                                            val product =
                                                productList?.find { it.id.toString() == order.productIds }
                                            product?.let { MyOrderItems(it, order) }
                                        }
                                    }
                                }
                            }
                        }

                        1 -> {
                            val completedOrders =
                                myOrderData?.filter { it.orderProgress == "Completed" }
                            if (completedOrders.isNullOrEmpty()) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painterResource(Res.drawable.no_item_found),
                                        contentDescription = null,
                                        modifier = Modifier.size(250.dp)
                                    )
                                    Text(
                                        text = "No Previous Order Found",
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(300.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalArrangement = Arrangement.Center,
                                    contentPadding = PaddingValues(6.dp)
                                ) {
                                    items(completedOrders) { order ->
                                        val product =
                                            productList?.find { it.id.toString() == order.productIds }
                                        product?.let { MyOrderItems(it, order) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrderItems(
    products: Products,
    order: Order,
) {
    var trackOrder by remember { mutableStateOf(false) }
    var orderDetails by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth()
            .height(190.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                val image: Resource<Painter> = asyncPainterResource(BASE_URL + products.imageUrl)
                KamelImage(
                    resource = image,
                    contentDescription = null,
                    modifier = Modifier.width(125.dp)
                        .height(90.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = products.name,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )

                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp, start = 4.dp)
                                .wrapContentWidth()
                                .padding(4.dp)
                                .border(
                                    1.dp,
                                    if (order.orderProgress == "On Progress") Color.Red.copy(alpha = 0.65f) else if (order.orderProgress == "On The Way") Color.Blue.copy(alpha = 0.65f) else Color.Green.copy(
                                        alpha = 0.65f
                                    ),
                                    RoundedCornerShape(topEnd = 14.dp, bottomStart = 14.dp)
                                )
                        ) {
                            Text(
                                text = order.orderProgress,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = if (order.orderProgress == "On Progress") Color.Red.copy(alpha = 0.65f) else if (order.orderProgress == "On The Way") Color.Blue.copy(alpha = 0.65f) else Color.Green.copy(
                                    alpha = 0.65f
                                ),
                                modifier = Modifier.padding(4.dp)
                            )
                        }


                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 0.dp, end = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val colors = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            ) {
                                append("Color: ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(order.selectedColor)
                            }
                        }
                        Text(text = colors)
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
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("${order.totalPrice}")
                            }
                        }
                        Text(
                            text = totalProductPrice,
                        )
                    }
                    val quantity = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        ) {
                            append("Qty: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(order.totalQuantity)
                        }
                    }
                    Text(text = quantity)

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 0.dp, top = 4.dp, start = 4.dp, end = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    onClick = {
                        orderDetails = !orderDetails
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(top = 4.dp)
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp)),
                    enabled = true,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Detail",
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                FilledIconButton(
                    onClick = {
                        trackOrder = !trackOrder
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(top = 4.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    enabled = true,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF5821c4)
                    )
                ) {
                    Text(
                        text = if (order.orderProgress == "Completed") "Received Order" else "Tracking",
                        color = Color.White
                    )
                }
            }
        }
    }
    if (trackOrder) {
        val sheetState = rememberModalBottomSheetState()
        val progressStatus = order.orderProgress
        ModalBottomSheet(
            onDismissRequest = {
                trackOrder = !trackOrder
            },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Progress of Your Order",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
                Spacer(modifier = Modifier.height(8.dp))
                OrderStatusItem(
                    icon = Icons.Outlined.MapsHomeWork,
                    text = "UpBox Bag",
                    status = "Shop",
                    time = "02:50 PM",
                    orderProgress = progressStatus
                )
                OrderStatusItem(
                    icon = Icons.Outlined.DeliveryDining,
                    text = "On the way",
                    status = "Delivery",
                    time = "03:20 PM",
                    orderProgress = progressStatus
                )
                OrderStatusItem(
                    icon = Icons.Outlined.LocationOn,
                    text = "5482 Adobe Falls Rd #15San Diego",
                    status = "House",
                    time = "03:45 PM",
                    orderProgress = progressStatus
                )
            }
        }
    }
}

enum class ProgressStatus {
    ON_PROGRESS,
    ON_THE_WAY,
    COMPLETED
}

@Composable
fun OrderStatusItem(
    icon: ImageVector,
    text: String,
    status: String,
    time: String,
    orderProgress: String,
) {
    val progressStatus = when (orderProgress) {
        "On Progress" -> ProgressStatus.ON_PROGRESS
        "On The Way" -> ProgressStatus.ON_THE_WAY
        "Completed" -> ProgressStatus.COMPLETED
        else -> ProgressStatus.ON_PROGRESS
    }

    val backgroundColor = when (progressStatus) {
        ProgressStatus.ON_PROGRESS -> if (status == "Shop") Color(0xFF5821c4) else Color.LightGray
        ProgressStatus.ON_THE_WAY -> if (status == "Shop" || status == "Delivery") Color(0xFF5821c4) else Color.LightGray
        ProgressStatus.COMPLETED -> Color(0xFF5821c4)
    }

    val textColor =
        if (progressStatus == ProgressStatus.COMPLETED) Color(0xFF5821c4) else Color.Black

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp)
                .background(backgroundColor, shape = CircleShape)
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
                    .padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = textColor
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = status,
                    fontSize = 13.sp,
                    color = textColor
                )
                Text(
                    text = ".",
                    fontSize = 13.sp,
                    color = textColor,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = time,
                    fontSize = 13.sp,
                    color = textColor
                )
            }
        }
    }

    if (status != "House") {
        VerticalDivider(
            color = backgroundColor,
            thickness = 3.dp,
            modifier = Modifier.fillMaxHeight(0.08f)
                .padding(horizontal = 16.dp)
        )
    }
}