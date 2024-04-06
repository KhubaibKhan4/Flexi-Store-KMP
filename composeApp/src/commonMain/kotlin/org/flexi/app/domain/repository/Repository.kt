package org.flexi.app.domain.repository

import org.flexi.app.data.remote.FlexiApiClient
import org.flexi.app.data.repository.FlexiApi

class Repository: FlexiApi {
    override suspend fun loginUser(email: String, password: String): String {
        FlexiApiClient.loginUser(email, password)
    }
}