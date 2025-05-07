package com.spoony.spoony.data.datasourceimpl.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.spoony.spoony.data.datasource.local.TokenDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenDataSourceImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
) : TokenDataSource {
    override suspend fun getAccessToken(): Flow<String> = datastore.data
        .map { preferences -> preferences[ACCESS_TOKEN] ?: "" }

    override suspend fun getRefreshToken(): Flow<String> = datastore.data
        .map { preferences -> preferences[REFRESH_TOKEN] ?: "" }

    override suspend fun updateAccessToken(accessToken: String) {
        datastore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun updateRefreshToken(refreshToken: String) {
        datastore.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun clearTokens() {
        datastore.edit { preferences ->
            preferences[ACCESS_TOKEN] = ""
            preferences[REFRESH_TOKEN] = ""
        }
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        private val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    }
}
