package com.jayhello.container;

/**
 * 简化版有序列表接口，对应 java.util.List
 *
 * <p>继承自 SimpleCollection，增加了基于下标的随机访问能力。
 * List 是有序的、允许重复元素的容器。</p>
 *
 * <p>继承关系：SimpleIterable &lt;- SimpleCollection &lt;- SimpleList
 *   实现类：SimpleAbstractCollection &lt;- SimpleAbstractList &lt;- SimpleArrayList
 *                                                              &lt;- SimpleLinkedList</p>
 *
 * @param <E> 元素类型
 */
public interface SimpleList<E> extends SimpleCollection<E> {

    /**
     * 返回指定下标处的元素
     *
     * @param index 下标（0-based）
     * @return 该位置的元素
     * @throws IndexOutOfBoundsException 如果下标越界
     */
    E get(int index);

    /**
     * 替换指定下标处的元素，返回被替换的旧元素
     *
     * @param index   下标（0-based）
     * @param element 新元素
     * @return 被替换的旧元素
     * @throws IndexOutOfBoundsException 如果下标越界
     */
    E set(int index, E element);

    /**
     * 在指定下标处插入元素，原位置及之后的元素后移
     *
     * @param index   下标（0-based）
     * @param element 要插入的元素
     * @throws IndexOutOfBoundsException 如果下标越界
     */
    void add(int index, E element);

    /**
     * 删除指定下标处的元素，返回被删除的元素
     *
     * @param index 下标（0-based）
     * @return 被删除的元素
     * @throws IndexOutOfBoundsException 如果下标越界
     */
    E remove(int index);

    /**
     * 返回指定元素第一次出现的下标，不存在则返回 -1
     *
     * @param o 要查找的元素
     * @return 下标，或 -1
     */
    int indexOf(Object o);
}
