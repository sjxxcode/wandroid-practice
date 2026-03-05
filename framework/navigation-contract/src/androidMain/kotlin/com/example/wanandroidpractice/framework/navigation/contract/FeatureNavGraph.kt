package com.example.wanandroidpractice.framework.navigation.contract

import androidx.navigation.NavGraphBuilder

/**
 * feature 子导航图契约（Android）。
 *
 * 每个 UI 模块实现一个该接口，并在其中声明：
 * 1. 图路由命名空间
 * 2. 图起始页面
 * 3. 如何向 `NavGraphBuilder` 注册自身页面
 *
 * App 聚合层只依赖该契约来装配所有子图，不关心具体业务细节。
 */
interface FeatureNavGraph {
    /**
     * 子图路由（graph route）。
     *
     * 必须全局唯一，用于图级命名空间隔离。
     */
    val graphRoute: String

    /**
     * 子图起始目标。
     */
    val startDestination: NavTarget

    /**
     * 子图装配顺序。
     *
     * 数值越小越靠前，默认 0。
     */
    val graphOrder: Int
        get() = 0

    /**
     * 向根图注册 feature 内部页面。
     *
     * @param builder 当前可用的图构建器。
     * @param navigator 跨页面/跨模块跳转入口。
     */
    fun register(
        builder: NavGraphBuilder,
        navigator: AppNavigator,
    )
}
