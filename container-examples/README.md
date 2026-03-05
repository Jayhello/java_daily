# Container Examples — 简化版 Java 容器

本模块通过**从零实现**的方式，帮助理解 Java 集合框架（`java.util`）中核心容器的**继承关系**和**实现原理**。

---

## 继承关系总览

```
java.lang.Iterable<T>
  └── java.util.Collection<E>
        └── java.util.List<E>
              ├── java.util.AbstractCollection<E>   ← 骨架抽象类
              │     └── java.util.AbstractList<E>   ← 骨架抽象类
              │           ├── java.util.ArrayList   ← 数组实现
              │           └── java.util.LinkedList  ← 链表实现

java.util.Map<K,V>
  └── java.util.AbstractMap<K,V>
        └── java.util.HashMap<K,V>                  ← 哈希表实现
```

本模块的对应简化版：

```
SimpleIterable<T>
  └── SimpleCollection<E>
        └── SimpleList<E>
              ├── SimpleAbstractCollection<E>   ← 骨架抽象类
              │     └── SimpleAbstractList<E>   ← 骨架抽象类
              │           ├── SimpleArrayList   ← 数组实现
              │           └── SimpleLinkedList  ← 链表实现

SimpleMap<K,V>
  └── SimpleHashMap<K,V>                        ← 哈希表实现
```

---

## 文件说明

| 文件 | 对应 JDK 类 | 说明 |
|------|------------|------|
| `SimpleIterator.java` | `java.util.Iterator` | 迭代器接口 |
| `SimpleIterable.java` | `java.lang.Iterable` | 可迭代接口 |
| `SimpleCollection.java` | `java.util.Collection` | 集合根接口 |
| `SimpleList.java` | `java.util.List` | 有序列表接口 |
| `SimpleMap.java` | `java.util.Map` | 键值映射接口（含 `Entry` 嵌套接口）|
| `SimpleAbstractCollection.java` | `java.util.AbstractCollection` | 基于迭代器的骨架实现 |
| `SimpleAbstractList.java` | `java.util.AbstractList` | 基于 `get(int)` 的骨架实现 |
| `SimpleArrayList.java` | `java.util.ArrayList` | 动态数组，扩容因子 1.5x |
| `SimpleLinkedList.java` | `java.util.LinkedList` | 双向链表 |
| `SimpleHashMap.java` | `java.util.HashMap` | 数组+链表哈希表，负载因子 0.75 |
| `ContainerDemo.java` | — | 演示继承关系与各容器操作 |

---

## 核心设计原理

### 1. 骨架实现（Skeletal Implementation）模式
`AbstractCollection` 和 `AbstractList` 只要求子类实现少量核心方法，其余方法全部通过这些核心方法提供默认实现，大幅减少子类代码量。

- `SimpleAbstractCollection` 只需 `size()` + `iterator()` → 自动获得 `contains`、`toArray`、`isEmpty` 等
- `SimpleAbstractList` 只需 `get(int)` + `size()` → 自动获得 `contains`、`indexOf`、`iterator` 等

### 2. SimpleArrayList 原理
- 底层：`Object[]` 数组
- 随机访问 O(1)，中间插入/删除 O(n)（需移动元素）
- 扩容：`newCapacity = oldCapacity + oldCapacity >> 1`（即 × 1.5）

### 3. SimpleLinkedList 原理
- 底层：双向链表（`Node<E>` 含 `prev`、`next`、`item` 三个字段）
- 头/尾操作 O(1)，随机访问 O(n)
- 双端折半优化：`index < size/2` 时从头遍历，否则从尾遍历

### 4. SimpleHashMap 原理
- 底层：桶数组 + 链表（拉链法解决哈希冲突）
- 容量始终为 2 的幂：用 `(length-1) & hash` 替代 `hash % length`，效率更高
- 哈希扰动：`hash ^ (hash >>> 16)` 让高位影响低位，减少碰撞
- 触发扩容条件：`size > capacity × loadFactor`，扩容后容量翻倍

---

## 运行演示

```bash
# 从项目根目录执行
mvn -pl container-examples compile exec:java \
  -Dexec.mainClass=com.jayhello.container.ContainerDemo
```

## 运行测试

```bash
mvn -pl container-examples test
```
