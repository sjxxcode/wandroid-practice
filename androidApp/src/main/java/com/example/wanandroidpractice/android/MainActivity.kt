package com.example.wanandroidpractice.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.wanandroidpractice.feature.main.navigation.appFeatureNavGraphs
import com.example.wanandroidpractice.feature.main.navigation.appStartDestination
import com.example.wanandroidpractice.framework.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavHost(
                    startDestination = appStartDestination,
                    featureNavGraphs = appFeatureNavGraphs,
                )
            }
        }
    }
}
