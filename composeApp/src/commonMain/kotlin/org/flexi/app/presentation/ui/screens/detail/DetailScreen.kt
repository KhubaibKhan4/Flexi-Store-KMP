package org.flexi.app.presentation.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
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
                        .height(350.dp),
                    contentScale = ContentScale.Crop,
                    onLoading = {
                        CircularProgressIndicator(progress = { it })
                    },
                    onFailure = {
                        Text(it.toString())
                    }
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(25.dp).clickable {
                                navigator?.pop()
                            }
                        )
                        Text(
                            text = "Detail Product",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Shopping Cart",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
                /*Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(all = 12.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier.wrapContentWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = products.name,
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row(
                                    modifier = Modifier.wrapContentWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.Top
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
                            Spacer(modifier = Modifier.weight(1f))
                            Column(
                                modifier = Modifier.wrapContentWidth(),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.wrapContentWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color.LightGray),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "-",
                                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.size(14.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                            .clickable {
                                                if (producstItems>=1){
                                                    producstItems--
                                                }else{
                                                    producstItems = 0
                                                }
                                            }
                                    )
                                    Text(
                                        text = producstItems.toString(),
                                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.size(14.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                    )
                                    Text(
                                        text = "+",
                                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.size(14.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                            .clickable {
                                                if (producstItems>=0){
                                                    producstItems++
                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }
                }*/
            }
        }
    }

}