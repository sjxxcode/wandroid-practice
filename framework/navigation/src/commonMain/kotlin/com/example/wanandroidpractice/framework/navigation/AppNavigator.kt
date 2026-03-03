package com.example.wanandroidpractice.framework.navigation

data class AppNavOptions(
    val launchSingleTop: Boolean = false,
    val popUpToRoute: String? = null,
    val inclusive: Boolean = false,
)

interface AppNavigator {
    fun navigate(
        route: Any,
        options: AppNavOptions = AppNavOptions(),
    )

    fun navigateSingleTop(route: Any) {
        navigate(
            route = route,
            options = AppNavOptions(launchSingleTop = true),
        )
    }

    fun popBackStack(): Boolean

    fun navigateUp(): Boolean
}
