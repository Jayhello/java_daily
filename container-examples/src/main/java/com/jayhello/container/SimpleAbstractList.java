package com.jayhello.container;

import java.util.NoSuchElementException;

public abstract class SimpleAbstractList<E> extends SimpleAbstractCollection<E>
        implements SimpleList<E> {

    @Override
    public abstract E get(int index);

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("set");
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("add");
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public boolean add(E e) {
        add(size(), e);
        return true;
    }

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
