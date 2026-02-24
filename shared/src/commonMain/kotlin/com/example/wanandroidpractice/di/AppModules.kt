package com.example.wanandroidpractice.di

import com.example.wanandroidpractice.Greeting
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val commonModule = module {
    singleOf(::Greeting)
}

val appModules = listOf(commonModule, platformModule)
