package io.bloco.core.data.network

import io.bloco.core.commons.BackgroundDispatcher
import io.bloco.core.commons.endpoints.MilkyMoEndpoint
import io.bloco.core.commons.loge
import io.bloco.core.data.models.AuthRequest
import io.bloco.core.data.models.AuthSessionDto
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import java.net.http.HttpConnectTimeoutException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AuthService @Inject constructor(
    private val httpClient: MilkyMoHttpClient,
    @BackgroundDispatcher private val coroutineContext: CoroutineContext
) {

    suspend fun login(idPeternak: String, password: String)
    : Result<AuthSessionDto> = withContext(coroutineContext) {
        return@withContext try {
            Result.success<AuthSessionDto>(
                httpClient().post {
                    url(path = MilkyMoEndpoint.login())
                    setBody(AuthRequest(idPeternak, password))
                }.body()
            )
        } catch (e: ClientRequestException){
            loge("Failed to auth", e)
            Result.failure(Throwable("Invalid Username or password"))
        } catch (e: ServerResponseException){
            loge("Failed to auth", e)
            Result.failure(Throwable("Internal Server error, try again..."))
        } catch (e: UnknownHostException) {
            loge("Failed to auth", e)
            Result.failure(Throwable("No internet connection, try again"))
        } catch (e: HttpRequestTimeoutException){
            loge("Failed to auth", e)
            Result.failure(Throwable("Unstable network, try again..."))
        } catch (e: HttpConnectTimeoutException){
            loge("Failed to auth", e)
            Result.failure(Throwable("Unstable network, try again..."))
        } catch (e: Exception){
            loge("Failed to auth", e)
            Result.failure(Throwable("Something went wrong, try again..."))
        }
    }

}
