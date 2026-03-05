package com.jayhello.container;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SimpleHashMap 单元测试
 */
@DisplayName("SimpleHashMap 测试")
class SimpleHashMapTest {

    private SimpleHashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new SimpleHashMap<>();
    }

    @Test
    @DisplayName("新建映射应为空")
    void testInitiallyEmpty() {
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("put/get 基本存取")
    void testPutAndGet() {
        map.put("a", 1);
        map.put("b", 2);
        assertEquals(1, map.get("a"));
        assertEquals(2, map.get("b"));
        assertEquals(2, map.size());
    }

    @Test
    @DisplayName("put 覆盖已有键并返回旧值")
    void testPutOverwrite() {
        assertNull(map.put("x", 10));
        Integer old = map.put("x", 20);
        assertEquals(10, old);
        assertEquals(20, map.get("x"));
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("get 不存在的键返回 null")
    void testGetMissing() {
        assertNull(map.get("missing"));
    }

    @Test
    @DisplayName("containsKey 正确识别键是否存在")
    void testContainsKey() {
        map.put("hello", 1);
        assertTrue(map.containsKey("hello"));
        assertFalse(map.containsKey("world"));
    }

    @Test
    @DisplayName("containsValue 正确识别值是否存在")
    void testContainsValue() {
        map.put("k", 42);
        assertTrue(map.containsValue(42));
        assertFalse(map.containsValue(99));
    }

    @Test
    @DisplayName("remove 删除键并返回旧值")
    void testRemove() {
        map.put("key", 7);
        Integer removed = map.remove("key");
        assertEquals(7, removed);
        assertEquals(0, map.size());
        assertFalse(map.containsKey("key"));
    }

    @Test
    @DisplayName("remove 不存在的键返回 null")
    void testRemoveMissing() {
        assertNull(map.remove("none"));
    }

    @Test
    @DisplayName("clear 清空所有键值对")
    void testClear() {
        map.put("a", 1);
        map.put("b", 2);
        map.clear();
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
        assertNull(map.get("a"));
    }

    @Test
    @DisplayName("keySet 返回所有键")
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
    @DisplayName("null 键支持")
    void testNullKey() {
        map.put(null, 0);
        assertTrue(map.containsKey(null));
        assertEquals(0, map.get(null));
        map.remove(null);
        assertFalse(map.containsKey(null));
    }

    @Test
    @DisplayName("触发扩容后数据仍正确")
    void testResizePreservesData() {
        // 使用小容量和高负载因子，强制触发扩容
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
    @DisplayName("大量键值对存取正确")
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
    @DisplayName("非法参数抛出 IllegalArgumentException")
    void testInvalidArguments() {
        assertThrows(IllegalArgumentException.class,
                () -> new SimpleHashMap<>(0, 0.75f));
        assertThrows(IllegalArgumentException.class,
                () -> new SimpleHashMap<>(16, -0.1f));
    }
}
