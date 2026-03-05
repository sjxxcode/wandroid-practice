package com.example.wanandroidpractice.framework.navigation.contract

/**
 * 统一的导航目标抽象。
 *
 * 该接口用于在业务层/模块层表达“我要跳去哪里”，
 * 而不是直接依赖 Android `NavController`。
 * 这样可以保证导航调用方只依赖契约，不依赖具体实现细节。
 */
interface NavTarget {
    /**
     * 目标路由字符串。
     *
     * 路由应遵循模块命名空间约定，例如：
     * `chat/home`、`chat/settings`。
     */
    val route: String
}
