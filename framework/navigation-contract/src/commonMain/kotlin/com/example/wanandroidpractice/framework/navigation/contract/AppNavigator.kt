package com.example.wanandroidpractice.framework.navigation.contract

/**
 * 应用级导航门面接口。
 *
 * 调用方通过该接口发起跳转/返回，不需要感知底层导航框架。
 * 该接口通常由 app 宿主层实现，并通过 DI 提供给 feature。
 */
interface AppNavigator {
    /**
     * 导航到指定目标。
     *
     * @param target 目标路由封装。
     * @param options 跳转行为选项（singleTop、popUpTo 等）。
     */
    fun navigate(
        target: NavTarget,
        options: NavOptionsSpec = NavOptionsSpec(),
    )

    /**
     * 执行返回操作。
     *
     * @return true 表示成功回退，false 表示当前无法继续回退。
     */
    fun back(): Boolean
}
