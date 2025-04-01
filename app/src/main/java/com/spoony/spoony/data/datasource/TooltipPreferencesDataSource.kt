package com.spoony.spoony.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TooltipPreferencesDataSource(private val dataStore: DataStore<Preferences>) {

    val isTooltipShown: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[SHOW_REGISTER_TOOLTIP] ?: true }

    suspend fun disableTooltip() {
        dataStore.edit { preferences ->
            preferences[SHOW_REGISTER_TOOLTIP] = false
        }
    }

    companion object {
        private val SHOW_REGISTER_TOOLTIP = booleanPreferencesKey("show_register_tooltip")
    }
}
