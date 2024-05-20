package org.flexi.app.presentation.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.Roboto_Bold
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.flexi.app.domain.model.products.Products
import org.flexi.app.presentation.ui.screens.detail.all.SeeAllProducts
import org.flexi.app.presentation.ui.screens.detail.common.DetailScreen
import org.flexi.app.theme.LocalThemeIsDark
import org.flexi.app.utils.Constant
import org.jetbrains.compose.resources.Font

@Composable
fun FurnituresList(products: List<Products>) {
    val isDark by LocalThemeIsDark.current
    val navigator = LocalNavigator.current
    val filteredList = products.filter { it.categoryId == 13 }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .padding(horizontal = 0.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Furniture & Decor",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color =if (isDark) Color.White else Color.Black,
                modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = "See All",
                fontSize = 16.sp,
                color = Color(0xFFe85110),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        navigator?.push(SeeAllProducts(filteredList, books = null, "Furniture & Decor"))
                    }
                    .padding(end = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(filteredList) { product ->
                FurnitureItems(product)
            }
        }
    }
}



@Composable
fun FurnitureItems(
    products: Products,
) {
    val navigator = LocalNavigator.current
    val isFav by remember { mutableStateOf(false) }
    val image: Resource<Painter> = asyncPainterResource(Constant.BASE_URL + products.imageUrl)

    Box(
        modifier = Modifier
            .width(150.dp)
            .wrapContentHeight(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
        ) {
            BoxWithConstraints {
                val cardHeight = constraints.maxHeight - 60
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight.dp)
                        .clickable {
                            navigator?.push(DetailScreen(products))
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.LightGray
                    ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        KamelImage(
                            resource = image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                                .height(150.dp),
                            contentScale = ContentScale.Crop,
                            animationSpec = tween(
                                durationMillis = 200,
                                delayMillis = 100,
                                easing = LinearOutSlowInEasing
                            )
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 4.dp, end = 2.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = products.name,
                                textAlign = TextAlign.Start,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = products.brand,
                                textAlign = TextAlign.Start,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = Color.Gray,
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "$" + products.price,
                                color = Color.Blue,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(start = 64.dp)
                .offset(y = 10.dp, x = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Blue)
                    .padding(4.dp)
            )
        }
    }
}