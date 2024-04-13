package org.flexi.app.domain.model.promotions


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PromotionsProductsItem(
    @SerialName("description")
    val description: String = "",
    @SerialName("enabled")
    val enabled: Boolean = false,
    @SerialName("endDate")
    val endDate: Long = 0,
    @SerialName("id")
    val id: Int = 0,
    @SerialName("imageUrl")
    val imageUrl: String = "",
    @SerialName("startDate")
    val startDate: Long = 0,
    @SerialName("title")
    val title: String = ""
)