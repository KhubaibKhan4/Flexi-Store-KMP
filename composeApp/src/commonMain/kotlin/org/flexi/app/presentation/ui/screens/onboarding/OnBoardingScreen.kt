package org.flexi.app.presentation.ui.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.electronics
import flexi_store.composeapp.generated.resources.female_products
import flexi_store.composeapp.generated.resources.shopping
import kotlinx.coroutines.launch
import org.flexi.app.presentation.ui.screens.auth.login.LoginScreen
import org.flexi.app.presentation.ui.screens.auth.signup.SignupScreen
import org.flexi.app.presentation.ui.screens.onboarding.model.OnBoardingItems
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalFoundationApi::class)
class OnBoardingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val scope = rememberCoroutineScope()
        val onBoardingData = listOf(
            OnBoardingItems(
                title = "Shop the Latest Electronics",
                description = "Discover an extensive range of electronics, from the latest smartphones and tablets to cutting-edge gadgets, all at unbeatable prices.",
                imageRes = painterResource(Res.drawable.electronics)
            ),
            OnBoardingItems(
                title = "Explore Fashion Trends",
                description = "Stay ahead of the curve with our curated collection of fashion essentials, including clothing, accessories, and footwear from top brands.",
                imageRes = painterResource(Res.drawable.female_products)
            ),
            OnBoardingItems(
                title = "Enjoy Hassle-free Shopping",
                description = "Experience seamless shopping with our user-friendly interface, secure payment options, and fast delivery service, ensuring a stress-free shopping experience.",
                imageRes = painterResource(Res.drawable.shopping)
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
                onClick = {
                    navigator?.push(SignupScreen())
                },
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
                color = Color(0xFF5821c4),
                modifier = Modifier.clickable {
                    navigator?.push(LoginScreen())
                }
            )
        }
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
        verticalArrangement = Arrangement.Top
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