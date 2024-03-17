package io.bloco.core.domain

import io.bloco.core.commons.logd
import io.bloco.core.data.models.AuthSessionDto
import io.bloco.core.data.repositories.AuthRepository
import io.bloco.core.domain.models.AuthData
import io.bloco.core.domain.models.AuthSession
import io.bloco.core.domain.models.Book
import io.bloco.core.domain.models.BookDetails
import io.bloco.core.domain.models.toModel
import javax.inject.Inject

class Login @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idPeternak: String, password: String): Result<AuthSession> {
        return authRepository.login(idPeternak,password).map { auth ->
            logd("Title: " + auth.data)
            auth.toModel()
        }
    }
}