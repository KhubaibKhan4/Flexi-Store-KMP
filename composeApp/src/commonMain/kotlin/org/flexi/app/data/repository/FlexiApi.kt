package org.flexi.app.data.repository

import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.domain.model.cart.CartItem
import org.flexi.app.domain.model.category.Categories
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.flexi.app.domain.model.user.User
import org.flexi.app.presentation.ui.screens.payment.model.Order
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
    suspend fun getProductById(id: List<Long>): List<Products>
    suspend fun addToCart(productId: Long, quantity: Int, userId: Long): CartItem
    suspend fun getCartItem(cartId: Long): CartItem
    suspend fun deleteCartItemById(cartId: Long): Boolean
    suspend fun getUserData(id: Int): User
    suspend fun updateUsersAddress(
        address: String,
        city: String,
        country: String,
        postalCode: Long,
    ): Boolean

    suspend fun placeOrder(
        userId: Int,
        productIds: Int,
        totalQuantity: String,
        totalPrice: Int,
        selectedColor: String,
        paymentType: String,
    ): Order

    suspend fun deleteUserCart(id: Int): String
    suspend fun getMyOrders(userId: Int): List<Order>
    suspend fun updateCountry(
        usersId: Int,
        countryName: String,
    ): Boolean
    suspend fun updateUsersDetails(
        usersId: Int,
        username: String,
        fullName: String,
        email: String,
        address: String,
        city: String,
        country: String,
        postalCode: Long,
        phoneNumber: String
    ): Boolean
}