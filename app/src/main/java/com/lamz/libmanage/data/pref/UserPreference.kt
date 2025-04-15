package com.lamz.libmanage.data.pref

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        Log.d("UserPreference", "Saving session for user: ${user.user}, role: ${user.role}")
        dataStore.edit { preferences ->
            preferences[USER_KEY] = user.user
            preferences[ROLE_KEY] = user.role
            preferences[IS_LOGIN_KEY] = true
        }
        Log.d("UserPreference", "Session saved for user: ${user.user}")
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            val user = preferences[USER_KEY] ?: ""
            val role = preferences[ROLE_KEY] ?: ""
            val isLoggedIn = preferences[IS_LOGIN_KEY] ?: false

            Log.d("UserPreference", "Getting session: user=$user, role=$role, isLoggedIn=$isLoggedIn")
            UserModel(user, role, isLoggedIn)
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val USER_KEY = stringPreferencesKey("user")
        private val ROLE_KEY = stringPreferencesKey("role")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
    }
}
