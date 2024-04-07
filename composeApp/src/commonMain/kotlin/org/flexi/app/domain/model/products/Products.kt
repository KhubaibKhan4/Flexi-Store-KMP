package org.flexi.app.domain.model.products


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Products(
    @SerialName("averageRating")
    val averageRating: Double = 0.0,
    @SerialName("brand")
    val brand: String = "",
    @SerialName("categoryId")
    val categoryId: Int = 0,
    @SerialName("categoryTitle")
    val categoryTitle: String = "",
    @SerialName("created_at")
    val createdAt: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("dimensions")
    val dimensions: String = "",
    @SerialName("discountPrice")
    val discountPrice: Int = 0,
    @SerialName("id")
    val id: Int = 0,
    @SerialName("imageUrl")
    val imageUrl: String = "",
    @SerialName("isAvailable")
    val isAvailable: Boolean = false,
    @SerialName("name")
    val name: String = "",
    @SerialName("price")
    val price: Int = 0,
    @SerialName("promotionDescription")
    val promotionDescription: String = "",
    @SerialName("total_stack")
    val totalStack: Int = 0,
    @SerialName("updated_at")
    val updatedAt: String = "",
    @SerialName("weight")
    val weight: Double = 0.0
)