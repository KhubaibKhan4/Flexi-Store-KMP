package org.flexi.app.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
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
import org.flexi.app.utils.Constant.BASE_URL
import org.jetbrains.compose.resources.Font

@Composable
fun FeaturedList(products: List<Products>) {
    val navigator = LocalNavigator.current
    val filteredList = products.filter { it.isFeatured }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Featured",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Text(
                text = "See All",
                fontSize = 14.sp,
                color = Color(0xFFe85110),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        navigator?.push(
                            SeeAllProducts(
                                filteredList,
                                books = null,
                                category = "Featured"
                            )
                        )
                    }
                    .padding(horizontal = 16.dp)
            )
        }
        LazyRow(
            contentPadding = PaddingValues(start = 8.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(filteredList) { product ->
                FeaturedItems(product)
            }
        }
    }
}

@Composable
fun FeaturedItems(
    products: Products,
) {
    val navigator = LocalNavigator.current
    val isFav by remember { mutableStateOf(false) }
    val image: Resource<Painter> = asyncPainterResource(BASE_URL + products.imageUrl)
    Card(
        modifier = Modifier.width(165.dp)
            .height(220.dp)
            .clickable {
                navigator?.push(DetailScreen(products))
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth().clickable {
            navigator?.push(DetailScreen(products))
        }) {
            KamelImage(
                resource = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)),
                contentScale = ContentScale.Crop,
                animationSpec = tween(),
                onLoading = {
                    CircularProgressIndicator()
                },
                onFailure = {
                    Text("Failed to Load")
                }
            )
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = if (isFav) Color.White else Color(0xFFe85110),
                modifier = Modifier
                    .padding(top = 6.dp, start = 8.dp)
                    .align(Alignment.TopStart)
                    .size(20.dp)
                    .clickable {
                        isFav != isFav
                    }

            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = products.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center,
                color = Color.Black,
                maxLines = 1,
                fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 3.dp, end = 3.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = products.categoryTitle,
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                fontWeight = FontWeight.Bold,
                lineHeight = 14.sp,
                color = Color(0xFFe85110),
                textAlign = TextAlign.Center,

                )

            val price = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFFe85110),
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
                    append(products.price.toString() + ".00")
                }
            }
            Text(
                text = price,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
            )
        }
    }
}