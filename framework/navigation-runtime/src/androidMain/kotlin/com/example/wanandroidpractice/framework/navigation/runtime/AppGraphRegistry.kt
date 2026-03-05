package com.example.wanandroidpractice.framework.navigation.runtime

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.wanandroidpractice.framework.navigation.contract.AppNavigator
import com.example.wanandroidpractice.framework.navigation.contract.FeatureNavGraph

/**
 * 应用级导航图注册中心。
 *
 * 负责对所有 feature 子图进行：
 * 1. 排序
 * 2. 基础校验
 * 3. 统一装配到根图
 */
internal class AppGraphRegistry(
    /**
     * 由 DI 收集到的全部 feature 子图。
     */
    featureGraphs: List<FeatureNavGraph>,
) {
    /**
     * 按 [FeatureNavGraph.graphOrder] 排序后的子图列表。
     */
    private val orderedFeatureGraphs = featureGraphs.sortedBy { it.graphOrder }

    /**
     * 根导航图的起始 destination。
     *
     * 规则：取排序后第一个子图的 graphRoute。
     *
     * 说明：
     * 根图的直接子节点是各 feature 子图（通过 `navigation(route=...)` 挂载），
     * 因此根 `startDestination` 必须指向子图路由，而不是子图内部页面路由，
     * 否则会触发“destination is not a direct child of this NavGraph”异常。
     */
    val startDestination: String
        get() = orderedFeatureGraphs.first().graphRoute

    init {
        // 启动阶段即校验，避免运行期才暴露配置问题。
        require(orderedFeatureGraphs.isNotEmpty()) {
            "No FeatureNavGraph registered."
        }
        validateGraphRoutes(orderedFeatureGraphs)
    }

    /**
     * 将全部 feature 子图注册到根图。
     *
     * 每个 feature 以 `navigation(route, startDestination)` 方式挂载。
     */
    fun registerAll(
        builder: NavGraphBuilder,
        navigator: AppNavigator,
    ) {
        orderedFeatureGraphs.forEach { graph ->
            builder.navigation(
                route = graph.graphRoute,
                startDestination = graph.startDestination.route,
            ) {
                graph.register(
                    builder = this,
                    navigator = navigator,
                )
            }
        }
    }

    /**
     * 校验 graphRoute 是否重复。
     *
     * 重复会导致导航图命名空间冲突，因此直接 fail-fast。
     */
    private fun validateGraphRoutes(graphs: List<FeatureNavGraph>) {
        val duplicateRoutes = graphs.groupBy { it.graphRoute }
            .filterValues { it.size > 1 }
            .keys
        require(duplicateRoutes.isEmpty()) {
            "Duplicate graph routes detected: ${duplicateRoutes.joinToString()}"
        }
    }
}
