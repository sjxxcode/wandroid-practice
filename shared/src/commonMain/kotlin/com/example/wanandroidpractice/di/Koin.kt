package com.example.wanandroidpractice.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(appDeclaration: KoinApplication.() -> Unit = {}): KoinApplication {
    return startKoin {
        appDeclaration()
        modules(appModules)
    }
}
