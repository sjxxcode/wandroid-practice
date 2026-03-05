package com.example.wanandroidpractice.feature.main.navigation

import com.example.wanandroidpractice.framework.navigation.contract.FeatureNavGraph
import org.koin.dsl.module

val mainNavigationModule = module {
    single<FeatureNavGraph> {
        MainFeatureNavGraph()
    }
}
