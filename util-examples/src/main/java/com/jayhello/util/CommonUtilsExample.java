package com.jayhello.util;

import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 常用工具类示例
 */
public class CommonUtilsExample {
    
    /**
     * 字符串工具示例
     */
    public void stringUtilsDemo() {
        String str = "  Hello World  ";
        
        // Apache Commons Lang
        System.out.println("isEmpty: " + StringUtils.isEmpty(str));
        System.out.println("isBlank: " + StringUtils.isBlank(str));
        System.out.println("trim: " + StringUtils.trim(str));
        System.out.println("capitalize: " + StringUtils.capitalize("hello"));
    }
    
    /**
     * 集合工具示例
     */
    public void collectionUtilsDemo() {
        // Guava Lists
        List<String> list = Lists.newArrayList("A", "B", "C");
        List<List<String>> partitions = Lists.partition(list, 2);
        
        System.out.println("原始列表: " + list);
        System.out.println("分区列表: " + partitions);
    }
    
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        CommonUtilsExample example = new CommonUtilsExample();
        
        System.out.println("=== 字符串工具示例 ===");
        example.stringUtilsDemo();
        
        System.out.println("\n=== 集合工具示例 ===");
        example.collectionUtilsDemo();

        System.out.println("\n=== arrayDemo1 ===");
//        arrayDemo1();
        arrayDemo2();

        System.out.println("\n=== classDemo1 ===");
        classDemo1();
//        classDemo2();
    }

    public static void arrayDemo1(){
        int[] intArray = new int[]{1, 2, 3};
        System.out.println(intArray.getClass().getName());

        Integer[] integerArray = new Integer[3];
        System.out.println(integerArray.getClass().getName());  // [Ljava.lang.Integer;

        String[] stringArray = new String[3];
        System.out.println(stringArray.getClass().getName());  // [Ljava.lang.String;

        int[][] matrix = new int[3][3];
        System.out.println(matrix.getClass().getName());  // [[I
    }

    public static void arrayDemo2(){
        int[] intArray = new int[3];

        // 1. 是对象，有Class对象
        Class<?> clazz = intArray.getClass();
        System.out.println("类名: " + clazz.getName());        // [I
        System.out.println("父类: " + clazz.getSuperclass());  // Object
        System.out.println("是否是数组: " + clazz.isArray());   // true

        // 2. 可以调用Object方法
        System.out.println("toString: " + intArray.toString());
        System.out.println("hashCode: " + intArray.hashCode());

        // 3. 可以作为锁
        synchronized (intArray) {
            System.out.println("数组作为锁");
        }

        // 4. 可以用反射操作
        System.out.println("字段数: " + clazz.getDeclaredFields().length);
        // 输出可能为0或1（有些JVM会显示length字段）
    }

    public void sayHello(){
        System.out.println("this is sayHello method");
    }

    public static void printClassInfo(Class<?> clazz) {
        System.out.println("类名: " + clazz.getName());
        System.out.println("包名: " + clazz.getPackage());
        System.out.println("父类: " + clazz.getSuperclass());

        // 获取所有方法，这里我们只打印信息，不关心方法的返回值类型
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("  方法: " + method.getName());
        }
    }

    public static void classDemo1() throws ClassNotFoundException {
        CommonUtilsExample item = new CommonUtilsExample();
        Class clazz1 = item.getClass();
        printClassInfo(clazz1);

        Class<?> clazz2 = CommonUtilsExample.class;

        Class<?> clazz3 = Class.forName("com.jayhello.util.CommonUtilsExample");
    }

    public static void classDemo2() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz1 = CommonUtilsExample.class;
        Object obj = clazz1.newInstance();
        Method[] methods = clazz1.getMethods();
        Method method = clazz1.getMethod("sayHello");
        method.invoke(obj);
    }
}
