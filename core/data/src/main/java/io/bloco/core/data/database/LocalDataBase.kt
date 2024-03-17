package io.bloco.core.data.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.bloco.core.commons.logi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataBase @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val DATA = "Data"
        private const val IsLogin = "IsLogin"
        private const val ACCESS_TOKEN = "AccessToken"
        private const val REFRESH_TOKEN = "RefreshToken"
        private const val ID_PETERNAK = "IdPeternak"
        val accessToken = stringPreferencesKey(ACCESS_TOKEN)
        val refreshToken = stringPreferencesKey(REFRESH_TOKEN)
        val isLogin = booleanPreferencesKey(IsLogin)
        val idPeternak = stringPreferencesKey(ID_PETERNAK)
    }

    suspend fun deleteAllUserData() {
        dataStore.edit { preference ->
            preference.remove(isLogin)
            preference.remove(accessToken)
            preference.remove(refreshToken)
            logi("user data has been deleted")
        }
    }

    fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { preference ->
            preference[isLogin] ?: false
        }
    }

    suspend fun setUserLoggedIn(isUserLoggedIn: Boolean) {
        dataStore.edit { preference ->
            preference[isLogin] = isUserLoggedIn
            logi("set user logged in has been saved")
        }
    }

    fun getIdPeternak(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preference ->
            preference[idPeternak] ?: ""
        }
    }

    suspend fun setIdPeternak(userId: String) {
        dataStore.edit { preference ->
            preference[idPeternak] = userId
        }
    }

    fun getRefreshToken(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[refreshToken] ?: ""
        }
    }

    suspend fun setRefreshToken(userRefreshToken: String) {
        dataStore.edit { preference ->
            preference[refreshToken] = userRefreshToken
            logi("refresh token has been saved")
        }
    }

    fun getAccessToken(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { value: Preferences ->
            value[accessToken] ?: ""
        }
    }

    suspend fun setAccessToken(userAccessToken: String) {
        dataStore.edit { preference ->
            preference[accessToken] = userAccessToken
            logi("access token has been saved")
        }
    }
}