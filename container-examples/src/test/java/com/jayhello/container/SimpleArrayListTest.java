package com.jayhello.container;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleArrayListTest {

    private SimpleArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new SimpleArrayList<>();
    }

    @Test
    void testInitiallyEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testAddAppend() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void testAddAtIndex() {
        list.add(1);
        list.add(3);
        list.add(1, 2);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void testAddAtHead() {
        list.add(2);
        list.add(0, 1);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    void testSet() {
        list.add(10);
        list.add(20);
        Integer old = list.set(1, 99);
        assertEquals(20, old);
        assertEquals(99, list.get(1));
    }

    @Test
    void testRemoveByIndex() {
        list.add(1);
        list.add(2);
        list.add(3);
        Integer removed = list.remove(1);
        assertEquals(2, removed);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    void testRemoveByObject() {
        list.add(1);
        list.add(2);
        list.add(1);
        assertTrue(list.remove(Integer.valueOf(1)));
        assertEquals(2, list.size());
        assertEquals(2, list.get(0));
        assertEquals(1, list.get(1));
    }

    @Test
    void testContains() {
        list.add(5);
        assertTrue(list.contains(5));
        assertFalse(list.contains(99));
    }

    @Test
    void testIndexOf() {
        list.add(10);
        list.add(20);
        list.add(10);
        assertEquals(0, list.indexOf(10));
        assertEquals(1, list.indexOf(20));
        assertEquals(-1, list.indexOf(99));
    }

    @Test
    void testClear() {
        list.add(1);
        list.add(2);
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testToArray() {
        list.add(1);
        list.add(2);
        list.add(3);
        Object[] arr = list.toArray();
        assertEquals(3, arr.length);
        assertEquals(1, arr[0]);
        assertEquals(2, arr[1]);
        assertEquals(3, arr[2]);
    }

    @Test
    void testIterator() {
        list.add(1);
        list.add(2);
        list.add(3);
        SimpleIterator<Integer> it = list.iterator();
        int sum = 0;
        while (it.hasNext()) {
            sum += it.next();
        }
        assertEquals(6, sum);
    }

    @Test
    void testAutoGrow() {
        int initial = list.capacity();
        for (int i = 0; i < initial + 1; i++) {
            list.add(i);
        }
        assertTrue(list.capacity() > initial);
        assertEquals(initial + 1, list.size());
    }

    @Test
    void testRangeCheck() {
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    void testNegativeCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleArrayList<>(-1));
    }
}

