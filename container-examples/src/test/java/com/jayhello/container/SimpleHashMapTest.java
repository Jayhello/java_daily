package com.jayhello.container;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SimpleHashMapTest {

    private SimpleHashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new SimpleHashMap<>();
    }

    @Test
    void testInitiallyEmpty() {
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

    @Test
    void testPutAndGet() {
        map.put("a", 1);
        map.put("b", 2);
        assertEquals(1, map.get("a"));
        assertEquals(2, map.get("b"));
        assertEquals(2, map.size());
    }

    @Test
    void testPutOverwrite() {
        assertNull(map.put("x", 10));
        Integer old = map.put("x", 20);
        assertEquals(10, old);
        assertEquals(20, map.get("x"));
        assertEquals(1, map.size());
    }

    @Test
    void testGetMissing() {
        assertNull(map.get("missing"));
    }

    @Test
    void testContainsKey() {
        map.put("hello", 1);
        assertTrue(map.containsKey("hello"));
        assertFalse(map.containsKey("world"));
    }

    @Test
    void testContainsValue() {
        map.put("k", 42);
        assertTrue(map.containsValue(42));
        assertFalse(map.containsValue(99));
    }

    @Test
    void testRemove() {
        map.put("key", 7);
        Integer removed = map.remove("key");
        assertEquals(7, removed);
        assertEquals(0, map.size());
        assertFalse(map.containsKey("key"));
    }

    @Test
    void testRemoveMissing() {
        assertNull(map.remove("none"));
    }

    @Test
    void testClear() {
        map.put("a", 1);
        map.put("b", 2);
        map.clear();
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
        assertNull(map.get("a"));
    }

    @Test
    void testKeySet() {
        map.put("x", 1);
        map.put("y", 2);
        map.put("z", 3);
        Set<String> keys = map.keySet();
        assertEquals(3, keys.size());
        assertTrue(keys.contains("x"));
        assertTrue(keys.contains("y"));
        assertTrue(keys.contains("z"));
    }

    @Test
    void testNullKey() {
        map.put(null, 0);
        assertTrue(map.containsKey(null));
        assertEquals(0, map.get(null));
        map.remove(null);
        assertFalse(map.containsKey(null));
    }

    @Test
    void testResizePreservesData() {
        SimpleHashMap<Integer, Integer> m = new SimpleHashMap<>(4, 0.75f);
        int count = 20;
        for (int i = 0; i < count; i++) {
            m.put(i, i * 10);
        }
        assertEquals(count, m.size());
        for (int i = 0; i < count; i++) {
            assertEquals(i * 10, m.get(i));
        }
    }

    @Test
    void testLargeInsert() {
        int n = 1000;
        for (int i = 0; i < n; i++) {
            map.put("key" + i, i);
        }
        assertEquals(n, map.size());
        for (int i = 0; i < n; i++) {
            assertEquals(i, map.get("key" + i));
        }
    }

    @Test
    void testInvalidArguments() {
        assertThrows(IllegalArgumentException.class,
                () -> new SimpleHashMap<>(0, 0.75f));
        assertThrows(IllegalArgumentException.class,
                () -> new SimpleHashMap<>(16, -0.1f));
    }
}

