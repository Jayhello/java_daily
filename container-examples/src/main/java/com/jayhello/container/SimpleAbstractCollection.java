package com.jayhello.container;

import java.util.NoSuchElementException;

/**
 * 简化版集合抽象基类，对应 java.util.AbstractCollection
 *
 * <p>为 SimpleCollection 提供骨架实现（Skeletal Implementation 模式）：
 * 基于少量抽象方法（{@link #size()} 和 {@link #iterator()}）实现大多数方法，
 * 减少子类的重复代码。</p>
 *
 * <p>子类必须实现的抽象方法：
 * <ul>
 *   <li>{@link #size()} — 返回元素数量</li>
 *   <li>{@link #iterator()} — 返回迭代器</li>
 * </ul>
 * 若子类支持修改，还应覆盖 {@link #add(Object)}。</p>
 *
 * @param <E> 元素类型
 */
public abstract class SimpleAbstractCollection<E> implements SimpleCollection<E> {

    /** 子类必须实现 */
    @Override
    public abstract int size();

    /** 子类必须实现 */
    @Override
    public abstract SimpleIterator<E> iterator();

    // ---- 基于 size() 的默认实现 ----

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    // ---- 基于 iterator() 的默认实现 ----

    @Override
    public boolean contains(Object o) {
        SimpleIterator<E> it = iterator();
        while (it.hasNext()) {
            E e = it.next();
            if (o == null ? e == null : o.equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 默认实现抛出 UnsupportedOperationException；
     * 支持添加操作的子类应覆盖此方法。
     */
    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("add");
    }

    /**
     * 通过迭代器找到第一个与 o 相等的元素并通过迭代器删除它。
     * 迭代器必须支持 remove 操作才能使用此默认实现。
     */
    @Override
    public boolean remove(Object o) {
        SimpleIterator<E> it = iterator();
        while (it.hasNext()) {
            E e = it.next();
            if (o == null ? e == null : o.equals(e)) {
                // 通过内部机制删除当前元素（子类迭代器可实现此逻辑）
                // 本简化版不要求迭代器提供 remove，由具体子类覆盖此方法
                throw new UnsupportedOperationException(
                        "SimpleAbstractCollection.remove: 请在子类中覆盖");
            }
        }
        return false;
    }

    @Override
    public void clear() {
        // 通过反复删除第一个元素实现（低效，子类应覆盖）
        while (!isEmpty()) {
            SimpleIterator<E> it = iterator();
            if (it.hasNext()) {
                remove(it.next());
            }
        }
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size()];
        SimpleIterator<E> it = iterator();
        for (int i = 0; i < result.length; i++) {
            if (!it.hasNext()) {
                // 并发修改保护：返回已填充部分
                Object[] trimmed = new Object[i];
                System.arraycopy(result, 0, trimmed, 0, i);
                return trimmed;
            }
            result[i] = it.next();
        }
        return result;
    }

    @Override
    public String toString() {
        SimpleIterator<E> it = iterator();
        if (!it.hasNext()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        while (true) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext()) {
                return sb.append(']').toString();
            }
            sb.append(", ");
        }
    }
}
