package com.jayhello.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 基本反射示例
 * 演示如何获取Class对象以及类的基本信息
 */
public class BasicReflectionExample {
    
    public static void main(String[] args) {
        System.out.println("=== Java反射基础示例 ===\n");
        
        // 示例1: 获取Class对象的三种方式
        getClassObjectExample();
        
        // 示例2: 获取类的基本信息
        getClassInfoExample();
        
        // 示例3: 获取类的字段信息
        getFieldsExample();
        
        // 示例4: 获取类的方法信息
        getMethodsExample();
        
        // 示例5: 获取类的构造器信息
        getConstructorsExample();
    }
    
    /**
     * 示例1: 获取Class对象的三种方式
     */
    private static void getClassObjectExample() {
        System.out.println("1. 获取Class对象的三种方式:");
        
        try {
            // 方式1: 通过类名.class
            Class<?> clazz1 = Person.class;
            System.out.println("方式1 - Person.class: " + clazz1.getName());
            
            // 方式2: 通过对象.getClass()
            Person person = new Person("张三", 25);
            Class<?> clazz2 = person.getClass();
            System.out.println("方式2 - person.getClass(): " + clazz2.getName());
            
            // 方式3: 通过Class.forName()
            Class<?> clazz3 = Class.forName("com.jayhello.reflection.Person");
            System.out.println("方式3 - Class.forName(): " + clazz3.getName());
            
            // 验证三种方式获取的是同一个Class对象
            System.out.println("三种方式是否相同: " + (clazz1 == clazz2 && clazz2 == clazz3));
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例2: 获取类的基本信息
     */
    private static void getClassInfoExample() {
        System.out.println("2. 获取类的基本信息:");
        
        Class<?> clazz = Person.class;
        
        // 类名
        System.out.println("简单类名: " + clazz.getSimpleName());
        System.out.println("完全限定类名: " + clazz.getName());
        System.out.println("规范类名: " + clazz.getCanonicalName());
        
        // 修饰符
        int modifiers = clazz.getModifiers();
        System.out.println("修饰符: " + Modifier.toString(modifiers));
        System.out.println("是否为public: " + Modifier.isPublic(modifiers));
        
        // 父类
        Class<?> superclass = clazz.getSuperclass();
        System.out.println("父类: " + superclass.getName());
        
        // 实现的接口
        Class<?>[] interfaces = clazz.getInterfaces();
        System.out.println("实现的接口数量: " + interfaces.length);
        
        // 包信息
        Package pkg = clazz.getPackage();
        System.out.println("所在包: " + pkg.getName());
        
        System.out.println();
    }
    
    /**
     * 示例3: 获取类的字段信息
     */
    private static void getFieldsExample() {
        System.out.println("3. 获取类的字段信息:");
        
        Class<?> clazz = Person.class;
        
        // getFields()只能获取public字段（包括继承的）
        System.out.println("--- 公有字段（getFields）---");
        Field[] publicFields = clazz.getFields();
        for (Field field : publicFields) {
            System.out.println("字段名: " + field.getName() + 
                             ", 类型: " + field.getType().getSimpleName());
        }
        
        // getDeclaredFields()获取所有声明的字段（不包括继承的）
        System.out.println("\n--- 所有声明的字段（getDeclaredFields）---");
        Field[] allFields = clazz.getDeclaredFields();
        for (Field field : allFields) {
            System.out.println("字段名: " + field.getName() + 
                             ", 类型: " + field.getType().getSimpleName() +
                             ", 修饰符: " + Modifier.toString(field.getModifiers()));
        }
        
        System.out.println();
    }
    
    /**
     * 示例4: 获取类的方法信息
     */
    private static void getMethodsExample() {
        System.out.println("4. 获取类的方法信息:");
        
        Class<?> clazz = Person.class;
        
        // getMethods()获取所有public方法（包括继承的）
        System.out.println("--- 公有方法（getMethods）---");
        Method[] publicMethods = clazz.getMethods();
        int count = 0;
        for (Method method : publicMethods) {
            // 只显示Person类自己声明的方法
            if (method.getDeclaringClass() == Person.class) {
                System.out.println("方法名: " + method.getName() + 
                                 ", 返回类型: " + method.getReturnType().getSimpleName() +
                                 ", 参数数量: " + method.getParameterCount());
                count++;
            }
        }
        System.out.println("Person类自己的公有方法数量: " + count);
        
        // getDeclaredMethods()获取所有声明的方法（不包括继承的）
        System.out.println("\n--- 所有声明的方法（getDeclaredMethods）---");
        Method[] allMethods = clazz.getDeclaredMethods();
        for (Method method : allMethods) {
            System.out.println("方法名: " + method.getName() + 
                             ", 修饰符: " + Modifier.toString(method.getModifiers()));
        }
        
        System.out.println();
    }
    
    /**
     * 示例5: 获取类的构造器信息
     */
    private static void getConstructorsExample() {
        System.out.println("5. 获取类的构造器信息:");
        
        Class<?> clazz = Person.class;
        
        // 获取所有公有构造器
        Constructor<?>[] constructors = clazz.getConstructors();
        System.out.println("公有构造器数量: " + constructors.length);
        
        for (Constructor<?> constructor : constructors) {
            System.out.print("构造器: " + constructor.getName() + "(");
            Class<?>[] paramTypes = constructor.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                System.out.print(paramTypes[i].getSimpleName());
                if (i < paramTypes.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(")");
        }
        
        System.out.println();
    }
}

/**
 * 示例用的Person类
 */
class Person {
    // 私有字段
    private String name;
    private int age;
    
    // 公有字段（用于演示）
    public String country = "China";
    
    // 静态字段
    private static int count = 0;
    
    // 无参构造器
    public Person() {
        this("未知", 0);
    }
    
    // 带参构造器
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        count++;
    }
    
    // 公有方法
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    // 私有方法
    private String getSecret() {
        return "这是一个私有方法";
    }
    
    // 静态方法
    public static int getCount() {
        return count;
    }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", country='" + country + "'}";
    }
}
