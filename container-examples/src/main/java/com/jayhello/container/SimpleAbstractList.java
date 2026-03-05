package com.jayhello.container;

import java.util.NoSuchElementException;

/**
 * 简化版列表抽象基类，对应 java.util.AbstractList
 *
 * <p>在 SimpleAbstractCollection 的基础上，利用 {@link #get(int)} 和 {@link #size()}
 * 提供 List 操作的骨架实现，包括迭代器、indexOf、contains 等。</p>
 *
 * <p>子类必须实现的抽象方法：
 * <ul>
 *   <li>{@link #get(int)} — 根据下标获取元素</li>
 *   <li>{@link #size()} — 返回元素数量</li>
 * </ul>
 * 若子类支持修改，还应覆盖 {@link #add(int, Object)}、{@link #set(int, Object)}、
 * {@link #remove(int)}。</p>
 *
 * @param <E> 元素类型
 */
public abstract class SimpleAbstractList<E> extends SimpleAbstractCollection<E>
        implements SimpleList<E> {

    /** 子类必须实现 */
    @Override
    public abstract E get(int index);

    /** 默认实现抛出异常，可变子类应覆盖 */
    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("set");
    }

    /** 默认实现抛出异常，可变子类应覆盖 */
    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("add");
    }

    /** 默认实现抛出异常，可变子类应覆盖 */
    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("remove");
    }

    // ---- 基于 get/size 实现 Collection 的 add ----

    /**
     * 等价于 add(size(), e)
     */
    @Override
    public boolean add(E e) {
        add(size(), e);
        return true;
    }

    // ---- 基于 get/size 实现 contains / indexOf / remove(Object) ----

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            E e = get(i);
            if (o == null ? e == null : o.equals(e)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    // ---- 基于 get/size 实现迭代器 ----

    /**
     * 返回基于下标访问的迭代器。
     * 这是 AbstractList 最重要的设计：只要实现了 get(int) 和 size()，
     * 迭代能力就可以自动获得。
     */
    @Override
    public SimpleIterator<E> iterator() {
        return new SimpleIterator<E>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(cursor++);
            }
        };
    }

    // ---- 下标合法性校验辅助方法 ----

    protected void rangeCheck(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(
                    "index: " + index + ", size: " + size());
        }
    }

    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException(
                    "index: " + index + ", size: " + size());
        }
    }
}
