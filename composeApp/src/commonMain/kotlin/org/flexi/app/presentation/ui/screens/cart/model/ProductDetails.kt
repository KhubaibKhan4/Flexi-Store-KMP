package org.flexi.app.presentation.ui.screens.cart.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetails(
    val imageUrl: String,
    val itemCount: Int,
    val itemPrice: Double,
    val totalPrice: Double,
    val colors: String,
    val title: String
)

