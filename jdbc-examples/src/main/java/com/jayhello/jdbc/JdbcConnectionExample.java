package com.jayhello.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC连接示例
 */
public class JdbcConnectionExample {
    
    private static final String URL = "jdbc:h2:mem:testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void main(String[] args) {
        JdbcConnectionExample example = new JdbcConnectionExample();
        try (Connection conn = example.getConnection()) {
            System.out.println("数据库连接成功！");
            System.out.println("数据库产品: " + conn.getMetaData().getDatabaseProductName());
        } catch (SQLException e) {
            System.err.println("数据库连接失败: " + e.getMessage());
        }
    }
}
