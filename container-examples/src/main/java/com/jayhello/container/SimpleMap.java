package com.jayhello.container;

import java.util.Set;

/**
 * 简化版键值映射接口，对应 java.util.Map
 *
 * <p>Map 存储键值对（key-value），每个键唯一，一个键对应一个值。
 * 与 Collection 体系独立，有自己的继承树：
 *   SimpleMap &lt;- SimpleAbstractMap &lt;- SimpleHashMap</p>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public interface SimpleMap<K, V> {

    /**
     * 返回映射中键值对的数量
     *
     * @return 键值对数量
     */
    int size();

    /**
     * 映射是否为空
     *
     * @return 如果没有键值对返回 true
     */
    boolean isEmpty();

    /**
     * 是否包含指定键
     *
     * @param key 要查找的键
     * @return 包含则返回 true
     */
    boolean containsKey(Object key);

    /**
     * 是否包含指定值
     *
     * @param value 要查找的值
     * @return 包含则返回 true
     */
    boolean containsValue(Object value);

    /**
     * 根据键获取对应的值，键不存在则返回 null
     *
     * @param key 键
     * @return 对应的值，或 null
     */
    V get(Object key);

    /**
     * 存入键值对，若键已存在则覆盖，返回旧值（无旧值则返回 null）
     *
     * @param key   键
     * @param value 值
     * @return 旧值，或 null
     */
    V put(K key, V value);

    /**
     * 删除指定键及其对应的值，返回被删除的值
     *
     * @param key 键
     * @return 被删除的值，或 null
     */
    V remove(Object key);

    /**
     * 清空所有键值对
     */
    void clear();

    /**
     * 返回所有键的集合
     *
     * @return 键的 Set
     */
    Set<K> keySet();

    /**
     * 键值对条目接口，对应 java.util.Map.Entry
     *
     * <p>Map 内部以 Entry 的形式存储每一个键值对。</p>
     *
     * @param <K> 键类型
     * @param <V> 值类型
     */
    interface Entry<K, V> {

        /**
         * 返回该条目的键
         *
         * @return 键
         */
        K getKey();

        /**
         * 返回该条目的值
         *
         * @return 值
         */
        V getValue();

        /**
         * 设置该条目的值，返回旧值
         *
         * @param value 新值
         * @return 旧值
         */
        V setValue(V value);
    }
}
