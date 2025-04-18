package com.example.atlysimdb.ui.theme

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_prefs")

// Theme preference key
private val THEME_KEY = stringPreferencesKey("theme_preference")

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

class ThemePreference(private val context: Context) {

    val themeFlow = context.themeDataStore.data.map { preferences ->
        ThemeMode.valueOf(preferences[THEME_KEY] ?: ThemeMode.SYSTEM.name)
    }

    suspend fun setTheme(themeMode: ThemeMode) {
        context.themeDataStore.edit { preferences ->
            preferences[THEME_KEY] = themeMode.name
        }
    }
}

@Composable
fun ThemePreference.getThemeState(): ThemeMode {
    return themeFlow.collectAsState(initial = ThemeMode.SYSTEM).value
}