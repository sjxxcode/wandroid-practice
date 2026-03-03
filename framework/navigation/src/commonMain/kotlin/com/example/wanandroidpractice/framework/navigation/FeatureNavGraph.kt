package com.example.wanandroidpractice.framework.navigation

import androidx.navigation.NavGraphBuilder

fun interface FeatureNavGraph {
    fun register(
        navGraphBuilder: NavGraphBuilder,
        navigator: AppNavigator,
    )
}
