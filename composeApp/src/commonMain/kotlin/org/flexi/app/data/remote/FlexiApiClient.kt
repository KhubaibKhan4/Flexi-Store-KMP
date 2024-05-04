package org.flexi.app.data.remote

import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.domain.model.cart.CartItem
import org.flexi.app.domain.model.category.Categories
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.flexi.app.domain.model.user.User
import org.flexi.app.presentation.ui.screens.payment.model.Order
import org.flexi.app.utils.Constant.BASE_URL
import org.flexi.app.utils.Constant.TIME_OUT
import org.koin.core.annotation.Single

object FlexiApiClient {
    val supaBaseClient = createSupabaseClient(
        supabaseUrl = "https://flrflqyxquvzhlvfcbit.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZscmZscXl4cXV2emhsdmZjYml0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTQ3MjA3MDgsImV4cCI6MjAzMDI5NjcwOH0.HjJVA5yZdXIKHmICMxucgOJqYSz-APT_pYyEKr9FvaE"
    ){
        install(Auth)
        install(ComposeAuth){
            googleNativeLogin(serverClientId = "336413989020-si8up4sj1hjupteneur11la2p8vkai94.apps.googleusercontent.com")
        }
    }
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(contentType = ContentType.Application.Json,
                json = Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }
        install(HttpTimeout) {
            socketTimeoutMillis = TIME_OUT
            connectTimeoutMillis = TIME_OUT
            requestTimeoutMillis = TIME_OUT
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun loginUser(email: String, password: String): String {
        val url = BASE_URL + "v1/login"
        val loginRequest = Parameters.build {
            append("email", email)
            append("password", password)
        }
        return client.post(url) {
            body = FormDataContent(loginRequest)
        }.body()
    }

    @OptIn(InternalAPI::class)
    suspend fun signupUser(username: String, email: String, password: String): String {
        val formData = Parameters.build {
            append("username", username)
            append("email", email)
            append("password", password)
            append("fullName", "null")
            append("address", "null")
            append("city", "null")
            append("country", "null")
            append("phoneNumber", "null")
            append("userRole", "Customer")
        }

        val url = BASE_URL + "v1/users"
        return client.post(url) {
            body = FormDataContent(formData)
        }.body()
    }

    suspend fun getProducts(): List<Products> {
        return client.get(BASE_URL + "v1/products").body()
    }

    suspend fun getPromotionsList(): List<PromotionsProductsItem> {
        return client.get(BASE_URL + "v1/promotions").body()
    }

    suspend fun getCategoriesList(): List<Categories> {
        return client.get(BASE_URL + "v1/categories").body()
    }

    suspend fun getBooksList(): List<BooksItem> {
        return client.get(BASE_URL + "v1/books").body()
    }

    suspend fun getCartListByUserId(id: Long): List<CartItem> {
        return client.get(BASE_URL + "v1/cart/user/1").body()
    }

    suspend fun getProductById(id: List<Long>): List<Products> {
        val idString = id.joinToString(",")
        return client.get(BASE_URL + "v1/products/userId/$idString").body()
    }

    @OptIn(InternalAPI::class)
    suspend fun addToCart(productId: Long, quantity: Int, userId: Long): CartItem {
        val url = BASE_URL + "v1/cart"
        val formData = Parameters.build {
            append("productId", productId.toString())
            append("quantity", quantity.toString())
            append("userId", userId.toString())
        }
        return client.post(url) {
            body = FormDataContent(formData)
        }.body()
    }

    suspend fun getCartItem(cartId: Long): CartItem {
        return client.get(BASE_URL + "v1/cart/cartId/$cartId").body()
    }

    suspend fun deleteCartItemById(cartId: Long): Boolean {
        val url = BASE_URL + "v1/cart/item/$cartId"
        return try {
            val response = client.delete(url)
            response.status.isSuccess()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUserData(id: Int): User {
        return client.get(BASE_URL + "v1/users/$id").body()
    }

    @OptIn(InternalAPI::class)
    suspend fun updateUsersAddress(
        address: String,
        city: String,
        country: String,
        postalCode: Long,
    ): Boolean {
        val url = BASE_URL + "v1/users/address/1"
        val formData = Parameters.build {
            append("address", address)
            append("city", city)
            append("country", country)
            append("postalCode", postalCode.toString())
        }
        return client.put(url) {
            body = FormDataContent(formData)
        }.body()
    }

    @OptIn(InternalAPI::class)
    suspend fun placeOrder(
        userId: Int,
        productIds: Int,
        totalQuantity: String,
        totalPrice: Int,
        selectedColor: String,
        paymentType: String,
    ): Order {
        val url = BASE_URL + "v1/order"
        val formData = Parameters.build {
            append("userId", userId.toString())
            append("productIds", productIds.toString())
            append("totalQuantity", totalQuantity)
            append("totalPrice", totalPrice.toString())
            append("selectedColor", selectedColor)
            append("paymentType", paymentType)
        }
        return client.post(url) {
            body = FormDataContent(formData)
        }.body()
    }
    suspend fun deleteUserCart(id: Int): String {
        return client.delete(BASE_URL + "v1/cart/$id").body()
    }
    suspend fun getMyOrders(userId: Int): List<Order> {
        return client.get(BASE_URL + "v1/order/userId/$userId").body()
    }
    @OptIn(InternalAPI::class)
    suspend fun updateCountry(
        usersId: Int,
        countryName: String,
    ): Boolean {
        val url = BASE_URL + "v1/users/country/$usersId"
        val formData = Parameters.build {
            append("countryName", countryName)
        }
        return client.put(url) {
            body = FormDataContent(formData)
        }.body()
    }
    @OptIn(InternalAPI::class)
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
    ): Boolean {
        val url = BASE_URL + "v1/users/userDetail/$usersId"
        val formData = Parameters.build {
            append("username", username)
            append("fullName", fullName)
            append("email", email)
            append("address", address)
            append("city", city)
            append("country", country)
            append("postalCode", postalCode.toString())
            append("phoneNumber", phoneNumber)
        }
        return client.put(url) {
            body = FormDataContent(formData)
        }.body()
    }
}