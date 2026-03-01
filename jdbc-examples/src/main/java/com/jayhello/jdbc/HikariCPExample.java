package com.jayhello.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * HikariCP 连接池示例
 * 演示如何使用HikariCP连接池管理数据库连接
 */
public class HikariCPExample {

    private HikariDataSource dataSource;

    /**
     * 初始化HikariCP连接池
     */
    public void initDataSource() {
        HikariConfig config = new HikariConfig();
        
        // 配置数据库连接信息
        config.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        config.setUsername("sa");
        config.setPassword("");
        
        // 配置连接池参数
        config.setMaximumPoolSize(10);              // 最大连接数
        config.setMinimumIdle(2);                   // 最小空闲连接数
        config.setConnectionTimeout(30000);         // 连接超时时间（毫秒）
        config.setIdleTimeout(600000);              // 空闲连接超时时间（毫秒）
        config.setMaxLifetime(1800000);             // 连接最大存活时间（毫秒）
        
        // 配置连接池名称
        config.setPoolName("HikariCP-Pool");
        
        // 配置连接测试查询
        config.setConnectionTestQuery("SELECT 1");
        
        // 创建数据源
        dataSource = new HikariDataSource(config);
        
        System.out.println("HikariCP连接池初始化成功！");
        System.out.println("最大连接数: " + config.getMaximumPoolSize());
        System.out.println("最小空闲连接数: " + config.getMinimumIdle());
    }

    /**
     * 从连接池获取连接
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 关闭连接池
     */
    public void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("HikariCP连接池已关闭");
        }
    }

    /**
     * 初始化数据库表
     */
    public void initTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS test_table (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "data VARCHAR(100)" +
                ")";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("数据库表初始化成功！");
        }
    }

    /**
     * 测试多个连接
     */
    public void testMultipleConnections() throws SQLException {
        System.out.println("\n=== 测试多个并发连接 ===");
        
        // 获取多个连接
        Connection conn1 = getConnection();
        System.out.println("获取连接1成功");
        
        Connection conn2 = getConnection();
        System.out.println("获取连接2成功");
        
        Connection conn3 = getConnection();
        System.out.println("获取连接3成功");
        
        // 使用连接执行查询
        try (Statement stmt = conn1.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1 AS test")) {
            if (rs.next()) {
                System.out.println("连接1查询结果: " + rs.getInt("test"));
            }
        }
        
        // 关闭连接（实际上是归还到连接池）
        conn1.close();
        System.out.println("连接1已归还到连接池");
        
        conn2.close();
        System.out.println("连接2已归还到连接池");
        
        conn3.close();
        System.out.println("连接3已归还到连接池");
    }

    /**
     * 演示连接池的使用
     */
    public static void main(String[] args) {
        HikariCPExample example = new HikariCPExample();

        try {
            // 1. 初始化连接池
            System.out.println("=== 1. 初始化HikariCP连接池 ===");
            example.initDataSource();

            // 2. 初始化数据库表
            System.out.println("\n=== 2. 初始化数据库表 ===");
            example.initTable();

            // 3. 测试单个连接
            System.out.println("\n=== 3. 测试单个连接 ===");
            try (Connection conn = example.getConnection()) {
                System.out.println("连接获取成功: " + conn);
                System.out.println("连接是否关闭: " + conn.isClosed());
            }
            System.out.println("连接已归还到连接池");

            // 4. 测试多个连接
            example.testMultipleConnections();

            // 5. 显示连接池统计信息
            System.out.println("\n=== 5. 连接池统计信息 ===");
            System.out.println("总连接数: " + example.dataSource.getHikariPoolMXBean().getTotalConnections());
            System.out.println("活跃连接数: " + example.dataSource.getHikariPoolMXBean().getActiveConnections());
            System.out.println("空闲连接数: " + example.dataSource.getHikariPoolMXBean().getIdleConnections());
            System.out.println("等待连接的线程数: " + example.dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());

            System.out.println("\n=== HikariCP连接池示例演示完成！===");
            System.out.println("\n优势：");
            System.out.println("- 自动管理连接的创建和回收");
            System.out.println("- 提高数据库连接的复用率");
            System.out.println("- 避免频繁创建和关闭连接的开销");
            System.out.println("- 提供连接池监控和统计信息");

        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭连接池
            example.closeDataSource();
        }
    }
}
