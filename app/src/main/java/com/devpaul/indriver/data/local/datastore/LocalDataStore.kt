package com.devpaul.indriver.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.devpaul.indriver.core.Config.AUTH_KEY
import com.devpaul.indriver.domain.model.res.LoginResponse
import com.devpaul.indriver.domain.model.res.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class LocalDataStore(private val dataStore: DataStore<Preferences>) {

    suspend fun save(loginResponse: LoginResponse) {
        val dataStoreKey = stringPreferencesKey(AUTH_KEY)
        dataStore.edit { pref ->
            pref[dataStoreKey] = loginResponse.toJson()
        }
    }

    suspend fun update(user: User) {
        val dataStoreKey = stringPreferencesKey(AUTH_KEY)

        val authResponse = runBlocking {
            getData().first()
        }

        authResponse.user?.name = user.name
        authResponse.user?.lastname = user.lastname
        authResponse.user?.phone = user.phone
        authResponse.user?.image = user.image

        if(!user.image.isNullOrBlank()){
            authResponse.user?.image = user.image
        }

        dataStore.edit { pref ->
            pref[dataStoreKey] = authResponse.toJson()
        }
    }

    fun getData(): Flow<LoginResponse> {
        val dataStoreKey = stringPreferencesKey(AUTH_KEY)
        return dataStore.data
            .map { prefs ->
                val userData = prefs[dataStoreKey] ?: ""
                LoginResponse.fromJson(userData) ?: LoginResponse()
            }
    }

    suspend fun delete() {
        val dataStoreKey = stringPreferencesKey(AUTH_KEY)
        dataStore.edit { pref ->
            pref.remove(dataStoreKey)
        }
    }

}