package com.jayhello.reflection;

import java.lang.reflect.Array;

/**
 * 数组反射示例
 * 演示如何通过反射操作数组
 */
public class ArrayReflectionExample {
    
    public static void main(String[] args) {
        System.out.println("=== 数组反射示例 ===\n");
        
        // 示例1: 创建数组
        createArrayExample();
        
        // 示例2: 操作数组元素
        manipulateArrayExample();
        
        // 示例3: 获取数组信息
        getArrayInfoExample();
        
        // 示例4: 创建多维数组
        createMultiDimensionalArrayExample();
    }
    
    /**
     * 示例1: 创建数组
     */
    private static void createArrayExample() {
        System.out.println("1. 创建数组:");
        
        // 创建int数组
        Object intArray = Array.newInstance(int.class, 5);
        System.out.println("创建int数组，长度: " + Array.getLength(intArray));
        System.out.println("数组类型: " + intArray.getClass().getName());
        
        // 创建String数组
        Object stringArray = Array.newInstance(String.class, 3);
        System.out.println("\n创建String数组，长度: " + Array.getLength(stringArray));
        System.out.println("数组类型: " + stringArray.getClass().getName());
        
        // 创建自定义类型数组
        Object personArray = Array.newInstance(Person.class, 4);
        System.out.println("\n创建Person数组，长度: " + Array.getLength(personArray));
        System.out.println("数组类型: " + personArray.getClass().getName());
        
        System.out.println();
    }
    
    /**
     * 示例2: 操作数组元素
     */
    private static void manipulateArrayExample() {
        System.out.println("2. 操作数组元素:");
        
        // 创建并填充int数组
        Object intArray = Array.newInstance(int.class, 5);
        
        System.out.println("设置数组元素:");
        for (int i = 0; i < 5; i++) {
            Array.set(intArray, i, (i + 1) * 10);
            System.out.println("  array[" + i + "] = " + (i + 1) * 10);
        }
        
        System.out.println("\n读取数组元素:");
        for (int i = 0; i < 5; i++) {
            int value = Array.getInt(intArray, i);
            System.out.println("  array[" + i + "] = " + value);
        }
        
        // 创建并操作String数组
        Object stringArray = Array.newInstance(String.class, 3);
        Array.set(stringArray, 0, "Java");
        Array.set(stringArray, 1, "Python");
        Array.set(stringArray, 2, "C++");
        
        System.out.println("\nString数组内容:");
        for (int i = 0; i < 3; i++) {
            String value = (String) Array.get(stringArray, i);
            System.out.println("  array[" + i + "] = " + value);
        }
        
        // 创建并操作对象数组
        Object personArray = Array.newInstance(Person.class, 2);
        Array.set(personArray, 0, new Person("张三", 25));
        Array.set(personArray, 1, new Person("李四", 30));
        
        System.out.println("\nPerson数组内容:");
        for (int i = 0; i < 2; i++) {
            Person person = (Person) Array.get(personArray, i);
            System.out.println("  array[" + i + "] = " + person);
        }
        
        System.out.println();
    }
    
    /**
     * 示例3: 获取数组信息
     */
    private static void getArrayInfoExample() {
        System.out.println("3. 获取数组信息:");
        
        // 普通数组
        int[] intArray = {1, 2, 3, 4, 5};
        Class<?> clazz = intArray.getClass();
        
        System.out.println("int[]数组信息:");
        System.out.println("  是否为数组: " + clazz.isArray());
        System.out.println("  组件类型: " + clazz.getComponentType().getName());
        System.out.println("  数组长度: " + Array.getLength(intArray));
        System.out.println("  类名: " + clazz.getName());
        
        // 对象数组
        String[] stringArray = {"Hello", "World"};
        clazz = stringArray.getClass();
        
        System.out.println("\nString[]数组信息:");
        System.out.println("  是否为数组: " + clazz.isArray());
        System.out.println("  组件类型: " + clazz.getComponentType().getName());
        System.out.println("  数组长度: " + Array.getLength(stringArray));
        System.out.println("  类名: " + clazz.getName());
        
        // 多维数组
        int[][] matrix = {{1, 2}, {3, 4}, {5, 6}};
        clazz = matrix.getClass();
        
        System.out.println("\nint[][]数组信息:");
        System.out.println("  是否为数组: " + clazz.isArray());
        System.out.println("  组件类型: " + clazz.getComponentType().getName());
        System.out.println("  外层数组长度: " + Array.getLength(matrix));
        System.out.println("  类名: " + clazz.getName());
        
        System.out.println();
    }
    
    /**
     * 示例4: 创建多维数组
     */
    private static void createMultiDimensionalArrayExample() {
        System.out.println("4. 创建多维数组:");
        
        // 创建二维int数组 (3x4)
        Object matrix = Array.newInstance(int.class, 3, 4);
        System.out.println("创建3x4的int二维数组");
        System.out.println("数组类型: " + matrix.getClass().getName());
        
        // 填充二维数组
        for (int i = 0; i < 3; i++) {
            Object row = Array.get(matrix, i);
            for (int j = 0; j < 4; j++) {
                Array.setInt(row, j, i * 4 + j + 1);
            }
        }
        
        // 打印二维数组
        System.out.println("\n二维数组内容:");
        for (int i = 0; i < 3; i++) {
            Object row = Array.get(matrix, i);
            System.out.print("  行" + i + ": [");
            for (int j = 0; j < 4; j++) {
                System.out.print(Array.getInt(row, j));
                if (j < 3) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
        
        // 创建三维数组
        Object cube = Array.newInstance(String.class, 2, 2, 2);
        System.out.println("\n创建2x2x2的String三维数组");
        System.out.println("数组类型: " + cube.getClass().getName());
        
        // 填充三维数组
        for (int i = 0; i < 2; i++) {
            Object plane = Array.get(cube, i);
            for (int j = 0; j < 2; j++) {
                Object row = Array.get(plane, j);
                for (int k = 0; k < 2; k++) {
                    Array.set(row, k, "(" + i + "," + j + "," + k + ")");
                }
            }
        }
        
        // 打印三维数组
        System.out.println("\n三维数组内容:");
        for (int i = 0; i < 2; i++) {
            System.out.println("  层" + i + ":");
            Object plane = Array.get(cube, i);
            for (int j = 0; j < 2; j++) {
                Object row = Array.get(plane, j);
                System.out.print("    行" + j + ": [");
                for (int k = 0; k < 2; k++) {
                    System.out.print(Array.get(row, k));
                    if (k < 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println("]");
            }
        }
        
        System.out.println();
    }
    
    /**
     * Person类（用于演示）
     */
    static class Person {
        private String name;
        private int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }
}
