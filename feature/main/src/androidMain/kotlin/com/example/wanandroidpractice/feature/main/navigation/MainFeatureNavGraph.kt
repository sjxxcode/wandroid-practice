package com.example.wanandroidpractice.feature.main.navigation

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wanandroidpractice.feature.main.detail.DetailRoute
import com.example.wanandroidpractice.feature.main.greeting.GreetingRoute
import com.example.wanandroidpractice.framework.navigation.contract.AppNavigator
import com.example.wanandroidpractice.framework.navigation.contract.FeatureNavGraph
import com.example.wanandroidpractice.framework.navigation.contract.NavOptionsSpec
import com.example.wanandroidpractice.framework.navigation.contract.NavTarget

class MainFeatureNavGraph : FeatureNavGraph {
    override val graphRoute: String = MainFeatureRoute.GRAPH

    override val startDestination: NavTarget = MainNavTarget.Greeting

    override fun register(
        builder: NavGraphBuilder,
        navigator: AppNavigator,
    ) {
        builder.composable(MainFeatureRoute.GREETING) {
            GreetingRoute(
                onOpenDetail = { greeting ->
                    navigator.navigate(
                        target = MainNavTarget.detail(greeting),
                        options = NavOptionsSpec(launchSingleTop = true),
                    )
                },
            )
        }

        builder.composable(
            route = MainFeatureRoute.DETAIL,
            arguments = listOf(
                navArgument(MainFeatureRoute.ARG_GREETING) {
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            val encodedGreeting =
                backStackEntry.arguments?.getString(MainFeatureRoute.ARG_GREETING).orEmpty()
            DetailRoute(
                greeting = Uri.decode(encodedGreeting),
                onBack = { navigator.back() },
            )
        }
    }
}
