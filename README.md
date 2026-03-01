# java_daily
日常Java实验的一些代码

## 项目结构

这是一个多模块Maven项目，包含以下子模块:

### 1. jdbc-examples - JDBC示例
包含JDBC相关的示例代码，包括数据库连接、CRUD操作、连接池使用等。

**主要依赖**: MySQL Connector, H2 Database, HikariCP

### 2. spring-examples - Spring示例
包含Spring框架相关的示例代码，包括IoC容器、依赖注入、AOP等。

**主要依赖**: Spring Context, Spring AOP, Spring JDBC

### 3. log-examples - 日志示例
包含日志相关的示例代码，演示SLF4J、Logback、Log4j2的使用。

**主要依赖**: SLF4J, Logback, Log4j2

### 4. network-examples - 网络编程示例
包含网络编程相关的示例代码，包括Socket编程、Netty、HTTP客户端等。

**主要依赖**: Netty, OkHttp, Apache HttpClient5

### 5. util-examples - 工具类和模板编程示例
包含常用工具类和模板引擎的示例代码。

**主要依赖**: Apache Commons, Guava, Freemarker, Velocity

## 构建项目

```bash
# 编译所有模块
mvn clean compile

# 打包所有模块
mvn clean package

# 运行测试
mvn test
```

## 运行示例

进入对应的子模块目录查看具体的README文件，了解如何运行各个示例。

## 技术栈

- Java 1.8 (Java 8)
- Maven 3.8.6+
- JUnit 5
- 各模块特定的依赖库

## 开发环境

建议使用以下IDE之一:
- IntelliJ IDEA
- Eclipse
- VS Code with Java Extension Pack
