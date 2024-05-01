package org.flexi.app.presentation.ui.navigation.tabs.favourite

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.flexi.app.presentation.ui.screens.favourite.FavouriteScreen

object FavouriteTab: Tab {
    @Composable
    override fun Content() {
        Navigator(FavouriteScreen())
    }

    override val options: TabOptions
        @Composable
        get() {
            val title by remember { mutableStateOf("Favourite") }
            val icon = rememberVectorPainter(Icons.Default.Favorite)
            val index: UShort = 3u
            return TabOptions(index, title, icon)
        }
}