package com.jayhello.spring.jdbc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;

/**
 * Spring JDBC 增删改查示例
 * 演示使用Spring JdbcTemplate进行数据库CRUD操作
 */
@Configuration
public class SpringJdbcCrudExample {

    /**
     * 配置数据源（使用H2内存数据库）
     * 在实际项目中，可以替换为MySQL等其他数据库
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    /**
     * 配置JdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 配置UserDao
     */
    @Bean
    public UserDao userDao(JdbcTemplate jdbcTemplate) {
        return new UserDaoImpl(jdbcTemplate);
    }

    /**
     * 初始化数据库表
     */
    public void initTable(JdbcTemplate jdbcTemplate) {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "username VARCHAR(50) NOT NULL, " +
                "email VARCHAR(100), " +
                "age INT" +
                ")";
        jdbcTemplate.execute(sql);
        System.out.println("数据库表初始化成功！");
    }

    /**
     * 演示Spring JDBC CRUD操作
     */
    public static void main(String[] args) {
        // 创建Spring容器
        try (AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(SpringJdbcCrudExample.class)) {

            // 获取Bean
            SpringJdbcCrudExample example = context.getBean(SpringJdbcCrudExample.class);
            JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
            UserDao userDao = context.getBean(UserDao.class);

            // 初始化表
            example.initTable(jdbcTemplate);
            System.out.println("\n=== 初始化数据库表 ===");

            // 1. 创建用户（Create）
            System.out.println("\n=== 1. 创建用户 (INSERT) ===");
            User user1 = new User(null, "张三", "zhangsan@example.com", 25);
            Long id1 = userDao.createUser(user1);
            System.out.println("创建用户成功，ID: " + id1);

            User user2 = new User(null, "李四", "lisi@example.com", 30);
            Long id2 = userDao.createUser(user2);
            System.out.println("创建用户成功，ID: " + id2);

            User user3 = new User(null, "王五", "wangwu@example.com", 28);
            Long id3 = userDao.createUser(user3);
            System.out.println("创建用户成功，ID: " + id3);

            // 2. 查询单个用户（Read）
            System.out.println("\n=== 2. 查询单个用户 (SELECT by ID) ===");
            User foundUser = userDao.getUserById(id1);
            if (foundUser != null) {
                System.out.println("找到用户: " + foundUser);
            } else {
                System.out.println("未找到用户");
            }

            // 3. 查询所有用户（Read）
            System.out.println("\n=== 3. 查询所有用户 (SELECT ALL) ===");
            List<User> allUsers = userDao.getAllUsers();
            System.out.println("用户总数: " + allUsers.size());
            for (User user : allUsers) {
                System.out.println("  " + user);
            }

            // 4. 统计用户数量
            System.out.println("\n=== 4. 统计用户数量 (COUNT) ===");
            int userCount = userDao.countUsers();
            System.out.println("数据库中用户总数: " + userCount);

            // 5. 更新用户（Update）
            System.out.println("\n=== 5. 更新用户 (UPDATE) ===");
            user1.setUsername("张三（已更新）");
            user1.setAge(26);
            int updatedRows = userDao.updateUser(user1);
            System.out.println("更新了 " + updatedRows + " 条记录");

            User updatedUser = userDao.getUserById(id1);
            System.out.println("更新后的用户: " + updatedUser);

            // 6. 删除用户（Delete）
            System.out.println("\n=== 6. 删除用户 (DELETE) ===");
            int deletedRows = userDao.deleteUser(id2);
            System.out.println("删除了 " + deletedRows + " 条记录");

            // 7. 再次查询所有用户
            System.out.println("\n=== 7. 删除后查询所有用户 ===");
            List<User> remainingUsers = userDao.getAllUsers();
            System.out.println("剩余用户数: " + remainingUsers.size());
            for (User user : remainingUsers) {
                System.out.println("  " + user);
            }

            System.out.println("\n=== Spring JDBC CRUD 操作演示完成！===");
            System.out.println("\n提示：");
            System.out.println("- 本示例使用H2内存数据库进行演示");
            System.out.println("- 在实际项目中，可以将数据源配置为MySQL等关系型数据库");
            System.out.println("- 配置MySQL示例：");
            System.out.println("  URL: jdbc:mysql://localhost:3306/your_database");
            System.out.println("  Driver: com.mysql.cj.jdbc.Driver");
        }
    }
}
