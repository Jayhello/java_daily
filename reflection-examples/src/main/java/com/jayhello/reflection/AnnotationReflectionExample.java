package com.jayhello.reflection;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 注解反射示例
 * 演示如何通过反射处理注解
 */
public class AnnotationReflectionExample {
    
    public static void main(String[] args) {
        System.out.println("=== 注解反射示例 ===\n");
        
        // 示例1: 检查类上的注解
        checkClassAnnotations();
        
        // 示例2: 检查方法上的注解
        checkMethodAnnotations();
        
        // 示例3: 检查字段上的注解
        checkFieldAnnotations();
        
        // 示例4: 运行时注解处理（模拟简单的依赖注入）
        simpleAnnotationProcessing();
    }
    
    /**
     * 示例1: 检查类上的注解
     */
    private static void checkClassAnnotations() {
        System.out.println("1. 检查类上的注解:");
        
        Class<?> clazz = AnnotatedUserService.class;
        
        // 检查类是否有指定注解
        if (clazz.isAnnotationPresent(Service.class)) {
            System.out.println("AnnotatedUserService类有@Service注解");
            
            // 获取注解实例
            Service service = clazz.getAnnotation(Service.class);
            System.out.println("  名称: " + service.name());
            System.out.println("  版本: " + service.version());
            System.out.println("  描述: " + service.description());
        }
        
        // 获取所有注解
        Annotation[] annotations = clazz.getAnnotations();
        System.out.println("\n类上的所有注解:");
        for (Annotation annotation : annotations) {
            System.out.println("  " + annotation.annotationType().getSimpleName());
        }
        
        System.out.println();
    }
    
    /**
     * 示例2: 检查方法上的注解
     */
    private static void checkMethodAnnotations() {
        System.out.println("2. 检查方法上的注解:");
        
        Class<?> clazz = AnnotatedUserService.class;
        Method[] methods = clazz.getDeclaredMethods();
        
        for (Method method : methods) {
            System.out.println("\n方法: " + method.getName());
            
            // 检查@RequestMapping注解
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                System.out.println("  @RequestMapping:");
                System.out.println("    路径: " + mapping.path());
                System.out.println("    方法: " + mapping.method());
            }
            
            // 检查@Deprecated注解
            if (method.isAnnotationPresent(Deprecated.class)) {
                System.out.println("  @Deprecated: 该方法已过时");
            }
            
            // 检查@Transaction注解
            if (method.isAnnotationPresent(Transaction.class)) {
                Transaction transaction = method.getAnnotation(Transaction.class);
                System.out.println("  @Transaction:");
                System.out.println("    传播级别: " + transaction.propagation());
                System.out.println("    隔离级别: " + transaction.isolation());
                System.out.println("    只读: " + transaction.readOnly());
            }
        }
        
        System.out.println();
    }
    
    /**
     * 示例3: 检查字段上的注解
     */
    private static void checkFieldAnnotations() {
        System.out.println("3. 检查字段上的注解:");
        
        Class<?> clazz = UserEntity.class;
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            System.out.println("\n字段: " + field.getName());
            
            // 检查@Column注解
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                System.out.println("  @Column:");
                System.out.println("    列名: " + column.name());
                System.out.println("    可空: " + column.nullable());
                System.out.println("    唯一: " + column.unique());
                System.out.println("    长度: " + column.length());
            }
            
            // 检查@Id注解
            if (field.isAnnotationPresent(Id.class)) {
                System.out.println("  @Id: 主键字段");
            }
            
            // 检查@NotNull注解
            if (field.isAnnotationPresent(NotNull.class)) {
                NotNull notNull = field.getAnnotation(NotNull.class);
                System.out.println("  @NotNull: " + notNull.message());
            }
        }
        
        System.out.println();
    }
    
    /**
     * 示例4: 运行时注解处理（模拟简单的依赖注入）
     */
    private static void simpleAnnotationProcessing() {
        System.out.println("4. 运行时注解处理（模拟依赖注入）:");
        
        try {
            // 创建UserController实例
            UserController controller = new UserController();
            
            // 模拟依赖注入
            injectDependencies(controller);
            
            // 使用注入的服务
            controller.handleRequest();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    /**
     * 模拟依赖注入框架
     */
    private static void injectDependencies(Object target) throws Exception {
        Class<?> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                System.out.println("发现需要注入的字段: " + field.getName());
                
                // 创建依赖实例
                Class<?> fieldType = field.getType();
                Object dependency = fieldType.getConstructor().newInstance();
                
                // 注入依赖
                field.setAccessible(true);
                field.set(target, dependency);
                
                System.out.println("已注入: " + dependency.getClass().getSimpleName());
            }
        }
    }
}

// ========== 自定义注解 ==========

/**
 * Service注解 - 标记服务类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Service {
    String name() default "";
    String version() default "1.0";
    String description() default "";
}

/**
 * RequestMapping注解 - 标记请求映射
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RequestMapping {
    String path();
    String method() default "GET";
}

/**
 * Transaction注解 - 标记事务
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Transaction {
    String propagation() default "REQUIRED";
    String isolation() default "DEFAULT";
    boolean readOnly() default false;
}

/**
 * Column注解 - 标记数据库列
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
    boolean nullable() default true;
    boolean unique() default false;
    int length() default 255;
}

/**
 * Id注解 - 标记主键
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {
}

/**
 * NotNull注解 - 标记非空
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotNull {
    String message() default "字段不能为空";
}

/**
 * Autowired注解 - 标记自动注入
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Autowired {
}

// ========== 示例类 ==========

/**
 * 用户服务类
 */
@Service(name = "userService", version = "2.0", description = "用户管理服务")
class AnnotatedUserService {
    
    @RequestMapping(path = "/users", method = "GET")
    @Transaction(readOnly = true)
    public void listUsers() {
        System.out.println("列出所有用户");
    }
    
    @RequestMapping(path = "/users", method = "POST")
    @Transaction(propagation = "REQUIRED", isolation = "READ_COMMITTED")
    public void createUser() {
        System.out.println("创建新用户");
    }
    
    @Deprecated
    @RequestMapping(path = "/users/old", method = "GET")
    public void oldMethod() {
        System.out.println("这是一个过时的方法");
    }
    
    public void processUser() {
        System.out.println("处理用户业务");
    }
}

/**
 * 用户实体类
 */
class UserEntity {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    @NotNull(message = "用户名不能为空")
    private String username;
    
    @Column(name = "email", nullable = false, length = 100)
    @NotNull(message = "邮箱不能为空")
    private String email;
    
    @Column(name = "age", nullable = true)
    private Integer age;
}

/**
 * 用户控制器类
 */
class UserController {
    @Autowired
    private AnnotatedUserService userService;
    
    public void handleRequest() {
        System.out.println("\nUserController处理请求...");
        if (userService != null) {
            System.out.println("AnnotatedUserService已成功注入!");
            userService.processUser();
        } else {
            System.out.println("AnnotatedUserService未注入");
        }
    }
}
