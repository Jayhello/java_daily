# Network Examples

本模块包含网络编程相关的示例代码。

## 依赖

- Netty (NIO框架)
- OkHttp (HTTP客户端)
- Apache HttpClient5

## 示例

### SimpleSocketServer
一个简单的Socket服务器示例，监听8080端口。

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.network.SimpleSocketServer"
```

### SimpleSocketClient
一个简单的Socket客户端示例（需要先启动服务器）。

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.network.SimpleSocketClient"
```

## 更多示例

可以在这个模块中添加更多网络编程相关的示例，例如:
- Netty服务器和客户端
- HTTP客户端请求
- WebSocket通信
- UDP通信
- NIO编程
- 多线程服务器
