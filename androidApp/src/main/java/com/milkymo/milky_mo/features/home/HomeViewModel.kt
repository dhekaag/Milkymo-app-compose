package com.milkymo.milky_mo.features.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bloco.core.commons.Log
import io.bloco.core.commons.logi
import io.bloco.core.domain.LocalDatabase
import io.bloco.core.domain.models.AuthData
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localDatabase: LocalDatabase
) : ViewModel(){
    
    
    var _accessToken = mutableStateOf("")
    private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Initiate)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            logi("homeviewmodel has been initialized")
            localDatabase.getAccessToken().map { token ->
                _accessToken.value = token
                logi("token = $token")
            }
        }
    }

    fun logout() {
        viewModelScope.launch{
        localDatabase.deleteAllUserData()
            delay(500.milliseconds)
            _state.value = HomeUiState.Logout
        logi("user logout and delete all user data")
    }
    }

    fun getUserData() = viewModelScope.launch {
        val accessTokenDeferred = async { localDatabase.getAccessToken().first() }
        val refreshTokenDeferred = async { localDatabase.getRefreshToken().first() }
        val userLoggedInDeferred = async { localDatabase.isUserLoggedIn().first() }

        val (accessToken, refreshToken, userLoggedIn) = awaitAll(
            accessTokenDeferred,
            refreshTokenDeferred,
            userLoggedInDeferred
        )

        logi("Access Token: $accessToken")
        logi("Refresh Token: $refreshToken")
        logi("User Logged In: $userLoggedIn")
    }

    sealed interface HomeUiState {
        object Initiate : HomeUiState
        object LoadingFromAPI : HomeUiState
        data class LoginSuccess(val auth: AuthData) : HomeUiState
        data class ErrorFromAPI(val message: String?) : HomeUiState
        object Logout : HomeUiState
    }

}