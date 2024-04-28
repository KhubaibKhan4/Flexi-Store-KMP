package org.flexi.app.presentation.ui.screens.payment.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: Long,
    val userId : Long,
    val productIds: String,
    val totalQuantity: String,
    val totalPrice: Double,
    val orderProgress: String,
    val selectedColor: String,
    val paymentType: String,
    val trackingId: String,
)
