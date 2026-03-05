package com.jayhello.container;

import java.util.HashSet;
import java.util.Set;

public class SimpleHashMap<K, V> implements SimpleMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private static class EntryNode<K, V> implements SimpleMap.Entry<K, V> {
        final K key;
        V value;
        final int hash;
        EntryNode<K, V> next;

        EntryNode(K key, V value, int hash, EntryNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            V old = this.value;
            this.value = newValue;
            return old;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    @SuppressWarnings("unchecked")
    private EntryNode<K, V>[] table;

    private int size;

    private int threshold;

    private final float loadFactor;

    public SimpleHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public SimpleHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("非法初始容量: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("非法负载因子: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        int cap = tableSizeFor(initialCapacity);
        this.table = new EntryNode[cap];
        this.threshold = (int) (cap * loadFactor);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (EntryNode<K, V> bucket : table) {
            for (EntryNode<K, V> e = bucket; e != null; e = e.next) {
                if (value == null ? e.value == null : value.equals(e.value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        EntryNode<K, V> e = getEntry(key);
        return e == null ? null : e.value;
    }

    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        for (EntryNode<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && keysEqual(e.key, key)) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        EntryNode<K, V> newEntry = new EntryNode<>(key, value, hash, table[index]);
        table[index] = newEntry;
        size++;

        if (size > threshold) {
            resize();
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        EntryNode<K, V> prev = null;
        EntryNode<K, V> e = table[index];
        while (e != null) {
            if (e.hash == hash && keysEqual(e.key, key)) {
                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
            prev = e;
            e = e.next;
        }
        return null;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (EntryNode<K, V> bucket : table) {
            for (EntryNode<K, V> e = bucket; e != null; e = e.next) {
                keys.add(e.key);
            }
        }
        return keys;
    }

    private static int hash(Object key) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    private static int indexFor(int hash, int length) {
        return (length - 1) & hash;
    }

    private static boolean keysEqual(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    private EntryNode<K, V> getEntry(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (EntryNode<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && keysEqual(e.key, key)) {
                return e;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCap = table.length;
        int newCap = oldCap << 1;
        EntryNode<K, V>[] newTable = new EntryNode[newCap];
        threshold = (int) (newCap * loadFactor);

        for (EntryNode<K, V> bucket : table) {
            for (EntryNode<K, V> e = bucket; e != null; ) {
                EntryNode<K, V> next = e.next;
                int newIndex = indexFor(e.hash, newCap);
                e.next = newTable[newIndex];
                newTable[newIndex] = e;
                e = next;
            }
        }
        table = newTable;
    }

    private static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    public int capacity() {
        return table.length;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (EntryNode<K, V> bucket : table) {
            for (EntryNode<K, V> e = bucket; e != null; e = e.next) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(e);
                first = false;
            }
        }
        return sb.append('}').toString();
    }
}
