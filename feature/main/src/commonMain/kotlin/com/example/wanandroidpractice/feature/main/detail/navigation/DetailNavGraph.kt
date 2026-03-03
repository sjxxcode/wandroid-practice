package com.example.wanandroidpractice.feature.main.detail.navigation

import androidx.navigation.toRoute
import androidx.navigation.compose.composable
import com.example.wanandroidpractice.feature.main.detail.DetailRoute
import com.example.wanandroidpractice.framework.navigation.FeatureNavGraph

fun detailNavGraph(): FeatureNavGraph = FeatureNavGraph { navGraphBuilder, navigator ->
    navGraphBuilder.composable<DetailDestination.Route> { backStackEntry ->
        val route = backStackEntry.toRoute<DetailDestination.Route>()
        DetailRoute(
            greeting = route.greeting,
            onBack = { navigator.popBackStack() },
        )
    }
}
