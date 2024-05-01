package org.flexi.app.presentation.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.flexi.app.domain.model.products.Products
import org.flexi.app.presentation.ui.screens.detail.common.DetailScreen
import org.flexi.app.utils.Constant.BASE_URL

@Composable
fun HotSaleItem(
    products: Products,
) {
    val navigator = LocalNavigator.current
    val image: Resource<Painter> =
        asyncPainterResource(data = BASE_URL + products.imageUrl)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigator?.push(DetailScreen(products))
            }
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1.5f)
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = Color.LightGray)
        ) {
            KamelImage(
                resource = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                animationSpec = tween(),
                onLoading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center)
                    )
                },
                onFailure = {
                    Text(
                        text = "Failed to load image",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center)
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = products.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$" + products.price,
            fontSize = 14.sp,
            color = Color.Gray,
            textDecoration = TextDecoration.LineThrough,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = " $" + (products.price - products.discountPrice),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "-${(products.discountPrice / products.price.toDouble() * 100).toInt()}% off",
            fontSize = 12.sp,
            color = Color.Red,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${products.averageRating}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp)
                )

            }
        }
    }
}
