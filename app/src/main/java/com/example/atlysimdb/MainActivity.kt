package com.example.atlysimdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.atlysimdb.ui.navigation.MovieAppNavGraph
import com.example.atlysimdb.ui.theme.AtlysIMDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtlysIMDBTheme {
                MovieAppNavGraph()
            }
        }
    }
}