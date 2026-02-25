package com.example.wanandroidpractice.framework.net

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * 轻量网络服务层，只负责封装最基础的 GET / POST 请求能力。
 *
 * 设计目标：
 * 1. 对上层屏蔽 Ktor 细节，统一请求入口。
 * 2. 支持注入外部 HttpClient，便于测试或外部统一管理 Client 生命周期。
 * 3. 保持最小职责，不在此处耦合业务模型、错误码映射、仓储逻辑。
 */
class NetService(
    /**
     * 可选基础地址。
     * 默认读取 [NetEnvConfig.baseUrl]，也可以在构造时显式覆盖。
     */
    baseUrl: String? = NetEnvConfig.baseUrl,
) {
    /**
     * 当前服务使用的 Client 实例。
     * `@PublishedApi` 用于保证 inline 函数访问该属性时的可见性要求。
     */
    @PublishedApi
    internal val client: HttpClient = HttpClientFactory.create(baseUrl)

    /**
     * 发送 GET 请求并将响应体反序列化为 [Response]。
     *
     * @param path 请求路径（可传相对路径或完整 URL，取决于 Client 的默认配置）。
     * @param queryParams 查询参数；value 为 null 的条目会被自动忽略。
     * @param headers 额外请求头；与默认头叠加。
     */
    suspend inline fun <reified Response : Any> getAsync(
        path: String,
        queryParams: Map<String, Any?> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
    ): Response {
        return client.get(path) {
            // 仅追加非空 query 参数，避免把 null 值传给服务端。
            queryParams.forEach { (key, value) ->
                if (value != null) {
                    parameter(key, value)
                }
            }
            // 逐个追加调用方传入的动态请求头。
            headers.forEach { (key, value) ->
                header(key, value)
            }
        }.body()
    }

    /**
     * 发送 POST 请求并将响应体反序列化为 [Response]。
     *
     * @param path 请求路径（可传相对路径或完整 URL）。
     * @param requestBody 请求体对象，会按 ContentNegotiation 配置进行序列化。
     * @param queryParams 查询参数；value 为 null 的条目会被自动忽略。
     * @param headers 额外请求头；与默认头叠加。
     */
    suspend inline fun <reified Request : Any, reified Response : Any> postAsync(
        path: String,
        requestBody: Request,
        queryParams: Map<String, Any?> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
    ): Response {
        return client.post(path) {
            // 明确声明 JSON 请求体，保证服务端按预期解析。
            contentType(ContentType.Application.Json)
            queryParams.forEach { (key, value) ->
                if (value != null) {
                    parameter(key, value)
                }
            }
            headers.forEach { (key, value) ->
                header(key, value)
            }
            // 由 Ktor 序列化插件将请求对象编码为 JSON。
            setBody(requestBody)
        }.body()
    }

    /**
     * 主动释放当前 Client 相关资源。
     *
     * 建议在不再需要该服务时调用，避免连接池与线程资源长期占用。
     */
    fun close() {
        client.close()
    }
}
