package com.example.wanandroidpractice.framework.net

/**
 * 网络环境配置。
 *
 * 当前职责：
 * 1. 统一承载网络层基础地址（baseUrl）配置。
 * 2. 作为 NetService / HttpClientFactory 的全局环境入口，便于后续扩展更多网络参数。
 *
 * 说明：
 * 1. 采用 object 形式，表示全局唯一的一份环境配置。
 * 2. `baseUrl` 允许为空；为空时，调用方可以在请求时传完整 URL。
 */
object NetEnvConfig {
    /**
     * 网络请求基础地址。
     * 例如：`https://api.example.com/`
     */
    var baseUrl: String? = null
}
