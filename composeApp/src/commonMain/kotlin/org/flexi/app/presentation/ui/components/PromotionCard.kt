package org.flexi.app.presentation.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.flexi.app.utils.Constant.BASE_URL

@ExperimentalFoundationApi
@Composable
fun PromotionCardWithPager(promotions: List<PromotionsProductsItem>) {
    if (promotions.isNotEmpty()) {
        var currentPage by remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(pageCount = { promotions.size })

        val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
        val activePromotions = promotions.filter { promotion ->
            promotion.startDate <= currentTimeMillis && promotion.endDate >= currentTimeMillis && promotion.enabled
        }
        LaunchedEffect(currentPage) {
            delay(1000)
            while (true) {
                delay(2000)
                if (activePromotions.isNotEmpty()) {
                    val nextPage = (currentPage + 1) % activePromotions.size
                    scope.launch {
                        currentPage = nextPage
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp)
        ) {
            HorizontalPager(
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
                    if (activePromotions.isNotEmpty() && page in activePromotions.indices) {
                        val image: Resource<Painter> =
                            asyncPainterResource(data = BASE_URL + activePromotions[page].imageUrl)
                        KamelImage(
                            resource = image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                            animationSpec = tween(
                                durationMillis = 100,
                                delayMillis = 100,
                                easing = FastOutSlowInEasing
                            ),
                            onLoading = {
                                CircularProgressIndicator(progress = it)
                            }
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No promotions available",
                                style = TextStyle(color = Color.Gray, fontSize = 16.sp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            DotsIndicator(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterHorizontally),
                pageCount = activePromotions.size,
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
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                Dot(
                    isSelected = index == currentPage,
                    onClick = {
                        onPageSelected(index)
                    }
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
    val width by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 6.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )
    val shape = if (isSelected) CircleShape else RoundedCornerShape(50)
    val color = if (isSelected) Color.Blue else Color.LightGray

    Surface(
        modifier = Modifier.width(width)
            .height(6.dp),
        shape = shape,
        color = color,
        onClick = onClick
    ) {}
}