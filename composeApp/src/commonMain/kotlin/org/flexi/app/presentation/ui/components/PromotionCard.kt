package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.flexi.app.utils.Constant.BASE_URL

@ExperimentalFoundationApi
@Composable
fun PromotionCardWithPager(promotions: List<PromotionsProductsItem>) {
    var currentPage by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { promotions.size })

    // Observe the page changes and update the currentPage accordingly
    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp)
    ) {
        HorizontalPager(
            beyondBoundsPageCount = promotions.size,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState
        ) { page ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                val image: Resource<Painter> =
                    asyncPainterResource(data = BASE_URL + promotions[page].imageUrl)
                KamelImage(
                    resource = image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        DotsIndicator(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally),
            pageCount = promotions.size,
            currentPage = currentPage,
            onPageSelected = { page ->
                scope.launch {
                    currentPage = page
                    pagerState.scrollToPage(page)
                }
            }
        )
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int,
    onPageSelected: (Int) -> Unit,
) {
    Box(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                Dot(
                    isSelected = index == currentPage,
                    onClick = { onPageSelected(index) }
                )
            }
        }
    }
}

@Composable
fun Dot(
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val color = if (isSelected) Color.Blue else Color.LightGray
    Surface(
        modifier = Modifier.size(6.dp),
        shape = RoundedCornerShape(6.dp),
        color = color,
        onClick = onClick
    ) {}
}