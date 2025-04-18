package com.example.atlysimdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.atlysimdb.ui.navigation.MovieAppNavGraph
import com.example.atlysimdb.ui.theme.AtlysIMDBTheme
import com.example.atlysimdb.ui.theme.ThemePreference
import com.example.atlysimdb.ui.theme.getThemeState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themePreference: ThemePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeMode = themePreference.getThemeState()
            AtlysIMDBTheme(themeMode = themeMode) {
                MovieAppNavGraph(themePreference = themePreference)
            }
        }
    }
}
