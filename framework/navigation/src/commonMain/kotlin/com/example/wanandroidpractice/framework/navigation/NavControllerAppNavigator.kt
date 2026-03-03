package com.example.wanandroidpractice.framework.navigation

import androidx.navigation.NavHostController

internal class NavControllerAppNavigator(
    private val navController: NavHostController,
) : AppNavigator {
    override fun navigate(
        route: Any,
        options: AppNavOptions,
    ) {
        navController.navigate(route) {
            launchSingleTop = options.launchSingleTop

            options.popUpToRoute?.let { popUpToRoute ->
                popUpTo(popUpToRoute) {
                    inclusive = options.inclusive
                }
            }
        }
    }

    override fun popBackStack(): Boolean {
        return navController.popBackStack()
    }

    override fun navigateUp(): Boolean {
        return navController.navigateUp()
    }
}
