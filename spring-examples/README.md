# Spring Examples

本模块包含Spring框架相关的示例代码。

## 依赖

- Spring Context
- Spring AOP
- Spring JDBC
- Spring Test (测试用)

## 示例

### SpringIoCExample
演示Spring IoC容器和依赖注入的基本用法。

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.spring.SpringIoCExample"
```

### SpringJdbcCrudExample
演示使用Spring JdbcTemplate进行数据库的增删改查（CRUD）操作，包括：
- **Create** - 创建用户（INSERT）
- **Read** - 查询单个用户和所有用户（SELECT）
- **Update** - 更新用户信息（UPDATE）
- **Delete** - 删除用户（DELETE）
- **Count** - 统计用户数量

本示例使用H2内存数据库进行演示，在实际项目中可以替换为MySQL等关系型数据库。

示例包含：
- `User.java` - 用户实体类
- `UserDao.java` - 用户数据访问对象接口
- `UserDaoImpl.java` - 使用Spring JdbcTemplate实现的DAO
- `SpringJdbcCrudExample.java` - Spring配置和演示主类

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.spring.jdbc.SpringJdbcCrudExample"
```

## 更多示例

可以在这个模块中添加更多Spring相关的示例，例如:
- AOP切面编程
- Bean生命周期
- 事件发布与监听
- 事务管理
- 配置管理
