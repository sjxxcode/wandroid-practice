package com.example.wanandroidpractice.framework.navigation.runtime

import com.example.wanandroidpractice.framework.navigation.contract.AppNavigator
import com.example.wanandroidpractice.framework.navigation.contract.FeatureNavGraph
import org.koin.dsl.module

/**
 * App 层导航 DI 组装模块。
 *
 * 该模块把导航运行时能力（Navigator、Registry、错误处理器）
 * 与 feature 提供的 [FeatureNavGraph] 自动聚合到一起。
 */
val navigationModule = module {
    /**
     * 导航错误处理器默认实现。
     */
    single<NavigationErrorHandler> {
        AndroidLogNavigationErrorHandler()
    }

    /**
     * 导航执行器实现。
     */
    single {
        AppNavigatorImpl(errorHandler = get())
    }

    /**
     * 对外暴露抽象接口，避免业务层依赖具体实现。
     */
    single<AppNavigator> {
        get<AppNavigatorImpl>()
    }

    /**
     * 对内暴露控制器绑定接口，供 AppNavHost 管理生命周期。
     */
    single<NavControllerBinder> {
        get<AppNavigatorImpl>()
    }

    /**
     * 自动收集所有 FeatureNavGraph 并构建注册中心。
     */
    single {
        AppGraphRegistry(featureGraphs = getAll<FeatureNavGraph>())
    }
}
