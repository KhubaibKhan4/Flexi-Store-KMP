package org.flexi.app.presentation.ui.screens.order

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.flexi.app.domain.model.products.Products
import org.flexi.app.presentation.ui.components.FeaturedItems
import org.flexi.app.presentation.ui.screens.payment.model.Order
import org.flexi.app.utils.formatOrderDateTime

class OrderDetail(
    private val products: Products,
    private val order: Order,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            navigator?.pop()
                        }
                    )
                    Text(
                        text = "Need Help?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color =  Color(0xFF5821c4)
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
                OrderDetailsSection(order)
                OrderStatusSection(order)
                FullFilledSection(products, order)
            }
        }
    }
}

@Composable
fun OrderDetailsSection(
    order: Order,
) {
    val (date, time) = formatOrderDateTime(order.orderDate)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionTitle(text = "Order Details")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OrderDetailItem("Order Created", date)
                HorizontalDivider()
                OrderDetailItem("Order Time", time)
                HorizontalDivider()
                OrderDetailItem("Order ID", "#${order.id}")
            }
        }
    }
}

@Composable
fun OrderStatusSection(
    order: Order,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionTitle(text = "Order Status")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            val orderStatus =
                if (order.orderProgress == "On Progress" || order.orderProgress == "On The Way") "Pending" else "Completed"
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                OrderDetailItem(
                    "Order Status",
                    orderStatus
                )
            }
        }
    }
}

@Composable
fun FullFilledSection(
    products: Products,
    order: Order,
) {
    var isProductVisible by remember { mutableStateOf(false) }
    val orderQty = order.totalQuantity
    val trackingId = order.trackingId.take(7)
    val trackingNumber = order.trackingId.replace("-", "").take(15)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        SectionTitle(text = "Full-Filled ($orderQty)")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        easing = LinearOutSlowInEasing,
                        delayMillis = 100,
                        durationMillis = 1000
                    )
                )
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                OrderDetailItem("Tracking ID", "#$trackingId")
                HorizontalDivider()
                OrderDetailItem("Tracking Number", trackingNumber)
                HorizontalDivider()
                TrackingUrlItem("Tracking URL", "Click Here")
                HorizontalDivider()
                OrderQtyItem(
                    "Items",
                    "Qty. $orderQty",
                    onArrowClick = { isProductVisible = !isProductVisible })
                Spacer(modifier = Modifier.height(4.dp))
                if (isProductVisible) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(orderQty.toInt()) {
                            FeaturedItems(products)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color =  Color(0xFF5821c4),
            fontSize = 14.sp
        )
    }
}

@Composable
fun OrderQtyItem(label: String, value: String, onArrowClick: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                color =  Color(0xFF5821c4),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(3.dp))
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = tween(
                            easing = LinearOutSlowInEasing,
                            delayMillis = 100,
                            durationMillis = 1000
                        )
                    )
                    .clickable {
                        onArrowClick()
                        isExpanded = !isExpanded
                    }
            )
        }
    }
}

@Composable
fun TrackingUrlItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color =  Color(0xFF5821c4),
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(horizontal = 16.dp),
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}