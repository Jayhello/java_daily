package com.jayhello.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC增删改查示例
 * 演示使用原生JDBC进行数据库的CRUD操作
 */
public class JdbcCrudExample {

    // 使用DB_CLOSE_DELAY=-1保持内存数据库在所有连接关闭后仍然存在
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    /**
     * 获取数据库连接
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 初始化数据库表
     */
    public void initTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "username VARCHAR(50) NOT NULL, " +
                "email VARCHAR(100), " +
                "age INT" +
                ")";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("数据库表初始化成功！");
        }
    }

    /**
     * 创建用户（INSERT）
     */
    public Long createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, age) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getAge());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("创建用户失败，没有行受到影响。");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    user.setId(id);
                    return id;
                } else {
                    throw new SQLException("创建用户失败，无法获取ID。");
                }
            }
        }
    }

    /**
     * 根据ID查询用户（SELECT）
     */
    public User getUserById(Long id) throws SQLException {
        String sql = "SELECT id, username, email, age FROM users WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }

    /**
     * 查询所有用户（SELECT）
     */
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT id, username, email, age FROM users";
        List<User> users = new ArrayList<>();
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    /**
     * 更新用户信息（UPDATE）
     */
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, email = ?, age = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getAge());
            pstmt.setLong(4, user.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * 删除用户（DELETE）
     */
    public boolean deleteUser(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * 将ResultSet映射为User对象
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setAge(rs.getInt("age"));
        return user;
    }

    /**
     * 演示CRUD操作
     */
    public static void main(String[] args) {
        JdbcCrudExample example = new JdbcCrudExample();

        try {
            // 初始化表
            example.initTable();
            System.out.println("\n=== 初始化数据库表 ===");

            // 1. 创建用户（Create）
            System.out.println("\n=== 1. 创建用户 (INSERT) ===");
            User user1 = new User(null, "张三", "zhangsan@example.com", 25);
            Long id1 = example.createUser(user1);
            System.out.println("创建用户成功，ID: " + id1);

            User user2 = new User(null, "李四", "lisi@example.com", 30);
            Long id2 = example.createUser(user2);
            System.out.println("创建用户成功，ID: " + id2);

            User user3 = new User(null, "王五", "wangwu@example.com", 28);
            Long id3 = example.createUser(user3);
            System.out.println("创建用户成功，ID: " + id3);

            // 2. 查询单个用户（Read）
            System.out.println("\n=== 2. 查询单个用户 (SELECT by ID) ===");
            User foundUser = example.getUserById(id1);
            if (foundUser != null) {
                System.out.println("找到用户: " + foundUser);
            } else {
                System.out.println("未找到用户");
            }

            // 3. 查询所有用户（Read）
            System.out.println("\n=== 3. 查询所有用户 (SELECT ALL) ===");
            List<User> allUsers = example.getAllUsers();
            System.out.println("用户总数: " + allUsers.size());
            for (User user : allUsers) {
                System.out.println("  " + user);
            }

            // 4. 更新用户（Update）
            System.out.println("\n=== 4. 更新用户 (UPDATE) ===");
            user1.setUsername("张三（已更新）");
            user1.setAge(26);
            boolean updated = example.updateUser(user1);
            System.out.println("更新用户" + (updated ? "成功" : "失败"));
            
            User updatedUser = example.getUserById(id1);
            System.out.println("更新后的用户: " + updatedUser);

            // 5. 删除用户（Delete）
            System.out.println("\n=== 5. 删除用户 (DELETE) ===");
            boolean deleted = example.deleteUser(id2);
            System.out.println("删除用户" + (deleted ? "成功" : "失败"));

            // 6. 再次查询所有用户
            System.out.println("\n=== 6. 删除后查询所有用户 ===");
            List<User> remainingUsers = example.getAllUsers();
            System.out.println("剩余用户数: " + remainingUsers.size());
            for (User user : remainingUsers) {
                System.out.println("  " + user);
            }

            System.out.println("\n=== JDBC CRUD 操作演示完成！===");

        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
