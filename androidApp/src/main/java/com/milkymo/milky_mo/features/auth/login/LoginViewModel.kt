package com.milkymo.milky_mo.features.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bloco.core.commons.Log
import io.bloco.core.commons.custom.error.MilkyMoException
import io.bloco.core.commons.logi
import io.bloco.core.domain.LocalDatabase
import io.bloco.core.domain.Login
import io.bloco.core.domain.models.AuthData
import io.bloco.core.domain.models.AuthSession
import io.ktor.util.Identity.decode
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: Login,
    private val localDatabase: LocalDatabase
):ViewModel() {
    var idPeternak = mutableStateOf("")
    var password = mutableStateOf("")
    var isRememberMe = mutableStateOf(false)
    var passwordVisibility = mutableStateOf(true)
    var showDialog = mutableStateOf(false)

    private val _state = MutableStateFlow<LoginUiState>(LoginUiState.Initiate)
    val state = _state.asStateFlow()
    var isUserLoggedIn by mutableStateOf(false)
    var userLoggedMessage by mutableStateOf("")



    fun login() {
        _state.value = LoginUiState.LoadingFromAPI
        viewModelScope.launch {
            login(idPeternak = idPeternak.value, password = password.value)
                .onSuccess { response ->
                    if (response.statusCode in 200..300) {
                        _state.value = LoginUiState.LoginSuccess(response)
                        localDatabase.setUserLoggedIn(true)
                        localDatabase.setIdPeternak(idPeternak.value)
                        isUserLoggedIn = true
                    }
                }
                .onFailure { error ->
                    _state.value = LoginUiState.ErrorFromAPI(error.message)
                }
        }
    }

        fun forgotAccount() {}

        private sealed class Event {
            object Refresh : Event()
        }

        sealed interface LoginUiState {
            object Initiate : LoginUiState
            object LoadingFromAPI : LoginUiState
            data class LoginSuccess(val auth: AuthSession) : LoginUiState
            data class ErrorFromAPI(val message: String?) : LoginUiState
        }
    }


