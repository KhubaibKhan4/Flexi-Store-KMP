package org.flexi.app

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import org.flexi.app.presentation.ui.screens.home.HomeScreen
import org.flexi.app.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    Navigator(HomeScreen())
}

internal expect fun openUrl(url: String?)