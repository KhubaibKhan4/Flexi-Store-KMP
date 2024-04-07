package org.flexi.app.domain.repository

import org.flexi.app.data.remote.FlexiApiClient
import org.flexi.app.data.repository.FlexiApi
import org.koin.core.annotation.Single

@Single
class Repository : FlexiApi {
    override suspend fun loginUser(email: String, password: String): String {
        return FlexiApiClient.loginUser(email, password)
    }

    override suspend fun signupUser(username: String, email: String, password: String): String {
        return FlexiApiClient.signupUser(username, email, password)
    }
}