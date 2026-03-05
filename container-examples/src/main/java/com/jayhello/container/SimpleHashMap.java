package com.jayhello.container;

import java.util.HashSet;
import java.util.Set;

/**
 * 简化版哈希表映射，对应 java.util.HashMap
 *
 * <p>使用"数组 + 链表"（拉链法）解决哈希冲突：
 * <ol>
 *   <li>维护一个长度为 {@code capacity} 的 {@link Entry} 数组（桶数组）。</li>
 *   <li>每个桶存放一个单向链表，键散列到同一桶的条目以链表形式串联。</li>
 *   <li>当 size / capacity > loadFactor 时触发扩容，容量翻倍并重新散列。</li>
 * </ol>
 * </p>
 *
 * <p>继承关系：
 * <pre>
 * SimpleMap
 *   └─ SimpleHashMap  ← 本类
 * </pre>
 * （JDK 中 HashMap 继承 AbstractMap，本简化版直接实现接口以突出核心原理）</p>
 *
 * <p>时间复杂度（散列均匀时）：
 * <ul>
 *   <li>get / put / remove：均摊 O(1)</li>
 *   <li>containsValue：O(n)（需遍历所有桶）</li>
 * </ul>
 * </p>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class SimpleHashMap<K, V> implements SimpleMap<K, V> {

    /** 默认初始桶容量 */
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /** 默认负载因子：当 size/capacity > 0.75 时扩容 */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // ---- 内部条目节点（单向链表） ----

    private static class EntryNode<K, V> implements SimpleMap.Entry<K, V> {
        final K key;
        V value;
        final int hash;
        EntryNode<K, V> next;

        EntryNode(K key, V value, int hash, EntryNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            V old = this.value;
            this.value = newValue;
            return old;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    // ---- 字段 ----

    /** 桶数组 */
    @SuppressWarnings("unchecked")
    private EntryNode<K, V>[] table;

    /** 实际存储的键值对数量 */
    private int size;

    /** 触发扩容的阈值 = capacity × loadFactor */
    private int threshold;

    /** 负载因子 */
    private final float loadFactor;

    // ---- 构造方法 ----

    public SimpleHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public SimpleHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("非法初始容量: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("非法负载因子: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        // 容量向上取整到 2 的幂，以便用位运算替代取模
        int cap = tableSizeFor(initialCapacity);
        this.table = new EntryNode[cap];
        this.threshold = (int) (cap * loadFactor);
    }

    // ---- 核心方法 ----

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (EntryNode<K, V> bucket : table) {
            for (EntryNode<K, V> e = bucket; e != null; e = e.next) {
                if (value == null ? e.value == null : value.equals(e.value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        EntryNode<K, V> e = getEntry(key);
        return e == null ? null : e.value;
    }

    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        // 查找链表中是否已存在该键
        for (EntryNode<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && keysEqual(e.key, key)) {
                // 键已存在，覆盖值
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        // 键不存在，在链表头部插入新节点（头插法，与 JDK 7 一致）
        EntryNode<K, V> newEntry = new EntryNode<>(key, value, hash, table[index]);
        table[index] = newEntry;
        size++;

        // 检查是否需要扩容
        if (size > threshold) {
            resize();
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        EntryNode<K, V> prev = null;
        EntryNode<K, V> e = table[index];
        while (e != null) {
            if (e.hash == hash && keysEqual(e.key, key)) {
                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
            prev = e;
            e = e.next;
        }
        return null;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (EntryNode<K, V> bucket : table) {
            for (EntryNode<K, V> e = bucket; e != null; e = e.next) {
                keys.add(e.key);
            }
        }
        return keys;
    }

    // ---- 内部工具方法 ----

    /**
     * 计算键的哈希值，并做二次扰动（高位影响低位）以减少碰撞。
     * 对应 JDK HashMap 的 hash(key) 方法。
     */
    private static int hash(Object key) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
        // 高16位与低16位异或，使散列更均匀
        return h ^ (h >>> 16);
    }

    /**
     * 根据哈希值和桶数组长度计算桶下标。
     * 当 length 是 2 的幂时，(length - 1) & hash 等价于 hash % length，但更快。
     */
    private static int indexFor(int hash, int length) {
        return (length - 1) & hash;
    }

    /** 键相等判断：先比较引用，再用 equals */
    private static boolean keysEqual(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    /** 根据键找到对应的条目节点 */
    private EntryNode<K, V> getEntry(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (EntryNode<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && keysEqual(e.key, key)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 扩容：容量翻倍，并重新散列所有条目。
     * 因为容量始终是 2 的幂，重新散列后每个条目要么留在原桶，
     * 要么移到 "原桶下标 + 旧容量" 的桶（JDK 8 的优化思路）。
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCap = table.length;
        int newCap = oldCap << 1; // 容量翻倍
        EntryNode<K, V>[] newTable = new EntryNode[newCap];
        threshold = (int) (newCap * loadFactor);

        // 重新散列所有条目
        for (EntryNode<K, V> bucket : table) {
            for (EntryNode<K, V> e = bucket; e != null; ) {
                EntryNode<K, V> next = e.next;
                int newIndex = indexFor(e.hash, newCap);
                // 头插到新桶
                e.next = newTable[newIndex];
                newTable[newIndex] = e;
                e = next;
            }
        }
        table = newTable;
    }

    /**
     * 将容量向上取整到最近的 2 的幂。
     * 例如：tableSizeFor(10) = 16，tableSizeFor(17) = 32。
     */
    private static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    /**
     * 返回当前桶数组容量（用于演示和测试）
     *
     * @return 桶数量
     */
    public int capacity() {
        return table.length;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (EntryNode<K, V> bucket : table) {
            for (EntryNode<K, V> e = bucket; e != null; e = e.next) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(e);
                first = false;
            }
        }
        return sb.append('}').toString();
    }
}
