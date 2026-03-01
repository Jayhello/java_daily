# Reflection Examples

本模块包含Java反射机制相关的示例代码。

## 简介

Java反射机制允许程序在运行时检查和操作类、接口、字段和方法。反射是Java语言的重要特性，广泛应用于框架开发、依赖注入、序列化等场景。

## 示例

### 1. BasicReflectionExample
演示反射的基本使用，包括：
- 获取Class对象的多种方式
- 获取类的名称、修饰符、父类、接口等基本信息
- 获取类的所有字段、方法、构造器

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.reflection.BasicReflectionExample"
```

### 2. FieldAccessExample
演示如何通过反射访问和修改字段，包括：
- 访问公有字段
- 访问和修改私有字段
- 处理final字段
- 获取字段的类型和修饰符

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.reflection.FieldAccessExample"
```

### 3. MethodInvocationExample
演示如何通过反射调用方法，包括：
- 调用公有方法
- 调用私有方法
- 调用静态方法
- 处理方法参数和返回值

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.reflection.MethodInvocationExample"
```

### 4. ConstructorReflectionExample
演示如何通过反射创建对象实例，包括：
- 使用无参构造器创建实例
- 使用带参构造器创建实例
- 访问私有构造器（单例模式破解）
- 获取构造器信息

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.reflection.ConstructorReflectionExample"
```

### 5. AnnotationReflectionExample
演示如何通过反射处理注解，包括：
- 检查类、方法、字段上的注解
- 读取注解的属性值
- 运行时注解处理
- 自定义注解的使用

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.reflection.AnnotationReflectionExample"
```

### 6. ArrayReflectionExample
演示如何通过反射操作数组，包括：
- 创建数组
- 获取和设置数组元素
- 获取数组长度和类型
- 处理多维数组

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.reflection.ArrayReflectionExample"
```

### 7. GenericReflectionExample
演示如何通过反射处理泛型，包括：
- 获取泛型类型信息
- 获取方法的泛型参数
- 获取字段的泛型类型
- 处理泛型边界

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.reflection.GenericReflectionExample"
```

### 8. ProxyExample
演示如何使用动态代理，包括：
- 创建动态代理对象
- InvocationHandler的使用
- 代理模式在AOP中的应用
- 方法拦截和增强

运行示例:
```bash
mvn exec:java -Dexec.mainClass="com.jayhello.reflection.ProxyExample"
```

## 反射的应用场景

1. **框架开发**: Spring、Hibernate等框架大量使用反射实现依赖注入、对象关系映射等功能
2. **动态代理**: AOP、RPC框架中的代理模式实现
3. **序列化和反序列化**: Jackson、Gson等JSON库使用反射进行对象转换
4. **测试工具**: JUnit等测试框架使用反射发现和执行测试方法
5. **开发工具**: IDE的自动补全、代码提示等功能

## 反射的性能考虑

反射虽然强大，但会带来一些性能开销：
- 反射调用比直接调用慢
- 跳过编译时类型检查，可能导致运行时错误
- 破坏封装性，访问私有成员可能导致安全问题

建议：
- 在性能敏感的场景避免过度使用反射
- 缓存反射获取的Class、Method、Field对象
- 考虑使用方法句柄（MethodHandle）作为替代方案

## 依赖

本模块仅依赖JDK标准库和JUnit测试框架。
