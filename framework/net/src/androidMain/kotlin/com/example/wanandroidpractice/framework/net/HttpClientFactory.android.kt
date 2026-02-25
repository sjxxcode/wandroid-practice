package com.example.wanandroidpractice.framework.net

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

/**
 * Android 平台网络引擎实现。
 *
 * 这里返回 OkHttp 引擎，供 common 层的 HttpClientFactory 统一装配 HttpClient。
 */
actual fun platformHttpClientEngineFactory(): HttpClientEngineFactory<*> = OkHttp
