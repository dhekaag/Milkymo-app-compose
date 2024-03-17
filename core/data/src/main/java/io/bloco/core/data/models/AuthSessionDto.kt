package io.bloco.core.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthSessionDto (
    @SerialName("status")
    val status: Boolean,
    @SerialName("statusCode")
    val statusCode: Long,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: AuthDataDto,
)

@Serializable
data class AuthDataDto (
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
)

