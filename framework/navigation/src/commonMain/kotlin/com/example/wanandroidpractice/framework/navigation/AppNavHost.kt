package com.example.wanandroidpractice.framework.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    startDestination: Any,
    featureNavGraphs: List<FeatureNavGraph>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navigator = remember(navController) { NavControllerAppNavigator(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        featureNavGraphs.forEach { featureNavGraph ->
            featureNavGraph.register(
                navGraphBuilder = this,
                navigator = navigator,
            )
        }
    }
}
