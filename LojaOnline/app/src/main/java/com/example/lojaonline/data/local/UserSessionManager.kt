package com.example.lojaonline.data.local

import androidx.datastore.preferences.core.edit
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_session")

class UserSessionManager(context: Context) {

    private val userTokenKey = stringPreferencesKey("USER_TOKEN")
    private val dataStore = context.dataStore

    // Save token
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[userTokenKey] = token
        }
    }

    // Retrieve token
    fun getToken(): Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[userTokenKey]
        }

    // Clear token
    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(userTokenKey)
        }
    }
}
