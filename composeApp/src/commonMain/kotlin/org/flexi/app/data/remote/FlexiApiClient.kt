package org.flexi.app.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.domain.model.cart.CartItem
import org.flexi.app.domain.model.category.Categories
import org.flexi.app.domain.model.products.Products
import org.flexi.app.domain.model.promotions.PromotionsProductsItem
import org.flexi.app.utils.Constant.BASE_URL
import org.flexi.app.utils.Constant.TIME_OUT
import org.koin.core.annotation.Single

@Single
object FlexiApiClient {
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

    suspend fun getProductById(id: List<Long>): List<Products>? {
        return client.get(BASE_URL + "v1/products/userId/$id").body()
    }
}