package com.jayhello.reflection;

import java.lang.reflect.Method;

/**
 * 方法调用示例
 * 演示如何通过反射调用方法
 */
public class MethodInvocationExample {
    
    public static void main(String[] args) {
        System.out.println("=== 方法调用示例 ===\n");
        
        // 示例1: 调用公有方法
        invokePublicMethodExample();
        
        // 示例2: 调用私有方法
        invokePrivateMethodExample();
        
        // 示例3: 调用静态方法
        invokeStaticMethodExample();
        
        // 示例4: 调用带参数的方法
        invokeMethodWithParametersExample();
        
        // 示例5: 处理方法返回值
        handleReturnValueExample();
    }
    
    /**
     * 示例1: 调用公有方法
     */
    private static void invokePublicMethodExample() {
        System.out.println("1. 调用公有方法:");
        
        try {
            Calculator calc = new Calculator();
            Class<?> clazz = calc.getClass();
            
            // 获取add方法
            Method addMethod = clazz.getMethod("add", int.class, int.class);
            
            // 调用方法
            Object result = addMethod.invoke(calc, 10, 20);
            System.out.println("10 + 20 = " + result);
            
            // 获取subtract方法
            Method subtractMethod = clazz.getMethod("subtract", int.class, int.class);
            result = subtractMethod.invoke(calc, 30, 15);
            System.out.println("30 - 15 = " + result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例2: 调用私有方法
     */
    private static void invokePrivateMethodExample() {
        System.out.println("2. 调用私有方法:");
        
        try {
            Calculator calc = new Calculator();
            Class<?> clazz = calc.getClass();
            
            // 获取私有方法
            Method privateMethod = clazz.getDeclaredMethod("multiplySecret", int.class, int.class);
            
            // 设置accessible为true才能调用私有方法
            privateMethod.setAccessible(true);
            
            // 调用私有方法
            Object result = privateMethod.invoke(calc, 5, 6);
            System.out.println("5 * 6 = " + result + " (通过私有方法计算)");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例3: 调用静态方法
     */
    private static void invokeStaticMethodExample() {
        System.out.println("3. 调用静态方法:");
        
        try {
            Class<?> clazz = Calculator.class;
            
            // 获取静态方法
            Method staticMethod = clazz.getMethod("getVersion");
            
            // 调用静态方法（第一个参数传null）
            Object result = staticMethod.invoke(null);
            System.out.println("Calculator版本: " + result);
            
            // 调用带参数的静态方法
            Method powerMethod = clazz.getMethod("power", int.class, int.class);
            result = powerMethod.invoke(null, 2, 8);
            System.out.println("2的8次方 = " + result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例4: 调用带参数的方法
     */
    private static void invokeMethodWithParametersExample() {
        System.out.println("4. 调用带参数的方法:");
        
        try {
            Calculator calc = new Calculator();
            Class<?> clazz = calc.getClass();
            
            // 调用带不同类型参数的方法
            Method divideMethod = clazz.getMethod("divide", double.class, double.class);
            Object result = divideMethod.invoke(calc, 10.0, 3.0);
            System.out.println("10.0 / 3.0 = " + result);
            
            // 调用带字符串参数的方法
            Method printMethod = clazz.getMethod("printMessage", String.class);
            printMethod.invoke(calc, "这是通过反射调用的消息");
            
            // 调用可变参数方法
            Method sumMethod = clazz.getMethod("sum", int[].class);
            int[] numbers = {1, 2, 3, 4, 5};
            result = sumMethod.invoke(calc, numbers);
            System.out.println("数组求和: " + result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    /**
     * 示例5: 处理方法返回值
     */
    private static void handleReturnValueExample() {
        System.out.println("5. 处理方法返回值:");
        
        try {
            Calculator calc = new Calculator();
            Class<?> clazz = calc.getClass();
            
            // 调用返回String的方法
            Method getInfoMethod = clazz.getMethod("getInfo");
            String info = (String) getInfoMethod.invoke(calc);
            System.out.println("Calculator信息: " + info);
            
            // 调用返回基本类型的方法
            Method isPositiveMethod = clazz.getMethod("isPositive", int.class);
            boolean isPositive = (boolean) isPositiveMethod.invoke(calc, 10);
            System.out.println("10是正数吗? " + isPositive);
            
            isPositive = (boolean) isPositiveMethod.invoke(calc, -5);
            System.out.println("-5是正数吗? " + isPositive);
            
            // 调用void方法
            Method resetMethod = clazz.getMethod("reset");
            Object voidResult = resetMethod.invoke(calc);
            System.out.println("void方法返回值: " + voidResult);
            
            // 获取方法的返回类型信息
            System.out.println("\n方法返回类型信息:");
            System.out.println("  getInfo返回类型: " + getInfoMethod.getReturnType().getName());
            System.out.println("  isPositive返回类型: " + isPositiveMethod.getReturnType().getName());
            System.out.println("  reset返回类型: " + resetMethod.getReturnType().getName());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}

/**
 * 示例用的Calculator类
 */
class Calculator {
    private int operationCount = 0;
    
    // 公有方法
    public int add(int a, int b) {
        operationCount++;
        return a + b;
    }
    
    public int subtract(int a, int b) {
        operationCount++;
        return a - b;
    }
    
    public double divide(double a, double b) {
        operationCount++;
        if (b == 0) {
            throw new ArithmeticException("除数不能为0");
        }
        return a / b;
    }
    
    public void printMessage(String message) {
        System.out.println("消息: " + message);
    }
    
    public int sum(int... numbers) {
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return total;
    }
    
    public String getInfo() {
        return "Calculator v1.0 - 操作次数: " + operationCount;
    }
    
    public boolean isPositive(int number) {
        return number > 0;
    }
    
    public void reset() {
        operationCount = 0;
        System.out.println("计算器已重置");
    }
    
    // 私有方法
    private int multiplySecret(int a, int b) {
        operationCount++;
        return a * b;
    }
    
    // 静态方法
    public static String getVersion() {
        return "1.0.0";
    }
    
    public static int power(int base, int exponent) {
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }
}
