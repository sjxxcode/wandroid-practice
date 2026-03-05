package com.example.wanandroidpractice.framework.navigation.runtime

import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.example.wanandroidpractice.framework.navigation.contract.AppNavigator
import com.example.wanandroidpractice.framework.navigation.contract.NavOptionsSpec
import com.example.wanandroidpractice.framework.navigation.contract.NavTarget

/**
 * [AppNavigator] 的 Android 实现。
 *
 * 该类负责：
 * 1. 持有并管理 `NavHostController` 绑定关系
 * 2. 将通用导航契约映射为 `NavController.navigate`
 * 3. 统一兜底导航异常
 */
internal class AppNavigatorImpl(
    /**
     * 导航异常处理器。
     */
    private val errorHandler: NavigationErrorHandler,
) : AppNavigator, NavControllerBinder {

    /**
     * 当前绑定的导航控制器。
     *
     * 仅在 `AppNavHost` 生命周期内有效。
     */
    private var navController: NavHostController? = null

    /**
     * 绑定 `NavHostController`。
     *
     * 通常在 `AppNavHost` 初始化后调用。
     */
    override fun bind(controller: NavHostController) {
        navController = controller
    }

    /**
     * 解除 `NavHostController` 绑定。
     *
     * 只会解除与入参引用相同的 controller，避免误解绑。
     */
    override fun unbind(controller: NavHostController) {
        if (navController === controller) {
            navController = null
        }
    }

    /**
     * 执行页面跳转。
     *
     * 当控制器未就绪时走告警回调；当导航执行异常时走失败回调。
     */
    override fun navigate(
        target: NavTarget,
        options: NavOptionsSpec,
    ) {
        val controller = navController
        if (controller == null) {
            errorHandler.onNavigatorNotBound(target)
            return
        }

        // 将平台无关的导航配置映射到 NavController 导航参数。
        runCatching {
            controller.navigate(target.route) {
                launchSingleTop = options.launchSingleTop
                restoreState = options.restoreState
                options.popUpToRoute?.let { route ->
                    popUpTo(route) {
                        inclusive = options.inclusive
                        saveState = options.restoreState
                    }
                }
            }
        }.onFailure { throwable ->
            errorHandler.onNavigationFailed(target, throwable)
        }
    }

    /**
     * 执行回退。
     *
     * @return 当存在可回退栈时返回 true，否则返回 false。
     */
    override fun back(): Boolean {
        val controller = navController ?: return false
        val currentDestination = controller.currentDestination ?: return false

        // 保护：当已处于应用根图的起始页面时，不再继续回退，
        // 避免 pop 到仅包含图节点（无实际 UI destination）的状态，导致白屏。
        val rootLeafStartDestinationId = findLeafStartDestinationId(controller.graph)
        if (currentDestination.id == rootLeafStartDestinationId) {
            return false
        }

        return controller.popBackStack()
    }

    /**
     * 解析 NavGraph 最终可展示页面（叶子节点）的 startDestination id。
     *
     * Navigation 允许图的 startDestination 继续指向子图，
     * 因此这里需要沿着 startDestination 链路向下解析，直到拿到非 NavGraph 的最终页面节点。
     */
    private fun findLeafStartDestinationId(graph: NavGraph): Int {
        var currentGraph: NavGraph = graph
        while (true) {
            val startId = currentGraph.startDestinationId
            val startNode = currentGraph.findNode(startId) ?: return startId
            if (startNode !is NavGraph) {
                return startNode.id
            }
            currentGraph = startNode
        }
    }
}
