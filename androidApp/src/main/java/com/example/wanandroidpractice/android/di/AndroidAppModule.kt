package com.example.wanandroidpractice.android.di

import com.example.wanandroidpractice.android.GreetingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidAppModule = module {
    viewModel { GreetingViewModel(get()) }
}
