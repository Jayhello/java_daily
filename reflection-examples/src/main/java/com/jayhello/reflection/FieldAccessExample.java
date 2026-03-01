package com.jayhello.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 字段访问示例
 * 演示如何通过反射访问和修改字段的值
 */
public class FieldAccessExample {
    
    public static void main(String[] args) {
        System.out.println("=== 字段访问示例 ===\n");
        
        // 示例1: 访问公有字段
        accessPublicFieldExample();
        
        // 示例2: 访问私有字段
        accessPrivateFieldExample();
        
        // 示例3: 访问静态字段
        accessStaticFieldExample();
        
        // 示例4: 修改final字段
        modifyFinalFieldExample();
    }
    
    /**
     * 示例1: 访问公有字段
     */
    private static void accessPublicFieldExample() {
        System.out.println("1. 访问公有字段:");
        
        try {
            Student student = new Student("李明", 20, "计算机科学");
            Class<?> clazz = student.getClass();
            
            // 获取公有字段
            Field publicField = clazz.getField("studentId");
            
            // 读取字段值
            Object value = publicField.get(student);
            System.out.println("学号: " + value);
            
            // 修改字段值
            publicField.set(student, "S12345678");
            System.out.println("修改后的学号: " + student.studentId);
            
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例2: 访问私有字段
     */
    private static void accessPrivateFieldExample() {
        System.out.println("2. 访问私有字段:");
        
        try {
            Student student = new Student("王芳", 22, "数学");
            Class<?> clazz = student.getClass();
            
            System.out.println("原始学生信息: " + student);
            
            // 获取私有字段 - 使用getDeclaredField
            Field nameField = clazz.getDeclaredField("name");
            Field ageField = clazz.getDeclaredField("age");
            Field majorField = clazz.getDeclaredField("major");
            
            // 私有字段需要设置accessible为true才能访问
            nameField.setAccessible(true);
            ageField.setAccessible(true);
            majorField.setAccessible(true);
            
            // 读取私有字段值
            String name = (String) nameField.get(student);
            int age = (int) ageField.get(student);
            String major = (String) majorField.get(student);
            
            System.out.println("通过反射读取:");
            System.out.println("  姓名: " + name);
            System.out.println("  年龄: " + age);
            System.out.println("  专业: " + major);
            
            // 修改私有字段值
            nameField.set(student, "王芳芳");
            ageField.set(student, 23);
            majorField.set(student, "应用数学");
            
            System.out.println("修改后的学生信息: " + student);
            
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例3: 访问静态字段
     */
    private static void accessStaticFieldExample() {
        System.out.println("3. 访问静态字段:");
        
        try {
            Class<?> clazz = Student.class;
            
            // 获取静态字段
            Field countField = clazz.getDeclaredField("studentCount");
            countField.setAccessible(true);
            
            // 读取静态字段（不需要实例对象）
            int count = (int) countField.get(null);
            System.out.println("当前学生总数: " + count);
            
            // 创建几个学生实例
            new Student("学生1", 20, "专业1");
            new Student("学生2", 21, "专业2");
            new Student("学生3", 22, "专业3");
            
            // 再次读取
            count = (int) countField.get(null);
            System.out.println("创建3个学生后的总数: " + count);
            
            // 修改静态字段
            countField.set(null, 100);
            System.out.println("通过反射修改后的总数: " + Student.getStudentCount());
            
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例4: 修改final字段
     */
    private static void modifyFinalFieldExample() {
        System.out.println("4. 修改final字段:");
        
        try {
            Student student = new Student("张三", 20, "物理");
            Class<?> clazz = student.getClass();
            
            // 获取final字段
            Field schoolField = clazz.getDeclaredField("school");
            schoolField.setAccessible(true);
            
            System.out.println("原始学校: " + schoolField.get(student));
            
            // 尝试修改final字段
            // 注意: 对于编译时常量，即使修改了字段值，直接访问可能仍然是旧值
            schoolField.set(student, "清华大学");
            
            System.out.println("通过反射读取修改后的学校: " + schoolField.get(student));
            System.out.println("直接访问学校字段: " + student.getSchool());
            
            // 演示字段的详细信息
            System.out.println("\n字段详细信息:");
            System.out.println("  字段名: " + schoolField.getName());
            System.out.println("  字段类型: " + schoolField.getType());
            System.out.println("  是否为final: " + Modifier.isFinal(schoolField.getModifiers()));
            System.out.println("  修饰符: " + Modifier.toString(schoolField.getModifiers()));
            
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}

/**
 * 示例用的Student类
 */
class Student {
    // 私有字段
    private String name;
    private int age;
    private String major;
    
    // 公有字段
    public String studentId = "S00000000";
    
    // final字段
    private final String school = "北京大学";
    
    // 静态字段
    private static int studentCount = 0;
    
    public Student(String name, int age, String major) {
        this.name = name;
        this.age = age;
        this.major = major;
        studentCount++;
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getMajor() {
        return major;
    }
    
    public String getSchool() {
        return school;
    }
    
    public static int getStudentCount() {
        return studentCount;
    }
    
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + 
               ", major='" + major + "', school='" + school + "'}";
    }
}
