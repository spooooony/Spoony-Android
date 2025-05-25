package com.spoony.spoony.data.datasourceimpl.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.spoony.spoony.data.datasource.local.RecentSearchDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecentSearchDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : RecentSearchDataSource {

    override suspend fun getRecentReviewQueries(): Flow<List<String>> = dataStore.data
        .map { preferences ->
            preferences[REVIEW_QUERY_KEY]?.toList() ?: emptyList()
        }

    override suspend fun getRecentUserQueries(): Flow<List<String>> = dataStore.data
        .map { preferences ->
            preferences[USER_QUERY_KEY]?.toList() ?: emptyList()
        }

    override suspend fun setUserQueries(list: List<String>) {
        dataStore.edit { prefs ->
            prefs[USER_QUERY_KEY] = list.take(6).toSet()
        }
    }

    override suspend fun setReviewQueries(list: List<String>) {
        dataStore.edit { prefs ->
            prefs[REVIEW_QUERY_KEY] = list.take(6).toSet()
        }
    }

    override suspend fun clearReviewQueries() {
        dataStore.edit { it[REVIEW_QUERY_KEY] = emptySet() }
    }

    override suspend fun clearUserQueries() {
        dataStore.edit { it[USER_QUERY_KEY] = emptySet() }
    }

    companion object {
        private val REVIEW_QUERY_KEY = stringSetPreferencesKey("recent_review_queries")
        private val USER_QUERY_KEY = stringSetPreferencesKey("recent_user_queries")
    }
}
