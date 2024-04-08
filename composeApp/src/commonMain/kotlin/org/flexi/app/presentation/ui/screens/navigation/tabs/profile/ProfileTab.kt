package org.flexi.app.presentation.ui.screens.navigation.tabs.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object ProfileTab : Tab {
    @Composable
    override fun Content() {
        Text("Profile Tab")
    }

    override val options: TabOptions
        @Composable
        get() {
            val title by remember { mutableStateOf("Profile") }
            val icon = rememberVectorPainter(Icons.Default.Person)
            val index: UShort = 4u
            return TabOptions(index, title, icon)
        }
}