package org.flexi.app.presentation.ui.navigation.tabs.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.flexi.app.presentation.ui.screens.home.HomeScreen

object HomeTab: Tab {
    @Composable
    override fun Content() {
       Navigator(HomeScreen())
    }

    override val options: TabOptions
        @Composable
        get() {
            val title by remember { mutableStateOf("Home") }
            val icon = rememberVectorPainter(Icons.Default.Home)
            val index : UShort = 0u
            return TabOptions(index,title,icon)
        }
}