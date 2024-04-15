package org.flexi.app.presentation.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.flexi.app.domain.model.products.Products
import org.flexi.app.utils.Constant.BASE_URL

class DetailScreen(
    val products: Products,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var producstItems by remember { mutableStateOf(0) }
        val colorOptions = listOf(
            Color.Blue,
            Color.Black,
            Color.Green,
            Color.Red,
            Color.Gray,
            Color.Cyan
        )
        var selectedColor by remember { mutableStateOf(Color.Blue) }
        var maxLines by remember { mutableStateOf(5) }
        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(enabled = true, state = scrollState)
                .padding(bottom = 34.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    val image: Resource<Painter> =
                        asyncPainterResource(data = BASE_URL + products.imageUrl)
                    KamelImage(
                        resource = image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                            .height(360.dp),
                        contentScale = ContentScale.Crop,
                        onLoading = {
                            CircularProgressIndicator(progress = { it })
                        },
                        onFailure = {
                            Text(it.toString())
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .background(color = Color.Black.copy(alpha = 0.05f))
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Arrow Back",
                            modifier = Modifier.size(25.dp)
                                .clickable { navigator?.pop() }
                        )
                        Text(
                            text = "Detail Product",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Icon(
                            imageVector = Icons.Default.ShoppingBasket,
                            contentDescription = "Shopping Cart",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 240.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 8.dp)
                        .heightIn(400.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .align(Alignment.Center)
                            .clip(
                                RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = products.name,
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color.Yellow
                                        )
                                        Text(
                                            text = "4.8",
                                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                        )
                                        Text(
                                            text = "(320 Review)",
                                            color = Color.LightGray
                                        )
                                    }
                                }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
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
                                                    } else {
                                                        producstItems = 0
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
                                    Text(
                                        text = "Available in stock",
                                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(top = 6.dp, start = 12.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Color",
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(0.55f),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    colorOptions.forEach { color ->
                                        ColorOption(
                                            color = color,
                                            isSelected = selectedColor == color,
                                            onColorSelected = { selectedColor = color }
                                        )
                                    }
                                }
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(top = 6.dp, start = 12.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Description",
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Bold
                                )

                                val descriptionThreshold = 100
                                val isLongDescription =
                                    products.description.length > descriptionThreshold

                                Text(
                                    text = buildAnnotatedString {
                                        append(
                                            if (isLongDescription) {
                                                if (maxLines == 5) {
                                                    "${
                                                        products.description.take(
                                                            descriptionThreshold
                                                        )
                                                    }... "
                                                } else {
                                                    products.description
                                                }
                                            } else {
                                                products.description
                                            }
                                        )

                                        if (isLongDescription) {
                                            append(
                                                if (maxLines == 5) "Read More" else "Read Less"
                                            )
                                        }
                                    },
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                    fontWeight = FontWeight.Normal,
                                    maxLines = maxLines,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.clickable {
                                        maxLines = if (maxLines == 5) Int.MAX_VALUE else 5
                                    }
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorOption(
    color: Color,
    isSelected: Boolean,
    onColorSelected: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(35.dp)
            .clip(CircleShape)
            .background(color = color)
            .clickable { onColorSelected() }
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center)
            )
        }
    }
}