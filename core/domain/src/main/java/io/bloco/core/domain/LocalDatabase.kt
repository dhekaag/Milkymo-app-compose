package io.bloco.core.domain

import io.bloco.core.data.database.LocalDataBase
import javax.inject.Inject

class LocalDatabase @Inject constructor(
    private val preferences: LocalDataBase
) {

    fun getAccessToken() =
        preferences.getAccessToken()

    suspend fun setAccessToken(accessToken: String) =
        preferences.setAccessToken(accessToken)

    fun getRefreshToken() =
        preferences.getRefreshToken()

    suspend fun setRefreshToken(refreshToken: String) =
        preferences.setRefreshToken(refreshToken)

    fun getIdPeternak() =
        preferences.getIdPeternak()

    suspend fun setIdPeternak(idPeternak: String) =
        preferences.setIdPeternak(idPeternak)

    fun isUserLoggedIn() =
        preferences.isUserLoggedIn()

    suspend fun setUserLoggedIn(userLoggedIn: Boolean) =
        preferences.setUserLoggedIn(userLoggedIn)

    suspend fun deleteAllUserData() =
        preferences.deleteAllUserData()
}
