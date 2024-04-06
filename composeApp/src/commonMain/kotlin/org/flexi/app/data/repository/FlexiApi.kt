package org.flexi.app.data.repository

interface FlexiApi {
    suspend fun loginUser(email: String, password: String): String
}