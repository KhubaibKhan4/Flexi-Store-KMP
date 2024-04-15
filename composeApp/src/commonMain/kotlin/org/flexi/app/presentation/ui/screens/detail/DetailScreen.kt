package org.flexi.app.presentation.ui.screens.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.semantics.Role
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
        var isFavourite by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()
        val descriptionThreshold = 100
        val isLongDescription =
            products.description.length > descriptionThreshold

        Box(
            modifier = Modifier.fillMaxWidth()
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
                            .height(390.dp),
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
                            .background(color = Color.Black.copy(alpha = 0.25f))
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Arrow Back",
                            modifier = Modifier.size(25.dp)
                                .clickable { navigator?.pop() },
                            tint = Color.White
                        )
                        Text(
                            text = "Detail Product",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = "Shopping Cart",
                            modifier = Modifier.size(25.dp),
                            tint = Color.White
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .animateContentSize(
                        animationSpec = tween()
                    )
                    .padding(top = if (isLongDescription) 320.dp else 340.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 8.dp)
                        .heightIn(200.dp)
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
                                            color = Color.LightGray,
                                            fontSize = MaterialTheme.typography.labelMedium.fontSize
                                        )
                                    }
                                }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .width(42.dp)
                                            .height(42.dp)
                                            .clip(CircleShape)
                                            .background(color = Color.LightGray.copy(alpha = 0.45f)),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                            contentDescription = "Decrease",
                                            modifier = Modifier
                                                .size(25.dp)
                                                .clickable(
                                                    interactionSource = MutableInteractionSource(),
                                                    indication = rememberRipple(color = Color.Red),
                                                    onClick = {
                                                        isFavourite = !isFavourite
                                                    }
                                                ),
                                            tint = if (isFavourite) Color.Red else Color.White
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
                            ExpandableDescription(products.description)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 12.dp, end = 12.dp, top = 12.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val image: Resource<Painter> =
                                asyncPainterResource(data = BASE_URL + products.imageUrl)
                            KamelImage(
                                resource = image,
                                contentDescription = null,
                                modifier = Modifier.size(35.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.padding(start = 3.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    modifier = Modifier.wrapContentWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Upbox Bog",
                                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = Color.Blue,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Row(
                                    modifier = Modifier.wrapContentWidth()
                                        .padding(start = 6.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "104 Products",
                                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.DarkGray
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "1.5k Followers",
                                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.DarkGray
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                            FilledIconButton(
                                onClick = {},
                                modifier = Modifier
                                    .width(75.dp)
                                    .height(30.dp),
                                enabled = true,
                                shape = RoundedCornerShape(24.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.Blue,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Follow")
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 12.dp, end = 12.dp, top = 14.dp),
                            color = Color.LightGray
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 20.dp, bottom = 20.dp, start = 14.dp, end = 14.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Choose amount:",
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
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
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .animateContentSize()
                                .padding(all = 6.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Column(
                                modifier = Modifier.wrapContentWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Price",
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                    color = Color.LightGray,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                val price = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = Color.Blue,
                                            fontSize = 18.sp,
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
                                        append(products.price.toString())
                                    }
                                }
                                Text(
                                    text = price,
                                    modifier = Modifier.padding(start =10.dp )
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))
                            FilledIconButton(
                                onClick = {},
                                modifier = Modifier
                                    .fillMaxWidth(.5f)
                                    .height(55.dp)
                                    .padding(top = 4.dp),
                                enabled = true,
                                shape = RoundedCornerShape(24.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.Blue,
                                    contentColor = Color.White
                                )
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.ShoppingBag,
                                        tint = Color.White,
                                        contentDescription = "Shopping Bag"
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "Add to Cart",
                                        color = Color.White
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
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

@Composable
fun ExpandableDescription(description: String) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
            .animateContentSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Description",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 6.dp, start = 12.dp)
        )

        val descriptionThreshold = 100
        val isLongDescription = description.length > descriptionThreshold

        val truncatedDescription = if (!expanded) {
            description.take(descriptionThreshold) + "..."
        } else {
            description
        }

        val clickableText = buildAnnotatedString {
            append(truncatedDescription)

            if (isLongDescription) {
                append(if (expanded) " Read Less" else " Read More")
            }
        }

        Text(
            text = clickableText,
            fontSize = 16.sp,
            maxLines = Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 6.dp, start = 12.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(color = Color.Transparent),
                    enabled = true,
                    role = Role.Button,
                    onClick = {
                        setExpanded(!expanded)
                    }
                )
        )
    }
}