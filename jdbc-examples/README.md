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

### JdbcCrudExample
演示使用原生JDBC进行数据库的增删改查（CRUD）操作，包括：
- **Create** - 创建用户（INSERT）
- **Read** - 查询单个用户和所有用户（SELECT）
- **Update** - 更新用户信息（UPDATE）
- **Delete** - 删除用户（DELETE）

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.jdbc.JdbcCrudExample"
```

### HikariCPExample
演示使用HikariCP连接池管理数据库连接，包括：
- 配置和初始化连接池
- 从连接池获取和归还连接
- 测试多个并发连接
- 查看连接池统计信息

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.jdbc.HikariCPExample"
```

## 更多示例

可以在这个模块中添加更多JDBC相关的示例，例如:
- 事务管理
- 批处理
- 存储过程调用
