package com.jayhello.container;

public interface SimpleList<E> extends SimpleCollection<E> {

    E get(int index);

    E set(int index, E element);

    void add(int index, E element);

    E remove(int index);

    int indexOf(Object o);
}
