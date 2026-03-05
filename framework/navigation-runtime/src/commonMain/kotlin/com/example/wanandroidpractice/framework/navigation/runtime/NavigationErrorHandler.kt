package com.example.wanandroidpractice.framework.navigation.runtime

import com.example.wanandroidpractice.framework.navigation.contract.NavTarget

/**
 * 导航异常处理器。
 *
 * 用于统一处理导航层运行时错误（未绑定控制器、跳转异常等），
 * 便于集中打点和问题排查。
 */
interface NavigationErrorHandler {
    /**
     * 导航器未绑定 `NavController` 时回调。
     *
     * 典型场景：UI 尚未进入 `AppNavHost`，但提前触发了导航请求。
     */
    fun onNavigatorNotBound(target: NavTarget)

    /**
     * 导航执行失败时回调。
     *
     * @param target 失败的目标路由。
     * @param throwable 具体异常。
     */
    fun onNavigationFailed(
        target: NavTarget,
        throwable: Throwable,
    )
}
