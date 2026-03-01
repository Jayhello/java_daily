package com.jayhello.spring.jdbc;

import java.util.List;

/**
 * 用户数据访问对象接口
 */
public interface UserDao {
    
    /**
     * 创建用户
     */
    Long createUser(User user);
    
    /**
     * 根据ID查询用户
     */
    User getUserById(Long id);
    
    /**
     * 查询所有用户
     */
    List<User> getAllUsers();
    
    /**
     * 更新用户
     */
    int updateUser(User user);
    
    /**
     * 删除用户
     */
    int deleteUser(Long id);
    
    /**
     * 查询用户数量
     */
    int countUsers();
}
