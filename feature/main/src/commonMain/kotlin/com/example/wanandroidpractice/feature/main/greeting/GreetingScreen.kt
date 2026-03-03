package com.example.wanandroidpractice.feature.main.greeting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wanandroidpractice.Greeting

@Composable
fun GreetingRoute(
    onOpenDetail: (String) -> Unit,
) {
    GreetingScreen(
        greeting = Greeting().greet(),
        onOpenDetail = onOpenDetail,
    )
}

@Composable
private fun GreetingScreen(
    greeting: String,
    onOpenDetail: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = greeting,
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleLarge,
        )
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = { onOpenDetail(greeting) },
        ) {
            Text(text = "Open Detail")
        }
    }
}
