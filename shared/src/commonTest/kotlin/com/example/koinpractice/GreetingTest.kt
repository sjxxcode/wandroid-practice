package com.example.koinpractice

import kotlin.test.Test
import kotlin.test.assertTrue

class GreetingTest {
    @Test
    fun greetContainsHello() {
        assertTrue(Greeting().greet().contains("Hello"))
    }
}
