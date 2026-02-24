package com.example.wanandroidpractice.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val viewModel: GreetingViewModel = koinViewModel()
                GreetingScreen(greeting = viewModel.greetingText)
            }
        }
    }
}

@Composable
private fun GreetingScreen(greeting: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = greeting,
            fontSize = 20.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingScreenPreview() {
    MaterialTheme {
        GreetingScreen(greeting = "Hello")
    }
}
