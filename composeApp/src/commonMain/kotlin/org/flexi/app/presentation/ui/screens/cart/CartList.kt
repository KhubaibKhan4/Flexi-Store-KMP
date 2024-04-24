package org.flexi.app.presentation.ui.screens.cart

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.flexi.app.domain.model.cart.CartItem

class CartList(
    private val cartItem: List<CartItem>,
) : Screen {
    @Composable
    override fun Content() {
        LazyColumn {
            items(cartItem) {
                CartItem(it)
            }
        }
    }

}

@Composable
fun CartItem(
    cartItem: CartItem,
) {
    var producstItems by remember { mutableStateOf(1) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckBox,
                contentDescription = null,
                modifier = Modifier.size(55.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            val image: Resource<Painter> = asyncPainterResource("")
            KamelImage(
                resource = image,
                contentDescription = null,
                modifier = Modifier.width(55.dp)
                    .height(35.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Bix Bag Limited Edition 229",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                val color = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = Color.LightGray,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
                    ) {
                        append("Color:")
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        )
                    ) {
                        append("Brown")
                    }
                }
                Text(color)
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .padding(2.dp)
                            .width(84.dp)
                            .height(34.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(color = Color.LightGray.copy(alpha = 0.45f)),
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
                                    if (producstItems >= 1) {
                                        producstItems--
                                    }
                                }
                        )
                        Text(
                            text = producstItems.toString(),
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
                                    producstItems++
                                }
                        )
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
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("65.00")
                        }
                    }
                    Text(
                        text = price,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }


            }
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(.85f),
            color = Color.LightGray
        )
    }
}