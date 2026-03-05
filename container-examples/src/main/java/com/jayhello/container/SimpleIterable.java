package com.jayhello.container;

/**
 * 简化版可迭代接口，对应 java.lang.Iterable
 *
 * <p>实现此接口的类可以使用 for-each 语句遍历（Java增强for循环底层依赖 Iterable）。</p>
 *
 * @param <T> 元素类型
 */
public interface SimpleIterable<T> {

    /**
     * 返回用于遍历元素的迭代器
     *
     * @return 迭代器
     */
    SimpleIterator<T> iterator();
}
