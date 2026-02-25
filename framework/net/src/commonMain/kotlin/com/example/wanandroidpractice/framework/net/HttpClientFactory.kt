package com.example.wanandroidpractice.framework.net

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * 由各平台提供底层网络引擎工厂。
 *
 * 说明：
 * 1. `commonMain` 只依赖抽象类型，不直接感知 OkHttp / Darwin 等具体实现。
 * 2. Android 与 iOS 分别在各自 sourceSet 中给出 `actual` 实现。
 */
expect fun platformHttpClientEngineFactory(): HttpClientEngineFactory<*>

/**
 * HttpClient 统一工厂。
 *
 * 作用：
 * 1. 在一个地方收敛 Ktor 的公共插件配置（序列化、超时、日志、默认请求头）。
 * 2. 避免业务层重复创建和重复配置 Client。
 * 3. 支持可选 `baseUrl`，便于同一套网络层在不同环境中复用。
 */
object HttpClientFactory {
    /**
     * 基于环境配置创建 HttpClient。
     *
     * @param envConfig 网络环境配置单例，内部读取其 `baseUrl` 进行默认请求配置。
     */
    fun create(envConfig: NetEnvConfig = NetEnvConfig): HttpClient {
        return create(baseUrl = envConfig.baseUrl)
    }

    /**
     * 创建并返回一个已完成通用初始化的 [HttpClient]。
     *
     * @param baseUrl 可选基础地址。
     * 当传入时，会作为默认请求 URL 的基础部分；调用 `getAsync/postAsync` 时传入相对路径即可。
     */
    fun create(baseUrl: String? = null): HttpClient {
        return HttpClient(platformHttpClientEngineFactory()) {
            // 统一 JSON 序列化策略：容忍服务端新增字段，减少因字段变更导致的反序列化失败。
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    },
                )
            }

            // 统一超时配置，避免请求在弱网场景下无限等待。
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 15_000
            }

            // 统一日志输出入口，开发期可快速定位请求与响应问题。
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.BODY
            }

            // 为所有请求设置默认头和默认 ContentType，减少业务调用样板代码。
            defaultRequest {
                headers.append(HttpHeaders.Accept, ContentType.Application.Json.toString())
                contentType(ContentType.Application.Json)
                if (!baseUrl.isNullOrBlank()) {
                    // 当配置了 baseUrl 时，后续请求可仅传相对 path。
                    url(baseUrl)
                }
            }
        }
    }
}
