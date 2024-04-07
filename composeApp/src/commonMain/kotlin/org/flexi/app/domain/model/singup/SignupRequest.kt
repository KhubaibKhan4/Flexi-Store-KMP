package org.flexi.app.domain.model.singup

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val fullName: String,
    val address: String,
    val city: String,
    val country: String,
    val phoneNumber: String,
    val userRole: String
)
