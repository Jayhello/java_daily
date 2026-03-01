package com.jayhello.reflection;

import java.lang.reflect.Constructor;

/**
 * 构造器反射示例
 * 演示如何通过反射创建对象实例
 */
public class ConstructorReflectionExample {
    
    public static void main(String[] args) {
        System.out.println("=== 构造器反射示例 ===\n");
        
        // 示例1: 使用无参构造器创建实例
        createInstanceWithNoArgsConstructor();
        
        // 示例2: 使用带参构造器创建实例
        createInstanceWithParameterizedConstructor();
        
        // 示例3: 访问私有构造器
        accessPrivateConstructor();
        
        // 示例4: 破解单例模式
        breakSingletonPattern();
    }
    
    /**
     * 示例1: 使用无参构造器创建实例
     */
    private static void createInstanceWithNoArgsConstructor() {
        System.out.println("1. 使用无参构造器创建实例:");
        
        try {
            // 方法1: 使用Class.newInstance()（已过时）
            Class<?> clazz = Book.class;
            // Book book1 = (Book) clazz.newInstance(); // 已过时
            
            // 方法2: 使用Constructor.newInstance()（推荐）
            Constructor<?> constructor = clazz.getConstructor();
            Book book1 = (Book) constructor.newInstance();
            System.out.println("创建的书籍1: " + book1);
            
            // 也可以直接获取构造器并创建
            Book book2 = (Book) Book.class.getConstructor().newInstance();
            System.out.println("创建的书籍2: " + book2);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例2: 使用带参构造器创建实例
     */
    private static void createInstanceWithParameterizedConstructor() {
        System.out.println("2. 使用带参构造器创建实例:");
        
        try {
            Class<?> clazz = Book.class;
            
            // 获取带String, String, double参数的构造器
            Constructor<?> constructor = clazz.getConstructor(String.class, String.class, double.class);
            
            // 使用构造器创建实例
            Book book = (Book) constructor.newInstance("Java编程思想", "Bruce Eckel", 108.0);
            System.out.println("创建的书籍: " + book);
            
            // 获取另一个构造器
            Constructor<?> constructor2 = clazz.getConstructor(String.class, String.class);
            Book book2 = (Book) constructor2.newInstance("Effective Java", "Joshua Bloch");
            System.out.println("创建的书籍2: " + book2);
            
            // 显示构造器信息
            System.out.println("\n构造器信息:");
            Constructor<?>[] constructors = clazz.getConstructors();
            for (Constructor<?> c : constructors) {
                System.out.print("  " + c.getName() + "(");
                Class<?>[] paramTypes = c.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    System.out.print(paramTypes[i].getSimpleName());
                    if (i < paramTypes.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println(")");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例3: 访问私有构造器
     */
    private static void accessPrivateConstructor() {
        System.out.println("3. 访问私有构造器:");
        
        try {
            Class<?> clazz = SecretBook.class;
            
            // 获取私有构造器
            Constructor<?> privateConstructor = clazz.getDeclaredConstructor(String.class);
            
            System.out.println("私有构造器是否可访问: " + privateConstructor.isAccessible());
            
            // 设置为可访问
            privateConstructor.setAccessible(true);
            System.out.println("设置accessible后: " + privateConstructor.isAccessible());
            
            // 使用私有构造器创建实例
            SecretBook secretBook = (SecretBook) privateConstructor.newInstance("机密文档");
            System.out.println("通过私有构造器创建: " + secretBook);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例4: 破解单例模式
     */
    private static void breakSingletonPattern() {
        System.out.println("4. 破解单例模式:");
        
        try {
            // 正常获取单例
            Singleton instance1 = Singleton.getInstance();
            System.out.println("正常方式获取单例1: " + instance1);
            System.out.println("单例1的ID: " + instance1.getId());
            
            Singleton instance2 = Singleton.getInstance();
            System.out.println("正常方式获取单例2: " + instance2);
            System.out.println("两个实例是否相同: " + (instance1 == instance2));
            
            // 通过反射破解单例
            Class<?> clazz = Singleton.class;
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            
            Singleton instance3 = (Singleton) constructor.newInstance();
            System.out.println("\n通过反射创建的实例: " + instance3);
            System.out.println("反射实例的ID: " + instance3.getId());
            System.out.println("反射实例与单例是否相同: " + (instance1 == instance3));
            
            System.out.println("\n注意: 反射可以破坏单例模式！");
            System.out.println("防御方法: 在构造器中检查实例是否已存在，如果存在则抛出异常");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}

/**
 * 示例用的Book类
 */
class Book {
    private String title;
    private String author;
    private double price;
    
    // 无参构造器
    public Book() {
        this("未知", "未知", 0.0);
    }
    
    // 带两个参数的构造器
    public Book(String title, String author) {
        this(title, author, 0.0);
    }
    
    // 带三个参数的构造器
    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "', price=" + price + "}";
    }
}

/**
 * 带私有构造器的类
 */
class SecretBook {
    private String title;
    
    // 私有构造器
    private SecretBook(String title) {
        this.title = title;
    }
    
    @Override
    public String toString() {
        return "SecretBook{title='" + title + "'}";
    }
}

/**
 * 单例模式示例
 */
class Singleton {
    private static final Singleton INSTANCE = new Singleton();
    private final String id;
    
    // 私有构造器
    private Singleton() {
        this.id = "SINGLETON-" + System.currentTimeMillis();
        
        // 防御反射攻击的代码（这里注释掉以演示反射破解）
        /*
        if (INSTANCE != null) {
            throw new RuntimeException("不能通过反射创建单例实例!");
        }
        */
    }
    
    public static Singleton getInstance() {
        return INSTANCE;
    }
    
    public String getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Singleton{id='" + id + "'}";
    }
}
