package org.flexi.app.data.repository

import org.koin.core.annotation.Single

@Single
interface FlexiApi {
    suspend fun loginUser(email: String, password: String): String
}