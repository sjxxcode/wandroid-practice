# Navigation Module 设计说明

本文档说明当前工程的导航架构落地方案：**基础设施集中 + Feature Graph 分散**。

## 1. 目标与原则

### 1.1 目标
- 继续使用单 `Activity` + Compose。
- 在 `framework/navigation`（KMP 模块）集中维护导航基础设施。
- 每个业务模块（当前是 `feature/main`）维护自己的导航图定义。
- `shared` 保持平台无关，不引入 Android `NavController` 依赖。

### 1.2 核心原则
- `framework/navigation` 只提供导航能力，不承载具体业务页面。
- Feature 自己声明 route、参数解析、目的地注册。
- 应用入口只做“组装”，不写细节路由逻辑。

## 2. 模块职责

### 2.1 `framework/navigation`
- `commonMain`
  - `AppNavigator.kt`：平台无关导航动作抽象与 `AppNavOptions`。
  - `FeatureNavGraph.kt`：统一 Feature 图注册契约。
  - `NavControllerAppNavigator.kt`：`NavHostController` 到 `AppNavigator` 的桥接实现。
  - `AppNavHost.kt`：统一 `NavHost` 搭建与 graph 注册。
- `commonMain` 依赖 CMP 导航库 `org.jetbrains.androidx.navigation:navigation-compose`，不再使用 Android-only 的 `androidx.navigation` 依赖坐标。
- `androidMain` 仅保留 Android Manifest 和 Android library 配置。

### 2.2 `feature/main`
- 提供页面实现（`GreetingRoute`、`DetailRoute`）。
- 维护 destination + nav graph。
- 在 `navigation/AppFeatureNavigation.kt` 组装 feature graph 列表和起始路由。

### 2.3 `androidApp`
- 仅作为 Android 宿主入口。
- 在 `MainActivity` 中调用 `AppNavHost`，并从 `feature/main` 读取路由组装结果。
- 在 `Application` 中做平台侧初始化（如 Koin `androidContext`）。

## 3. 当前代码结构

```text
framework/navigation/
  src/commonMain/kotlin/com/example/wanandroidpractice/framework/navigation/
    AppNavHost.kt
    AppNavigator.kt
    FeatureNavGraph.kt
    NavControllerAppNavigator.kt
  src/androidMain/
    AndroidManifest.xml

feature/main/src/commonMain/kotlin/com/example/wanandroidpractice/feature/main/
  navigation/
    AppFeatureNavigation.kt
  greeting/
    GreetingScreen.kt
    navigation/
      GreetingDestination.kt
      GreetingNavGraph.kt
  detail/
    DetailScreen.kt
    navigation/
      DetailDestination.kt
      DetailNavGraph.kt

androidApp/src/main/java/com/example/wanandroidpractice/android/
  MainActivity.kt
  WanAndroidApp.kt
```

## 4. 架构流程

1. `androidApp/MainActivity` 调用 `AppNavHost(startDestination, featureNavGraphs)`。
2. `AppNavHost` 创建 `NavController` 和 `AppNavigator`。
3. `AppNavHost` 遍历 `featureNavGraphs`，逐个注册到 `NavGraphBuilder`。
4. 每个 feature graph 通过 `composable(...)` 注册自己的页面和参数。
5. 页面内通过 `AppNavigator` 执行跳转/返回。

## 5. 为什么不是“全量集中式导航图”

如果把所有 feature 路由都放在 `framework/navigation`：
- 导航模块会快速膨胀成巨型模块。
- 每次新增 feature 都要改核心模块，冲突和耦合上升。
- 导航层需要依赖所有业务页面，失去模块边界。

当前方案将“基础设施”和“业务图”解耦，能够兼顾统一性与扩展性。

## 5.1 为什么先迁移到 `commonMain` 实现

- 导航基础设施需要跟工程模块体系保持一致，因此模块本身采用 KMP。
- 当前已经把导航契约和 `AppNavHost` 迁到 `commonMain`，这样后续接入 CMP UI 复用时不需要再改导航模块结构。
- 依赖层已从 Android-only 坐标切到 CMP 导航坐标，iOS 目标可以直接参与编译验证。
- 目前 `androidApp` 依然是宿主入口；未来将 feature UI 挪到 KMP UI 模块时，导航层可以保持稳定。

## 6. 新增一个 Feature 模块时怎么做

以新增 `profile` 功能为例：

1. 在 feature 模块内定义 destination。
2. 在 feature 模块内实现 `profileNavGraph(): FeatureNavGraph`。
3. 在应用组装层（当前是 `feature/main` 的 `AppFeatureNavigation.kt`）把 `profileNavGraph()` 加入列表。
4. 如需参数，使用 route pattern + `navArgument` 声明并在页面入口解析。

注意：
- 新功能的路由定义不放到其他 feature 目录下。
- `framework/navigation` 一般不需要改动，除非需要新增全局导航基础能力。

## 7. 路由与参数约定

- 每个 feature 维护本 feature 的 `Destination` 对象。
- 采用 typed route：在 `Destination` 内定义 `@Serializable` 的 `Route` 类型。
- 注册目的地时使用 `composable<RouteType> { ... }`。
- 跳转时通过 `navigator.navigateSingleTop(RouteType(...))` 传参。
- 读取参数时使用 `backStackEntry.toRoute<RouteType>()`，避免手写字符串 key 与平台 API 差异。

## 8. 与 ViewModel/DI 的边界

- ViewModel 不直接持有 `NavController`。
- 页面层负责触发导航动作（通过 `AppNavigator`）。
- 业务逻辑与导航实现保持解耦，便于测试和后续多平台迁移。

## 9. 后续演进建议

- 当 feature 数量增加时，可增加“导航组装模块”专门负责 graph 聚合，减少 `androidApp` 主入口职责。
- 若后续进入 Compose Multiplatform UI 共享阶段，可保留本方案思想，替换具体平台导航实现层。
