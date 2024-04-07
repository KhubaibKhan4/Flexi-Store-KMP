package org.flexi.app.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import org.flexi.app.domain.model.login.LoginRequest
import org.flexi.app.domain.model.singup.SignupRequest
import org.flexi.app.utils.Constant.BASE_URL
import org.flexi.app.utils.Constant.TIME_OUT
import org.koin.core.annotation.Single

@Single
object FlexiApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
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
        val loginRequest = LoginRequest(email, password)
        return client.post(url) {
            body = loginRequest
        }.body()
    }

    @OptIn(InternalAPI::class)
    suspend fun signupUser(username: String, email: String, password: String): String {
        val signupRequest = SignupRequest(
            username = username,
            email = email,
            password = password,
            fullName = null,
            address = null,
            city = null,
            country = null,
            phoneNumber = null,
            userRole = "Customer"
        )
        val url = BASE_URL + "v1/users"
        return client.post(url) {
            body = signupRequest
        }.body()
    }
}