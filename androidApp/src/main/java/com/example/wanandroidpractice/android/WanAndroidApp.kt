package com.example.wanandroidpractice.android

import android.app.Application
import com.example.wanandroidpractice.di.initKoin
import com.example.wanandroidpractice.feature.main.navigation.mainNavigationModule
import com.example.wanandroidpractice.framework.navigation.runtime.navigationModule
import org.koin.android.ext.koin.androidContext

class WanAndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@WanAndroidApp)
            modules(
                navigationModule,
                mainNavigationModule,
            )
        }
    }
}
