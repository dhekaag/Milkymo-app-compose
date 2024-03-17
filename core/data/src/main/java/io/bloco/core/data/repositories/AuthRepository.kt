package io.bloco.core.data.repositories

import io.bloco.core.data.database.LocalDataBase
import io.bloco.core.data.models.AuthSessionDto
import io.bloco.core.data.network.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val milkyMoService: AuthService,
    private val localDatabase: LocalDataBase
) {
    suspend fun login(idPeternak: String, password: String): Result<AuthSessionDto> {
        val response = milkyMoService.login(idPeternak, password)
        if (response.isSuccess) {
            response.map { response ->
                localDatabase.setAccessToken(response.data.accessToken)
                localDatabase.setRefreshToken(response.data.refreshToken)
            }
        }
        return response
    }
}