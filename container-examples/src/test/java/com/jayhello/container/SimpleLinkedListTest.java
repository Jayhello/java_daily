package com.jayhello.container;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleLinkedListTest {

    private SimpleLinkedList<String> list;

    @BeforeEach
    void setUp() {
        list = new SimpleLinkedList<>();
    }

    @Test
    void testInitiallyEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testAddAppend() {
        list.add("A");
        list.add("B");
        list.add("C");
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    void testAddAtIndex() {
        list.add("A");
        list.add("C");
        list.add(1, "B");
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    void testAddAtHead() {
        list.add("B");
        list.add(0, "A");
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
    }

    @Test
    void testSet() {
        list.add("X");
        list.add("Y");
        String old = list.set(0, "Z");
        assertEquals("X", old);
        assertEquals("Z", list.get(0));
    }

    @Test
    void testRemoveByIndex() {
        list.add("A");
        list.add("B");
        list.add("C");
        String removed = list.remove(1);
        assertEquals("B", removed);
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
    }

    @Test
    void testRemoveByObject() {
        list.add("A");
        list.add("B");
        list.add("A");
        assertTrue(list.remove("A"));
        assertEquals(2, list.size());
        assertEquals("B", list.get(0));
        assertEquals("A", list.get(1));
    }

    @Test
    void testContains() {
        list.add("hello");
        assertTrue(list.contains("hello"));
        assertFalse(list.contains("world"));
    }

    @Test
    void testIndexOf() {
        list.add("A");
        list.add("B");
        list.add("A");
        assertEquals(0, list.indexOf("A"));
        assertEquals(1, list.indexOf("B"));
        assertEquals(-1, list.indexOf("Z"));
    }

    @Test
    void testClear() {
        list.add("A");
        list.add("B");
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testToArray() {
        list.add("X");
        list.add("Y");
        Object[] arr = list.toArray();
        assertEquals(2, arr.length);
        assertEquals("X", arr[0]);
        assertEquals("Y", arr[1]);
    }

    @Test
    void testIterator() {
        list.add("A");
        list.add("B");
        list.add("C");
        SimpleIterator<String> it = list.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        assertEquals("ABC", sb.toString());
    }

    @Test
    void testRangeCheck() {
        list.add("A");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    void testNullElement() {
        list.add(null);
        list.add("A");
        assertEquals(2, list.size());
        assertNull(list.get(0));
        assertTrue(list.contains(null));
        assertEquals(0, list.indexOf(null));
        assertTrue(list.remove(null));
        assertEquals(1, list.size());
    }
}

