package io.bloco.core.domain.models

import io.bloco.core.data.models.AuthDataDto
import io.bloco.core.data.models.AuthSessionDto

data class AuthSession(
    val status: Boolean,
    val statusCode: Long,
    val message: String,
    val data: AuthDataDto
)

data class AuthData (
    val accessToken: String,
    val refreshToken: String
)

fun AuthSessionDto.toModel() : AuthSession = AuthSession(
    status = status,
    statusCode = statusCode,
    message = message,
    data = data
)
fun AuthDataDto.toModel() : AuthData = AuthData(
    accessToken = accessToken,
    refreshToken = refreshToken
)
