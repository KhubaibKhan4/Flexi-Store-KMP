package org.flexi.app.presentation.ui.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.basket
import flexi_store.composeapp.generated.resources.cloth_category
import flexi_store.composeapp.generated.resources.travel
import kotlinx.coroutines.launch
import org.flexi.app.presentation.ui.screens.onboarding.model.OnBoardingItems
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen() {
    val scope = rememberCoroutineScope()
    val onBoardingData = listOf(
        OnBoardingItems(
            title = "Various Collections of The Latest Products",
            description = "Urna amet, suspenisse ullamcorper oc elit diam foclislis cursus vestibulum.",
            imageRes = painterResource(Res.drawable.basket)
        ),
        OnBoardingItems(
            title = "Discover New Features",
            description = "Eget aliquet nibh praesent tristique magna sit amet purus gravida quis blandit turpis cursus in hac.",
            imageRes = painterResource(Res.drawable.cloth_category)
        ),
        OnBoardingItems(
            title = "Easy Shopping Experience",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            imageRes = painterResource(Res.drawable.travel)
        )
    )

    var currentPage by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { onBoardingData.size })
    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(.75f)
        ) { page ->
            OnBoardingItem(
                title = onBoardingData[page].title,
                description = onBoardingData[page].description,
                imageRes = onBoardingData[page].imageRes
            )
        }
        HorizontalPagerDots(
            modifier = Modifier.padding(vertical = 12.dp),
            pageCount = onBoardingData.size,
            currentPage = currentPage,
            onPageSelected = { page ->
                currentPage = page
                scope.launch {
                    pagerState.scrollToPage(page)
                }
            }
        )
        FilledTonalButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .padding(all = 12.dp),
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = Color(0xFF5821c4),
                    contentColor = Color.White
                )
        ) {
            Text(
                text = "Create Account"
            )
        }
        Text(
            text = "Already Have Account",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            textAlign = TextAlign.Center,
            color = Color(0xFF5821c4)
        )
    }
}


@Composable
fun OnBoardingItem(
    title: String,
    description: String,
    imageRes: Painter,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(all = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = imageRes,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(.95f)
                .clip(RoundedCornerShape(24.dp)),
        )
        Text(
            text = title,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(all = 10.dp)
        )
        Text(
            text = description,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(all = 10.dp)
        )
    }
}

@Composable
fun HorizontalPagerDots(
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
    val color = if (isSelected) Color.Blue else Color.LightGray
    Surface(
        modifier = Modifier.size(6.dp),
        shape = CircleShape,
        color = color,
        onClick = onClick
    ) {}
}