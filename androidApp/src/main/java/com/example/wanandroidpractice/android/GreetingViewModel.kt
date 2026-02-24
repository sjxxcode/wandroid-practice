package com.example.wanandroidpractice.android

import androidx.lifecycle.ViewModel
import com.example.wanandroidpractice.Greeting

class GreetingViewModel(
    private val greeting: Greeting,
) : ViewModel() {
    val greetingText: String = greeting.greet()
}
