package com.jayhello.container;

public interface SimpleCollection<E> extends SimpleIterable<E> {

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    boolean add(E e);

    boolean remove(Object o);

    void clear();

    Object[] toArray();
}
