package com.example.wanandroidpractice.framework.navigation.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.wanandroidpractice.framework.navigation.contract.AppNavigator
import org.koin.core.context.GlobalContext

/**
 * 应用唯一顶层导航宿主。
 *
 * 职责：
 * 1. 创建并持有 `NavController`
 * 2. 通过 [NavControllerBinder] 绑定导航控制器生命周期
 * 3. 通过 `AppGraphRegistry` 聚合所有 feature 子图
 */
@Composable
fun AppNavHost(
    /**
     * 根容器修饰符。
     */
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val koin = remember { GlobalContext.get() }
    val navigator = remember(koin) { koin.get<AppNavigator>() }
    val binder = remember(koin) { koin.get<NavControllerBinder>() }
    val graphRegistry = remember(koin) { koin.get<AppGraphRegistry>() }

    // 保证导航控制器绑定关系与当前 NavHost 生命周期一致。
    DisposableEffect(navController, binder) {
        binder.bind(navController)
        onDispose {
            binder.unbind(navController)
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = graphRegistry.startDestination,
    ) {
        graphRegistry.registerAll(
            builder = this,
            navigator = navigator,
        )
    }
}
