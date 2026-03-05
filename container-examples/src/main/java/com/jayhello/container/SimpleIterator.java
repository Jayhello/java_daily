package com.jayhello.container;

/**
 * 简化版迭代器接口，对应 java.util.Iterator
 *
 * <p>迭代器模式：提供一种顺序访问容器元素的方法，无需暴露容器的内部实现。</p>
 *
 * @param <E> 元素类型
 */
public interface SimpleIterator<E> {

    /**
     * 是否还有下一个元素
     *
     * @return 如果还有元素返回 true
     */
    boolean hasNext();

    /**
     * 返回下一个元素
     *
     * @return 下一个元素
     * @throws java.util.NoSuchElementException 如果没有更多元素
     */
    E next();
}
