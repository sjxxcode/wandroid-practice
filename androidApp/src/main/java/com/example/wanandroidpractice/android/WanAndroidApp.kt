package com.example.wanandroidpractice.android

import android.app.Application
import com.example.wanandroidpractice.android.di.androidAppModule
import com.example.wanandroidpractice.di.initKoin
import org.koin.android.ext.koin.androidContext

class WanAndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@WanAndroidApp)
            modules(androidAppModule)
        }
    }
}
