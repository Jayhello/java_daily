package com.jayhello.container;

import java.util.NoSuchElementException;

/**
 * 简化版双向链表列表，对应 java.util.LinkedList
 *
 * <p>底层使用双向链表（{@link Node}）存储元素，支持高效的头/尾操作。
 * 与 SimpleArrayList 相比，随机访问（get/set）需要 O(n) 遍历，
 * 但在已知节点位置时插入/删除是 O(1)。</p>
 *
 * <p>继承关系：
 * <pre>
 * SimpleIterable
 *   └─ SimpleCollection
 *         └─ SimpleList
 *               └─ SimpleAbstractCollection
 *                     └─ SimpleAbstractList
 *                           └─ SimpleLinkedList  ← 本类
 * </pre>
 * </p>
 *
 * <p>时间复杂度：
 * <ul>
 *   <li>get/set：O(n)（需要遍历到目标节点）</li>
 *   <li>add(头/尾)：O(1)</li>
 *   <li>add(中间)/remove：O(n)（定位）+ O(1)（操作节点）</li>
 *   <li>contains/indexOf：O(n)</li>
 * </ul>
 * </p>
 *
 * @param <E> 元素类型
 */
public class SimpleLinkedList<E> extends SimpleAbstractList<E> {

    /** 双向链表节点 */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    /** 头节点（first.prev == null） */
    private Node<E> first;

    /** 尾节点（last.next == null） */
    private Node<E> last;

    /** 元素数量 */
    private int size;

    // ---- 构造方法 ----

    public SimpleLinkedList() {
    }

    // ---- 核心方法 ----

    @Override
    public int size() {
        return size;
    }

    /**
     * 获取指定下标的元素，需要从头（或尾）开始遍历。
     * 当 index < size/2 时从头遍历，否则从尾遍历（小优化）。
     */
    @Override
    public E get(int index) {
        rangeCheck(index);
        return node(index).item;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element;
        return oldVal;
    }

    /**
     * 在指定下标插入元素。
     * 若 index == size，则追加到末尾；否则在对应节点前插入。
     */
    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if (index == size) {
            linkLast(element);
        } else {
            linkBefore(element, node(index));
        }
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        return unlink(node(index));
    }

    @Override
    public void clear() {
        // 断开所有节点引用，帮助 GC
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
    }

    // ---- 链表操作的辅助方法 ----

    /** 追加到链表末尾 */
    private void linkLast(E e) {
        Node<E> l = last;
        Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode; // 链表本来为空
        } else {
            l.next = newNode;
        }
        size++;
    }

    /** 在节点 succ 之前插入新节点 */
    private void linkBefore(E e, Node<E> succ) {
        Node<E> pred = succ.prev;
        Node<E> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode; // succ 原来是头节点
        } else {
            pred.next = newNode;
        }
        size++;
    }

    /** 从链表中断开节点 x 并返回其元素 */
    private E unlink(Node<E> x) {
        E element = x.item;
        Node<E> next = x.next;
        Node<E> prev = x.prev;

        if (prev == null) {
            first = next; // x 是头节点
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev; // x 是尾节点
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null; // 帮助 GC
        size--;
        return element;
    }

    /**
     * 根据下标找到对应节点（双端折半优化）
     *
     * @param index 下标
     * @return 对应节点
     */
    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            // 从头部遍历
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            // 从尾部遍历
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    // ---- 重写迭代器以利用链表特性 ----

    /**
     * 返回基于链表指针的迭代器（比 AbstractList 基于 get(index) 的版本更高效）
     */
    @Override
    public SimpleIterator<E> iterator() {
        return new SimpleIterator<E>() {
            private Node<E> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E item = current.item;
                current = current.next;
                return item;
            }
        };
    }
}
