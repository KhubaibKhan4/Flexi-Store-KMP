package org.flexi.app

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import kotlinx.coroutines.delay
import org.flexi.app.domain.model.version.Platform
import org.flexi.app.presentation.ui.screens.navigation.rails.items.NavigationItem
import org.flexi.app.presentation.ui.screens.navigation.rails.navbar.NavigationSideBar
import org.flexi.app.presentation.ui.screens.navigation.tabs.favourite.FavouriteTab
import org.flexi.app.presentation.ui.screens.navigation.tabs.home.HomeTab
import org.flexi.app.presentation.ui.screens.navigation.tabs.orders.MyOrders
import org.flexi.app.presentation.ui.screens.navigation.tabs.profile.ProfileTab
import org.flexi.app.presentation.ui.screens.splash.SplashScreen
import org.flexi.app.theme.AppTheme
import org.flexi.app.theme.LocalThemeIsDark

@Composable
internal fun App() = AppTheme {
    val platform = getPlatform()
    var showSplashScreen by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000)
        showSplashScreen = false
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showSplashScreen && platform != Platform.Android) {
            SplashScreen()
        } else {
            AppContent()
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppContent() {
    val items = listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Default.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        NavigationItem(
            title = "My Orders",
            selectedIcon = Icons.Default.DeliveryDining,
            unselectedIcon = Icons.Outlined.DeliveryDining,
            hasNews = true,
            badgeCount = 0
        ),
        NavigationItem(
            title = "Favourite",
            selectedIcon = Icons.Default.Favorite,
            unselectedIcon = Icons.Outlined.Favorite,
            hasNews = false,
        ),
        NavigationItem(
            title = "My Profile",
            selectedIcon = Icons.Default.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = false,
        ),
    )
    val windowClass = calculateWindowSizeClass()
    val showNavigationRail = windowClass.widthSizeClass != WindowWidthSizeClass.Compact
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    TabNavigator(HomeTab) { tabNavigator ->
        Scaffold(modifier = Modifier.fillMaxWidth(),
            bottomBar = {
                if (!showNavigationRail) {
                    BottomNavigation(
                        modifier = Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.ime),
                        backgroundColor = MaterialTheme.colorScheme.background,
                        contentColor = contentColorFor(Color.Red),
                        elevation = 8.dp
                    ) {
                        TabItem(HomeTab)
                        TabItem(MyOrders)
                        TabItem(FavouriteTab)
                        TabItem(ProfileTab)
                    }
                }
            }) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .navigationBarsPadding()
                    .padding(
                        top = it.calculateTopPadding(),
                        start = if (showNavigationRail) 80.dp else 0.dp
                    )
            ) {
                CurrentTab()
            }
        }
    }
    if (showNavigationRail) {
        NavigationSideBar(
            items = items,
            selectedItemIndex = selectedItemIndex,
            onNavigate = {
                selectedItemIndex = it
            }
        )

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(start = 80.dp)
        ) {
            when (selectedItemIndex) {
                0 -> {

                }

                1 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        TabNavigator(MyOrders)
                    }
                }

                2 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        TabNavigator(FavouriteTab)
                    }
                }

                3 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        TabNavigator(ProfileTab)
                    }
                }
            }
        }

    }
}

@Composable
fun RowScope.TabItem(tab: Tab) {
    val isDark by LocalThemeIsDark.current
    val tabNavigator = LocalTabNavigator.current
    NavigationBarItem(
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .height(58.dp).clip(RoundedCornerShape(16.dp)),
        selected = tabNavigator.current == tab,
        onClick = {
            tabNavigator.current = tab
        },
        icon = {
            tab.options.icon?.let { painter ->
                Icon(
                    painter,
                    contentDescription = tab.options.title,
                    tint = if (tabNavigator.current == tab) Color.Red else if (isDark) Color.White else Color.Black
                )
            }
        },
        label = {
            tab.options.title.let { title ->
                Text(
                    title,
                    fontSize = 12.sp,
                    color = if (tabNavigator.current == tab) Color.Red else if (isDark) Color.White else Color.Black
                )
            }
        },
        enabled = true,
        alwaysShowLabel = true,
        interactionSource = MutableInteractionSource(),
        colors = NavigationBarItemColors(
            selectedTextColor = Color.Red,
            selectedIconColor = Color.Red,
            unselectedTextColor = Color.Black,
            unselectedIconColor = Color.Black,
            selectedIndicatorColor = Color.Transparent,
            disabledIconColor = Color.Black,
            disabledTextColor = Color.Black
        )
    )
}

internal expect fun openUrl(url: String?)
expect fun getPlatform(): Platform