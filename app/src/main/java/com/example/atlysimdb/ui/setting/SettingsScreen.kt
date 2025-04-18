package com.example.atlysimdb.ui.setting

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.atlysimdb.ui.theme.CustomTheme
import com.example.atlysimdb.ui.theme.ThemeMode
import com.example.atlysimdb.ui.theme.ThemePreference
import com.example.atlysimdb.ui.theme.getThemeState
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController, themePreference: ThemePreference
) {
    val themeMode = themePreference.getThemeState()
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.Surface)
            .padding(16.dp)
    ) {
        Text(
            text = "Theme Settings",
            style = CustomTheme.typography.titleMd,
            color = CustomTheme.colors.text,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ThemeMode.entries.forEach { mode ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = themeMode == mode, onClick = {
                        coroutineScope.launch {
                            themePreference.setTheme(mode)
                            Log.d("SettingsScreen", "Theme changed to: $mode")
                        }
                    })
                Text(
                    text = when (mode) {
                        ThemeMode.LIGHT -> "Light Theme"
                        ThemeMode.DARK -> "Dark Theme"
                        ThemeMode.SYSTEM -> "System Default"
                    },
                    style = CustomTheme.typography.titleMd,
                    modifier = Modifier.padding(start = 8.dp),
                    color = CustomTheme.colors.text,
                )
            }
        }
    }
}