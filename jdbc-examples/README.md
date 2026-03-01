# JDBC Examples

本模块包含JDBC相关的示例代码。

## 依赖

- MySQL Connector (mysql-connector-j 8.1.0)
- H2 Database (用于测试)
- HikariCP 连接池

## 示例

### JdbcConnectionExample
演示基本的JDBC数据库连接。

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.jdbc.JdbcConnectionExample"
```

## 更多示例

可以在这个模块中添加更多JDBC相关的示例，例如:
- CRUD操作
- 事务管理
- 批处理
- 存储过程调用
- 连接池使用
