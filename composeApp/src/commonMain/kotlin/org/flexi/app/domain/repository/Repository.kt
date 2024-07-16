package org.flexi.app.domain.repository

import org.flexi.app.data.remote.FlexiApiClient
import org.flexi.app.data.repository.FlexiApi
import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.domain.model.cart.CartItem
import org.flexi.app.domain.model.category.Categories
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.flexi.app.domain.model.user.User
import org.flexi.app.presentation.ui.screens.payment.model.Order

class Repository : FlexiApi {
    override suspend fun loginUser(email: String, password: String): String {
        return FlexiApiClient.loginUser(email, password)
    }

    override suspend fun signupUser(username: String, email: String, password: String): String {
        return FlexiApiClient.signupUser(username, email, password)
    }

    override suspend fun getProducts(): List<Products> {
        return FlexiApiClient.getProducts()
    }

    override suspend fun getPromotionsProducts(): List<PromotionsProductsItem> {
        return FlexiApiClient.getPromotionsList()
    }

    override suspend fun getCategories(): List<Categories> {
        return FlexiApiClient.getCategoriesList()
    }

    override suspend fun getBooksList(): List<BooksItem> {
        return FlexiApiClient.getBooksList()
    }

    override suspend fun getCartListByUserId(id: Long): List<CartItem> {
        return FlexiApiClient.getCartListByUserId(id)
    }

    override suspend fun getProductById(id: List<Long>): List<Products>  {
        return FlexiApiClient.getProductById(id)
    }

    override suspend fun addToCart(productId: Long, quantity: Int, userId: Long): CartItem {
        return FlexiApiClient.addToCart(productId, quantity, userId)
    }

    override suspend fun getCartItem(cartId: Long): CartItem {
        return FlexiApiClient.getCartItem(cartId)
    }

    override suspend fun deleteCartItemById(cartId: Long): Boolean {
        return FlexiApiClient.deleteCartItemById(cartId)
    }

    override suspend fun getUserData(id: Int): User {
        return FlexiApiClient.getUserData(id)
    }

    override suspend fun updateUsersAddress(
        address: String,
        city: String,
        country: String,
        postalCode: Long,
    ): Boolean {
        return FlexiApiClient.updateUsersAddress(address, city, country, postalCode)
    }

    override suspend fun placeOrder(
        userId: Int,
        productIds: Int,
        totalQuantity: String,
        totalPrice: Int,
        selectedColor: String,
        paymentType: String,
    ): Order {
        return FlexiApiClient.placeOrder(userId, productIds, totalQuantity, totalPrice, selectedColor, paymentType)
    }

    override suspend fun deleteUserCart(id: Int): String {
        return FlexiApiClient.deleteUserCart(id)
    }

    override suspend fun getMyOrders(userId: Int): List<Order> {
        return FlexiApiClient.getMyOrders(userId)
    }

    override suspend fun updateCountry(usersId: Int, countryName: String): Boolean {
        return FlexiApiClient.updateCountry(usersId, countryName)
    }

    override suspend fun updateUsersDetails(
        usersId: Int,
        username: String,
        fullName: String,
        email: String,
        address: String,
        city: String,
        country: String,
        postalCode: Long,
        phoneNumber: String,
    ): Boolean {
        return FlexiApiClient.updateUsersDetails(usersId, username, fullName, email, address, city, country, postalCode, phoneNumber)
    }


}