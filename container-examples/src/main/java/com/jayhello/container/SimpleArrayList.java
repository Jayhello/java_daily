package com.jayhello.container;

import java.util.Arrays;

/**
 * 简化版动态数组列表，对应 java.util.ArrayList
 *
 * <p>底层使用 Object 数组存储元素，支持动态扩容：
 * 当容量不足时，新容量 = 旧容量 × 1.5（与 JDK 实现一致）。</p>
 *
 * <p>继承关系：
 * <pre>
 * SimpleIterable
 *   └─ SimpleCollection
 *         └─ SimpleList
 *               └─ SimpleAbstractCollection
 *                     └─ SimpleAbstractList
 *                           └─ SimpleArrayList  ← 本类
 * </pre>
 * </p>
 *
 * <p>时间复杂度：
 * <ul>
 *   <li>get/set：O(1)</li>
 *   <li>add(末尾)：均摊 O(1)</li>
 *   <li>add(中间)/remove：O(n)（需要移动元素）</li>
 *   <li>contains/indexOf：O(n)</li>
 * </ul>
 * </p>
 *
 * @param <E> 元素类型
 */
public class SimpleArrayList<E> extends SimpleAbstractList<E> {

    /** 默认初始容量 */
    private static final int DEFAULT_CAPACITY = 10;

    /** 存储元素的数组 */
    private Object[] elementData;

    /** 当前元素数量（逻辑大小） */
    private int size;

    // ---- 构造方法 ----

    /**
     * 使用默认初始容量（10）创建空列表
     */
    public SimpleArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 使用指定初始容量创建空列表
     *
     * @param initialCapacity 初始容量
     * @throws IllegalArgumentException 如果 initialCapacity 为负数
     */
    public SimpleArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                    "非法的初始容量: " + initialCapacity);
        }
        this.elementData = new Object[initialCapacity];
    }

    // ---- 核心方法 ----

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        rangeCheck(index);
        return (E) elementData[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    /**
     * 在指定下标处插入元素。
     *
     * <p>将从 index 开始的所有元素向右移动一格（System.arraycopy），
     * 再将新元素放入 index 位置。这体现了数组在中间插入时 O(n) 的代价。</p>
     */
    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacity(size + 1);
        // 将 index 之后的元素整体右移一格
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    /**
     * 删除指定下标处的元素。
     *
     * <p>将 index 之后的所有元素向左移动一格，最后一个位置置 null
     * （帮助 GC 回收对象）。</p>
     */
    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        rangeCheck(index);
        E oldValue = (E) elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null; // 清除引用，帮助 GC
        return oldValue;
    }

    @Override
    public void clear() {
        // 将所有元素置 null，帮助 GC 回收
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    // ---- 扩容逻辑 ----

    /**
     * 确保数组至少能容纳 minCapacity 个元素，容量不足时触发扩容。
     *
     * @param minCapacity 最小所需容量
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) {
            grow(minCapacity);
        }
    }

    /**
     * 扩容：新容量 = 旧容量 × 1.5（右移1位再加上旧容量），
     * 若仍不够则直接使用 minCapacity。
     *
     * @param minCapacity 最小所需容量
     */
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        // 新容量 = 旧容量 + 旧容量/2，等效于 oldCapacity * 1.5
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * 返回当前数组容量（用于演示和测试）
     *
     * @return 底层数组的容量
     */
    public int capacity() {
        return elementData.length;
    }
}
