package com.example.wanandroidpractice.feature.main.detail.navigation

import kotlinx.serialization.Serializable

object DetailDestination {
    @Serializable
    data class Route(val greeting: String)
}
