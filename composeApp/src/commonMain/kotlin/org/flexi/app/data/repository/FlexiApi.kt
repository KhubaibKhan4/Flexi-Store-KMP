package org.flexi.app.data.repository

import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.domain.model.cart.CartItem
import org.flexi.app.domain.model.category.Categories
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.koin.core.annotation.Single

@Single
interface FlexiApi {
    suspend fun loginUser(email: String, password: String): String
    suspend fun signupUser(username: String, email: String, password: String): String
    suspend fun getProducts(): List<Products>
    suspend fun getPromotionsProducts(): List<PromotionsProductsItem>
    suspend fun getCategories(): List<Categories>
    suspend fun getBooksList(): List<BooksItem>
    suspend fun getCartListByUserId(id: Long): List<CartItem>
}