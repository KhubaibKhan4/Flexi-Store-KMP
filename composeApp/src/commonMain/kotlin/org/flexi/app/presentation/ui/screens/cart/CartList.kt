package org.flexi.app.presentation.ui.screens.cart

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.flexi.app.domain.model.cart.CartItem

class CartList(
    private val cartItem: List<CartItem>,
) : Screen {
    @Composable
    override fun Content() {
        LazyColumn {
            items(cartItem) {
                Text(it.toString())
            }
        }
    }

}