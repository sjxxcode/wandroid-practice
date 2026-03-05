package com.example.wanandroidpractice.framework.navigation.runtime

import android.util.Log
import com.example.wanandroidpractice.framework.navigation.contract.NavTarget

/**
 * 基于 Android Logcat 的导航错误处理实现。
 *
 * 当前实现以日志记录为主，后续可以在此扩展埋点上报。
 */
internal class AndroidLogNavigationErrorHandler : NavigationErrorHandler {
    /**
     * 记录“导航器未绑定”告警日志。
     */
    override fun onNavigatorNotBound(target: NavTarget) {
        Log.w(TAG, "Navigator is not bound. route=${target.route}")
    }

    /**
     * 记录导航执行失败错误日志和异常堆栈。
     */
    override fun onNavigationFailed(
        target: NavTarget,
        throwable: Throwable,
    ) {
        Log.e(TAG, "Navigation failed. route=${target.route}", throwable)
    }

    private companion object {
        private const val TAG = "AppNavigator"
    }
}
