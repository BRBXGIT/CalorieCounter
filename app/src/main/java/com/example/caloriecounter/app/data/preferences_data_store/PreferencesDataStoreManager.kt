package com.example.caloriecounter.app.data.preferences_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataStoreManager(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by
        preferencesDataStore(name = "calorie_counter_preferences")

    private val darkThemeKey = booleanPreferencesKey("darkThemeEnabled")

    suspend fun storeDarkTheme(isOn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[darkThemeKey] = isOn
        }
    }

    val darkThemeStatus: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[darkThemeKey]
    }
}