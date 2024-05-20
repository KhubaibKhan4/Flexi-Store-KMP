package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.Roboto_Bold
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.presentation.ui.screens.detail.all.SeeAllProducts
import org.flexi.app.presentation.ui.screens.detail.books.BooksDetail
import org.flexi.app.theme.LocalThemeIsDark
import org.flexi.app.utils.Constant
import org.flexi.app.utils.generateRandomColor
import org.jetbrains.compose.resources.Font

@Composable
fun BooksList(products: List<BooksItem>) {
    val navigator = LocalNavigator.current
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Books & Journals",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(Res.font.Roboto_Bold))
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "See All",
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                color = Color(0xFFe85110),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navigator?.push(SeeAllProducts(null, books = products, "Books & Journals"))
                }.padding(horizontal = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) // Add divider line
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(products) { product ->
                BooksItems(product)
            }
        }
    }
}


@Composable
fun BooksItems(
    booksItem: BooksItem,
) {
    val isDark by LocalThemeIsDark.current
    val navigator = LocalNavigator.current
    val isFav by remember { mutableStateOf(false) }
    val image: Resource<Painter> = asyncPainterResource(Constant.BASE_URL + booksItem.imageUrl)

    Column(
        modifier = Modifier.width(180.dp)
            .height(280.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    navigator?.push(BooksDetail(booksItem))
                },
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.width(160.dp)
                    .height(130.dp),
                shape = RoundedCornerShape(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = generateRandomColor()
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {}
            KamelImage(
                resource = image,
                contentDescription = null,
                modifier = Modifier
                    .width(170.dp)
                    .height(150.dp)
                    .offset(
                        x = 0.dp, y = (-30).dp
                    ),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = booksItem.title,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
            color = if (isDark) Color.White else Color.Black
        )
        Text(
            text = booksItem.author,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = Color.Gray
        )
    }
}