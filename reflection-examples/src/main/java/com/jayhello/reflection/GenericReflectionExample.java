package com.jayhello.reflection;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * 泛型反射示例
 * 演示如何通过反射处理泛型信息
 */
public class GenericReflectionExample {
    
    public static void main(String[] args) {
        System.out.println("=== 泛型反射示例 ===\n");
        
        // 示例1: 获取字段的泛型类型
        getFieldGenericType();
        
        // 示例2: 获取方法的泛型类型
        getMethodGenericType();
        
        // 示例3: 获取类的泛型参数
        getClassGenericType();
        
        // 示例4: 获取泛型边界
        getGenericBounds();
    }
    
    /**
     * 示例1: 获取字段的泛型类型
     */
    private static void getFieldGenericType() {
        System.out.println("1. 获取字段的泛型类型:");
        
        Class<?> clazz = GenericClass.class;
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            System.out.println("\n字段: " + field.getName());
            System.out.println("  声明类型: " + field.getType().getName());
            
            // 获取泛型类型
            Type genericType = field.getGenericType();
            System.out.println("  泛型类型: " + genericType);
            
            // 判断是否为参数化类型
            if (genericType instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) genericType;
                
                // 获取原始类型
                System.out.println("  原始类型: " + paramType.getRawType());
                
                // 获取实际类型参数
                Type[] typeArguments = paramType.getActualTypeArguments();
                System.out.println("  类型参数:");
                for (Type typeArg : typeArguments) {
                    System.out.println("    - " + typeArg);
                }
            }
        }
        
        System.out.println();
    }
    
    /**
     * 示例2: 获取方法的泛型类型
     */
    private static void getMethodGenericType() {
        System.out.println("2. 获取方法的泛型类型:");
        
        Class<?> clazz = GenericClass.class;
        
        try {
            // 获取processList方法
            Method method = clazz.getMethod("processList", List.class);
            System.out.println("方法: " + method.getName());
            
            // 获取参数的泛型类型
            Type[] paramTypes = method.getGenericParameterTypes();
            System.out.println("参数数量: " + paramTypes.length);
            
            for (int i = 0; i < paramTypes.length; i++) {
                System.out.println("\n参数" + i + ":");
                Type paramType = paramTypes[i];
                System.out.println("  类型: " + paramType);
                
                if (paramType instanceof ParameterizedType) {
                    ParameterizedType pType = (ParameterizedType) paramType;
                    Type[] typeArgs = pType.getActualTypeArguments();
                    System.out.println("  泛型参数:");
                    for (Type typeArg : typeArgs) {
                        System.out.println("    - " + typeArg);
                    }
                }
            }
            
            // 获取返回值的泛型类型
            Type returnType = method.getGenericReturnType();
            System.out.println("\n返回类型: " + returnType);
            
            if (returnType instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) returnType;
                Type[] typeArgs = pType.getActualTypeArguments();
                System.out.println("返回类型的泛型参数:");
                for (Type typeArg : typeArgs) {
                    System.out.println("  - " + typeArg);
                }
            }
            
            // 获取processMap方法
            System.out.println("\n----------------------------------------");
            method = clazz.getMethod("processMap", Map.class);
            System.out.println("方法: " + method.getName());
            
            paramTypes = method.getGenericParameterTypes();
            Type mapType = paramTypes[0];
            
            if (mapType instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) mapType;
                Type[] typeArgs = pType.getActualTypeArguments();
                System.out.println("Map的泛型参数:");
                for (int i = 0; i < typeArgs.length; i++) {
                    System.out.println("  参数" + i + ": " + typeArgs[i]);
                }
            }
            
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    /**
     * 示例3: 获取类的泛型参数
     */
    private static void getClassGenericType() {
        System.out.println("3. 获取类的泛型参数:");
        
        // 获取GenericSubClass的泛型父类信息
        Class<?> clazz = GenericSubClass.class;
        Type superType = clazz.getGenericSuperclass();
        
        System.out.println("子类: " + clazz.getName());
        System.out.println("父类: " + superType);
        
        if (superType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) superType;
            Type[] typeArgs = paramType.getActualTypeArguments();
            
            System.out.println("父类的泛型参数:");
            for (Type typeArg : typeArgs) {
                System.out.println("  - " + typeArg);
            }
        }
        
        // 获取实现的接口的泛型信息
        Type[] interfaces = clazz.getGenericInterfaces();
        System.out.println("\n实现的接口:");
        for (Type interfaceType : interfaces) {
            System.out.println("  接口: " + interfaceType);
            
            if (interfaceType instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) interfaceType;
                Type[] typeArgs = paramType.getActualTypeArguments();
                System.out.println("  泛型参数:");
                for (Type typeArg : typeArgs) {
                    System.out.println("    - " + typeArg);
                }
            }
        }
        
        System.out.println();
    }
    
    /**
     * 示例4: 获取泛型边界
     */
    private static void getGenericBounds() {
        System.out.println("4. 获取泛型边界:");
        
        Class<?> clazz = BoundedGenericClass.class;
        
        // 获取类的类型参数
        TypeVariable<?>[] typeParams = clazz.getTypeParameters();
        System.out.println("类型参数数量: " + typeParams.length);
        
        for (TypeVariable<?> typeParam : typeParams) {
            System.out.println("\n类型参数: " + typeParam.getName());
            
            // 获取上界
            Type[] bounds = typeParam.getBounds();
            System.out.println("  上界:");
            for (Type bound : bounds) {
                System.out.println("    - " + bound);
            }
        }
        
        // 获取方法的类型参数
        try {
            Method method = clazz.getMethod("compare", Object.class, Object.class);
            TypeVariable<?>[] methodTypeParams = method.getTypeParameters();
            
            System.out.println("\n方法: " + method.getName());
            System.out.println("方法类型参数数量: " + methodTypeParams.length);
            
            for (TypeVariable<?> typeParam : methodTypeParams) {
                System.out.println("\n  类型参数: " + typeParam.getName());
                
                Type[] bounds = typeParam.getBounds();
                System.out.println("    上界:");
                for (Type bound : bounds) {
                    System.out.println("      - " + bound);
                }
            }
            
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        
        System.out.println();
    }
}

/**
 * 带泛型字段的类
 */
class GenericClass<T> {
    private List<String> stringList;
    private List<Integer> integerList;
    private Map<String, Integer> stringIntMap;
    private Map<Long, List<String>> complexMap;
    private T genericField;
    
    public List<String> processList(List<String> list) {
        return list;
    }
    
    public void processMap(Map<String, Integer> map) {
    }
    
    public T getGenericField() {
        return genericField;
    }
    
    public void setGenericField(T field) {
        this.genericField = field;
    }
}

/**
 * 泛型父类
 */
class GenericParent<T, E> {
    private T data;
    private E extra;
}

/**
 * 继承泛型父类的子类
 */
class GenericSubClass extends GenericParent<String, Integer> implements Comparable<String> {
    @Override
    public int compareTo(String o) {
        return 0;
    }
}

/**
 * 带边界的泛型类
 */
class BoundedGenericClass<T extends Number & Comparable<T>> {
    private T value;
    
    public <E extends Comparable<E>> int compare(E o1, E o2) {
        return o1.compareTo(o2);
    }
}
