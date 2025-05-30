package com.spoony.spoony.data.datasourceimpl.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.spoony.spoony.data.datasource.local.SpoonLocalDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SpoonLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SpoonLocalDataSource {
    override suspend fun getLastEntryDate(): Flow<String?> = dataStore.data
        .map { preferences -> preferences[LAST_ENTRY_DATE] }

    override suspend fun getIsSpoonDrawn(): Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[IS_SPOON_DRAWN] ?: false }

    override suspend fun updateLastEntryDate(date: String) {
        dataStore.edit { preferences ->
            preferences[LAST_ENTRY_DATE] = date
        }
    }

    override suspend fun updateSpoonDrawn(isSpoonDrawn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_SPOON_DRAWN] = isSpoonDrawn
        }
    }

    companion object {
        private val LAST_ENTRY_DATE = stringPreferencesKey("LAST_ENTRY_DATE")
        private val IS_SPOON_DRAWN = booleanPreferencesKey("IS_SPOON_DRAWN")
    }
}
