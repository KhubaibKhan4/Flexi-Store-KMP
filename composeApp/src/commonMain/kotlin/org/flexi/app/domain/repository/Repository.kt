package org.flexi.app.domain.repository

import org.flexi.app.data.remote.FlexiApiClient
import org.flexi.app.data.repository.FlexiApi
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class Repository: FlexiApi {
    @Single
    override suspend fun loginUser(email: String, password: String): String {
      return FlexiApiClient.loginUser(email, password)
    }
}