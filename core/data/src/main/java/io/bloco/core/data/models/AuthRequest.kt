package io.bloco.core.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    @SerialName("id_peternak")
    val id_peternak: String,
    @SerialName("password")
    val password: String
)
