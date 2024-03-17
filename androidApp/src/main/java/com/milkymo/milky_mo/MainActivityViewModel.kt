package com.milkymo.milky_mo

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bloco.core.commons.logd
import io.bloco.core.domain.AppSettingsInteractor
import com.milkymo.milky_mo.MainActivityViewModel.UiState.Loading
import com.milkymo.milky_mo.MainActivityViewModel.UiState.Success
import io.bloco.core.domain.LocalDatabase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    appSettings: AppSettingsInteractor,
    private val localDatabase: LocalDatabase
) : ViewModel() {


    var isUserAuthenticated by mutableStateOf(localDatabase.isUserLoggedIn())


//    var _accessToken by mutableStateOf("")
//    var _refreshToken by mutableStateOf("")
//    var _isLoggedIn by mutableStateOf(false)
//
//    init {
//       viewModelScope.launch {
//           val accessTokenDeferred = async { localDatabase.getAccessToken().first() }
//           val refreshTokenDeferred = async { localDatabase.getRefreshToken().first() }
//           val isLoggedInDeferred = async { localDatabase.isUserLoggedIn().first() }
//
//           _accessToken = accessTokenDeferred.await()
//           _refreshToken = refreshTokenDeferred.await()
//           _isLoggedIn = isLoggedInDeferred.await()
//       }
//    }

    val uiState: StateFlow<UiState> = appSettings.hasBeenOpened().map {
        if (!it) {
            // Here you can add new logic
            // for first time opening the app it can be useful to check if theres is existing local
            // data from previous installations, or check and download heavier resources
            appSettings.appOpened()
            logd("Opening the app for the first time")
        }
        isUserAuthenticated = localDatabase.isUserLoggedIn()
        delay(100.milliseconds)
        Success
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(200)
    )







    sealed interface UiState {
        object Loading : UiState
        object Success : UiState
    }
}
