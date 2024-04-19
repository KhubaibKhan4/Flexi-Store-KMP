package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import org.flexi.app.utils.Constant
import org.jetbrains.compose.resources.Font

@Composable
fun ElectronicsList(products: List<Products>) {
    val navigator = LocalNavigator.current
    val filteredList = products.filter { it.categoryId == 22 }
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Electronics Accessories",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(Res.font.Roboto_Bold))
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "See All",
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                color = Color(0xFFe85110),
                modifier = Modifier.clickable {
                    navigator?.push(SeeAllProducts(filteredList, "Electronics Accessories"))
                }
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(filteredList) { product ->
                ElectronicsItems(product)
            }
        }
    }

}

@Composable
fun ElectronicsItems(
    products: Products,
) {
    val navigator = LocalNavigator.current
    val image: Resource<Painter> = asyncPainterResource(Constant.BASE_URL + products.imageUrl)

    Card(
        modifier = Modifier.width(200.dp)
            .height(260.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(
            topStart = 6.dp,
            topEnd = 6.dp,
            bottomStart = 6.dp,
            bottomEnd = 52.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    navigator?.push(DetailScreen(products))
                }
        ) {
            KamelImage(
                resource = image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 120.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = products.name,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black
                    )
                    Text(
                        text = products.categoryTitle,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val integerRating = products.averageRating.toInt()
                        val decimalRating = products.averageRating - integerRating

                        Icon(
                            imageVector = if (decimalRating >= 0.25f && decimalRating < 0.75f) Icons.Filled.StarHalf else Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0XFFf74623),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = products.averageRating.toString(),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }



                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "$" + products.price,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0XFFf74623)
                        )
                        Box(
                            modifier = Modifier
                                .padding(end = 12.dp, bottom = 8.dp)
                                .size(40.dp)
                                .clip(
                                    RoundedCornerShape(
                                        bottomEnd = 22.dp,
                                        topStart = 8.dp,
                                        topEnd = 8.dp,
                                        bottomStart = 8.dp
                                    )
                                )
                                .background(Color(0XFFf74623)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ShoppingBag,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
