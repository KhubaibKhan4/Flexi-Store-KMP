package org.flexi.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.flexi.app.presentation.ui.screens.auth.login.LoginScreen
import org.flexi.app.presentation.ui.screens.navigation.rails.items.NavigationItem
import org.flexi.app.presentation.ui.screens.navigation.rails.navbar.NavigationIcon
import org.flexi.app.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    Navigator(LoginScreen())
}

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

}

internal expect fun openUrl(url: String?)