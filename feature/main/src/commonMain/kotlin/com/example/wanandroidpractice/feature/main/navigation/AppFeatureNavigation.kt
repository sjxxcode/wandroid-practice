package com.example.wanandroidpractice.feature.main.navigation

import com.example.wanandroidpractice.feature.main.detail.navigation.detailNavGraph
import com.example.wanandroidpractice.feature.main.greeting.navigation.GreetingDestination
import com.example.wanandroidpractice.feature.main.greeting.navigation.greetingNavGraph
import com.example.wanandroidpractice.framework.navigation.FeatureNavGraph

val appStartDestination: Any = GreetingDestination.Route

val appFeatureNavGraphs: List<FeatureNavGraph> = listOf(
    greetingNavGraph(),
    detailNavGraph(),
)
