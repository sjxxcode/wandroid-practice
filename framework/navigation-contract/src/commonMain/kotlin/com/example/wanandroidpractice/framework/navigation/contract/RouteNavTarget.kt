package com.example.wanandroidpractice.framework.navigation.contract

/**
 * [NavTarget] 的默认实现。
 *
 * 适用于无额外行为的纯路由目标场景，
 * 例如 feature 对外暴露固定页面入口。
 */
data class RouteNavTarget(
    /**
     * 目标页面路由。
     */
    override val route: String,
) : NavTarget
