package com.example.lojaonline.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionManager(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
        private val USER_ID_KEY = intPreferencesKey("user_id")
    }

    val userToken: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY]
        }

    val userID: Flow<Int?>
        get() = context.dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }

    val isLoggedIn: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY] != null && preferences[USER_ID_KEY] != null
        }


    suspend fun saveUserToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    suspend fun saveUserId(userId: Int){
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN_KEY)
            preferences.remove(USER_ID_KEY)
        }
    }
}

