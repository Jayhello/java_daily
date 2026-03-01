package com.jayhello.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理示例
 * 演示如何使用Java动态代理实现AOP功能
 */
public class ProxyExample {
    
    public static void main(String[] args) {
        System.out.println("=== 动态代理示例 ===\n");
        
        // 示例1: 基本的动态代理
        basicProxyExample();
        
        // 示例2: 带日志的代理
        loggingProxyExample();
        
        // 示例3: 性能监控代理
        performanceProxyExample();
        
        // 示例4: 事务代理
        transactionProxyExample();
    }
    
    /**
     * 示例1: 基本的动态代理
     */
    private static void basicProxyExample() {
        System.out.println("1. 基本的动态代理:");
        
        // 创建真实对象
        UserService realService = new UserServiceImpl();
        
        // 创建代理对象
        UserService proxyService = (UserService) Proxy.newProxyInstance(
            realService.getClass().getClassLoader(),
            realService.getClass().getInterfaces(),
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("代理: 调用方法 " + method.getName());
                    Object result = method.invoke(realService, args);
                    System.out.println("代理: 方法 " + method.getName() + " 执行完成");
                    return result;
                }
            }
        );
        
        // 使用代理对象
        proxyService.addUser("张三");
        System.out.println();
        
        String user = proxyService.getUser(1);
        System.out.println("返回结果: " + user);
        
        System.out.println();
    }
    
    /**
     * 示例2: 带日志的代理
     */
    private static void loggingProxyExample() {
        System.out.println("2. 带日志的代理:");
        
        UserService realService = new UserServiceImpl();
        
        // 创建日志代理
        UserService proxyService = (UserService) Proxy.newProxyInstance(
            realService.getClass().getClassLoader(),
            realService.getClass().getInterfaces(),
            new LoggingInvocationHandler(realService)
        );
        
        proxyService.addUser("李四");
        proxyService.updateUser(1, "王五");
        proxyService.deleteUser(2);
        
        System.out.println();
    }
    
    /**
     * 示例3: 性能监控代理
     */
    private static void performanceProxyExample() {
        System.out.println("3. 性能监控代理:");
        
        UserService realService = new UserServiceImpl();
        
        // 创建性能监控代理
        UserService proxyService = (UserService) Proxy.newProxyInstance(
            realService.getClass().getClassLoader(),
            realService.getClass().getInterfaces(),
            new PerformanceInvocationHandler(realService)
        );
        
        proxyService.addUser("赵六");
        proxyService.getUser(1);
        proxyService.updateUser(1, "钱七");
        
        System.out.println();
    }
    
    /**
     * 示例4: 事务代理
     */
    private static void transactionProxyExample() {
        System.out.println("4. 事务代理:");
        
        UserService realService = new UserServiceImpl();
        
        // 创建事务代理
        UserService proxyService = (UserService) Proxy.newProxyInstance(
            realService.getClass().getClassLoader(),
            realService.getClass().getInterfaces(),
            new TransactionInvocationHandler(realService)
        );
        
        try {
            proxyService.addUser("孙八");
            proxyService.updateUser(1, "周九");
        } catch (Exception e) {
            System.out.println("捕获异常: " + e.getMessage());
        }
        
        System.out.println();
    }
}

// ========== 接口和实现类 ==========

/**
 * 用户服务接口
 */
interface UserService {
    void addUser(String name);
    String getUser(int id);
    void updateUser(int id, String name);
    void deleteUser(int id);
}

/**
 * 用户服务实现类
 */
class UserServiceImpl implements UserService {
    
    @Override
    public void addUser(String name) {
        System.out.println("[真实方法] 添加用户: " + name);
        // 模拟耗时操作
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getUser(int id) {
        System.out.println("[真实方法] 获取用户ID: " + id);
        return "User-" + id;
    }
    
    @Override
    public void updateUser(int id, String name) {
        System.out.println("[真实方法] 更新用户ID: " + id + ", 新名称: " + name);
        // 模拟耗时操作
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void deleteUser(int id) {
        System.out.println("[真实方法] 删除用户ID: " + id);
    }
}

// ========== 各种InvocationHandler实现 ==========

/**
 * 日志代理处理器
 */
class LoggingInvocationHandler implements InvocationHandler {
    private Object target;
    
    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前置日志
        System.out.println("[日志] 准备执行方法: " + method.getName());
        System.out.print("[日志] 参数: ");
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if (i < args.length - 1) {
                    System.out.print(", ");
                }
            }
        }
        System.out.println();
        
        // 执行目标方法
        Object result = method.invoke(target, args);
        
        // 后置日志
        System.out.println("[日志] 方法执行完成: " + method.getName());
        if (result != null) {
            System.out.println("[日志] 返回值: " + result);
        }
        System.out.println();
        
        return result;
    }
}

/**
 * 性能监控代理处理器
 */
class PerformanceInvocationHandler implements InvocationHandler {
    private Object target;
    
    public PerformanceInvocationHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        System.out.println("[性能监控] 开始执行: " + method.getName());
        
        // 执行目标方法
        Object result = method.invoke(target, args);
        
        // 记录结束时间
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("[性能监控] 方法: " + method.getName() + 
                         " 执行时间: " + duration + "ms");
        
        // 如果执行时间超过阈值，发出警告
        if (duration > 80) {
            System.out.println("[性能监控] ⚠️  警告: 方法执行时间过长!");
        }
        
        System.out.println();
        
        return result;
    }
}

/**
 * 事务代理处理器
 */
class TransactionInvocationHandler implements InvocationHandler {
    private Object target;
    
    public TransactionInvocationHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 开启事务
        System.out.println("[事务] 开启事务");
        
        Object result = null;
        try {
            // 执行目标方法
            result = method.invoke(target, args);
            
            // 提交事务
            System.out.println("[事务] 提交事务");
            
        } catch (Exception e) {
            // 回滚事务
            System.out.println("[事务] 回滚事务");
            throw e;
        }
        
        System.out.println();
        return result;
    }
}
