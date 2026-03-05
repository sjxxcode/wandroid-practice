package com.example.wanandroidpractice.framework.navigation.runtime

import androidx.navigation.NavHostController

/**
 * `NavHostController` 绑定能力抽象。
 *
 * 设计目的：
 * 1. 将“控制器生命周期绑定”从 [com.example.wanandroidpractice.framework.navigation.contract.AppNavigator] 中解耦；
 * 2. 让 `AppNavHost` 仅依赖最小接口集合，避免直接依赖具体实现类；
 * 3. 支持后续替换导航执行器实现时，保持宿主层绑定逻辑稳定。
 */
internal interface NavControllerBinder {
    /**
     * 绑定当前 `NavHostController`。
     *
     * 通常在 `AppNavHost` 首次创建并进入组合时调用，
     * 用于把运行时控制器注入到导航执行器中。
     */
    fun bind(controller: NavHostController)

    /**
     * 解除当前 `NavHostController` 绑定。
     *
     * 通常在 `AppNavHost` 离开组合时调用，
     * 用于避免持有失效 controller 引用造成的内存泄漏或误导航。
     */
    fun unbind(controller: NavHostController)
}
