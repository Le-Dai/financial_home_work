# 金融 AI 对话助手

一个基于 Spring Boot 的金融知识问答助手，提供股票、基金、债券等金融领域的智能问答服务。

## 功能特性

- 金融知识问答：涵盖股票、基金、债券、市盈率、资产配置等主题
- 多轮对话：支持上下文历史，对话更连贯
- 现代化 UI：暗色主题聊天界面，快捷问题按钮，打字动画效果
- RESTful API：标准 JSON 接口，易于扩展

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.4.4 |
| 语言 | Java 17 |
| 数据库 | MySQL 8.x + Spring Data JPA |
| 前端 | 原生 HTML / CSS / JavaScript |
| AI 接口 | Anthropic Claude API（当前为 Mock 模式） |

## 项目结构

```
src/main/
├── java/com/example/financial/home/work/
│   ├── FinancialHomeWorkApplication.java   # 启动入口
│   ├── controller/
│   │   ├── ChatController.java             # POST /api/chat
│   │   └── UserController.java             # GET /api/users
│   ├── service/
│   │   ├── ChatService.java                # 对话逻辑（Mock / Claude API）
│   │   └── UserService.java
│   ├── repository/
│   │   └── UserRepository.java
│   ├── entity/
│   │   └── User.java
│   └── dto/
│       ├── ChatRequest.java
│       └── ChatResponse.java
└── resources/
    ├── application.properties
    └── static/
        └── index.html                      # 前端页面
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.x

### 配置数据库

在 `application.properties` 中修改数据库连接信息：

```properties
spring.datasource.url=jdbc:mysql://<host>:3306/financial?useSSL=false&serverTimezone=UTC
spring.datasource.username=<用户名>
spring.datasource.password=<密码>
```

### 启动项目

```bash
mvn spring-boot:run
```

启动后访问：[http://localhost:8080](http://localhost:8080)

## API 接口

### 发送对话消息

```
POST /api/chat
Content-Type: application/json
```

请求体：

```json
{
  "message": "什么是指数基金？",
  "history": [
    { "role": "user", "content": "你好" },
    { "role": "assistant", "content": "你好！有什么可以帮助你的？" }
  ]
}
```

响应：

```json
{
  "reply": "指数基金是跟踪特定股票指数的基金...",
  "success": true
}
```

## 接入 Claude API

当前 `ChatService` 运行在 Mock 模式（关键词匹配返回预设回答）。获取 Anthropic API Key 后，在 `application.properties` 中配置：

```properties
anthropic.api.key=sk-ant-xxxxxxxx
anthropic.model=claude-sonnet-4-6
```

然后将 `ChatService.chat()` 方法替换为真实的 Claude API 调用逻辑即可。

## 注意事项

- 本助手提供的信息**仅供参考**，不构成投资建议
- 投资有风险，请根据自身风险承受能力做决策
