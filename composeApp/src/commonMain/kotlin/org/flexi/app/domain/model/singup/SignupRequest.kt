package org.flexi.app.domain.model.singup

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val fullName: String? =null,
    val address: String? = null,
    val city: String? = null,
    val country: String? = null,
    val phoneNumber: String?=null,
    val userRole: String
)
