package org.flexi.app.data.local

import org.flexi.app.db.ProductEntity

interface DataSource {
    suspend fun getProductById(id: Int): ProductEntity?
    fun getAllProducts(): List<ProductEntity>
    suspend fun deleteProductById(id: Int)
    suspend fun insertProduct(
        id: Int?,
        averageRating: Double,
        brand: String,
        categoryId: Int,
        categoryTitle: String,
        createdAt: String,
        description: String,
        dimensions: String,
        discountPrice: Int,
        imageUrl: String,
        isAvailable: Boolean,
        name: String,
        price: Int,
        promotionDescription: String,
        totalStack: Int,
        updatedAt: String,
        weight: Double,
        isFeatured: Boolean,
        manufacturer: String,
        colors: String,
    )
}