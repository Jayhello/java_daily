package com.jayhello.container;

/**
 * 简化版集合根接口，对应 java.util.Collection
 *
 * <p>继承关系：SimpleIterable &lt;- SimpleCollection &lt;- SimpleList / SimpleMap</p>
 *
 * <p>Collection 是所有单值容器（List、Set 等）的根接口，定义了容器的基本操作：
 * 增、删、查、清空、大小等。</p>
 *
 * @param <E> 元素类型
 */
public interface SimpleCollection<E> extends SimpleIterable<E> {

    /**
     * 返回容器中元素的数量
     *
     * @return 元素数量
     */
    int size();

    /**
     * 容器是否为空
     *
     * @return 如果没有元素返回 true
     */
    boolean isEmpty();

    /**
     * 容器是否包含指定元素（使用 equals 判断）
     *
     * @param o 要查找的元素
     * @return 包含则返回 true
     */
    boolean contains(Object o);

    /**
     * 向容器末尾添加元素
     *
     * @param e 要添加的元素
     * @return 如果容器因此改变则返回 true
     */
    boolean add(E e);

    /**
     * 删除容器中第一个与指定对象相等的元素
     *
     * @param o 要删除的元素
     * @return 如果容器因此改变则返回 true
     */
    boolean remove(Object o);

    /**
     * 清空容器中所有元素
     */
    void clear();

    /**
     * 将容器中的元素以数组的形式返回
     *
     * @return 包含所有元素的 Object 数组
     */
    Object[] toArray();
}
