package com.jayhello.container;

public abstract class SimpleAbstractCollection<E> implements SimpleCollection<E> {

    @Override
    public abstract int size();

    @Override
    public abstract SimpleIterator<E> iterator();

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

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

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("add");
    }

    @Override
    public boolean remove(Object o) {
        SimpleIterator<E> it = iterator();
        while (it.hasNext()) {
            E e = it.next();
            if (o == null ? e == null : o.equals(e)) {
                throw new UnsupportedOperationException(
                        "SimpleAbstractCollection.remove: 请在子类中覆盖");
            }
        }
        return false;
    }

    @Override
    public void clear() {
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
