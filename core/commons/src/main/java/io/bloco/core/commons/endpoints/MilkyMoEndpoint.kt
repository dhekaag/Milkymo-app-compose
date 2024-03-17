package io.bloco.core.commons.endpoints

object MilkyMoEndpoint {
    val baseUrl: String
        get() = "milkymo-backend.vercel.app"

    fun login() =
        "/users/login"

}