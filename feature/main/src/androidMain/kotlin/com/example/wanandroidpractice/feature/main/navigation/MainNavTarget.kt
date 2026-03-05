package com.example.wanandroidpractice.feature.main.navigation

import com.example.wanandroidpractice.framework.navigation.contract.NavTarget
import com.example.wanandroidpractice.framework.navigation.contract.RouteNavTarget

object MainNavTarget {
    val Greeting: NavTarget = RouteNavTarget(MainFeatureRoute.GREETING)

    fun detail(greeting: String): NavTarget = RouteNavTarget(MainFeatureRoute.detail(greeting))
}
