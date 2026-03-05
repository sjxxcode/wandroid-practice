package com.example.wanandroidpractice.framework.navigation.contract

/**
 * 平台无关的导航选项描述。
 *
 * 该数据结构用于把业务层导航意图传给运行时导航实现，
 * 由具体实现（如 Android 的 `NavController`）负责映射执行。
 */
data class NavOptionsSpec(
    /**
     * 是否采用 singleTop 语义。
     *
     * 为 true 时，如果目标已经位于栈顶，将复用而不是重复入栈。
     */
    val launchSingleTop: Boolean = false,
    /**
     * 是否恢复已保存的目的地状态。
     */
    val restoreState: Boolean = false,
    /**
     * 可选的 popUpTo 路由。
     *
     * 当不为 null 时，会先回退到该路由，再执行当前导航。
     */
    val popUpToRoute: String? = null,
    /**
     * 搭配 [popUpToRoute] 使用，表示 popUpTo 的目标是否一并出栈。
     */
    val inclusive: Boolean = false,
)
