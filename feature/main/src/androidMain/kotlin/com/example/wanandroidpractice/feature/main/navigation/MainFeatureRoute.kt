package com.example.wanandroidpractice.feature.main.navigation

import android.net.Uri

internal object MainFeatureRoute {
    const val GRAPH = "main"

    const val GREETING = "main/greeting"

    const val ARG_GREETING = "greeting"
    const val DETAIL = "main/detail/{$ARG_GREETING}"

    fun detail(greeting: String): String = "main/detail/${Uri.encode(greeting)}"
}
