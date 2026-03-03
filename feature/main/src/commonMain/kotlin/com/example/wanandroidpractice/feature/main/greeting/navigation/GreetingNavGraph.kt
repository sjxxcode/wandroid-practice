package com.example.wanandroidpractice.feature.main.greeting.navigation

import androidx.navigation.compose.composable
import com.example.wanandroidpractice.feature.main.detail.navigation.DetailDestination
import com.example.wanandroidpractice.feature.main.greeting.GreetingRoute
import com.example.wanandroidpractice.framework.navigation.FeatureNavGraph

fun greetingNavGraph(): FeatureNavGraph = FeatureNavGraph { navGraphBuilder, navigator ->
    navGraphBuilder.composable<GreetingDestination.Route> {
        GreetingRoute(
            onOpenDetail = { greeting ->
                navigator.navigateSingleTop(DetailDestination.Route(greeting))
            },
        )
    }
}
