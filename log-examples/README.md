# Log Examples

本模块包含日志相关的示例代码。

## 依赖

- SLF4J API
- Logback (主要日志框架)
- Log4j2 (可选的日志框架)

## 示例

### LogbackExample
演示使用SLF4J + Logback进行日志记录的各个级别。

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.log.LogbackExample"
```

## 配置文件

- `logback.xml`: Logback的配置文件，配置了控制台和文件输出

## 更多示例

可以在这个模块中添加更多日志相关的示例，例如:
- 日志级别动态调整
- 异步日志
- MDC (Mapped Diagnostic Context)
- 日志过滤
- 自定义Appender
