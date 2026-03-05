package com.jayhello.container;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SimpleArrayList 单元测试
 */
@DisplayName("SimpleArrayList 测试")
class SimpleArrayListTest {

    private SimpleArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new SimpleArrayList<>();
    }

    @Test
    @DisplayName("新建列表应为空")
    void testInitiallyEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("add 末尾追加元素")
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
    @DisplayName("add(index) 在指定位置插入元素")
    void testAddAtIndex() {
        list.add(1);
        list.add(3);
        list.add(1, 2); // 在中间插入
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    @DisplayName("add(0) 在头部插入元素")
    void testAddAtHead() {
        list.add(2);
        list.add(0, 1);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    @DisplayName("set 替换指定位置元素并返回旧值")
    void testSet() {
        list.add(10);
        list.add(20);
        Integer old = list.set(1, 99);
        assertEquals(20, old);
        assertEquals(99, list.get(1));
    }

    @Test
    @DisplayName("remove(index) 删除指定下标元素")
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
    @DisplayName("remove(Object) 删除第一个匹配元素")
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
    @DisplayName("contains 检查是否包含元素")
    void testContains() {
        list.add(5);
        assertTrue(list.contains(5));
        assertFalse(list.contains(99));
    }

    @Test
    @DisplayName("indexOf 返回元素首次出现的下标")
    void testIndexOf() {
        list.add(10);
        list.add(20);
        list.add(10);
        assertEquals(0, list.indexOf(10));
        assertEquals(1, list.indexOf(20));
        assertEquals(-1, list.indexOf(99));
    }

    @Test
    @DisplayName("clear 清空所有元素")
    void testClear() {
        list.add(1);
        list.add(2);
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("toArray 返回包含所有元素的数组")
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
    @DisplayName("iterator 可以正确遍历所有元素")
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
    @DisplayName("超出容量时自动扩容")
    void testAutoGrow() {
        int initial = list.capacity(); // 默认 10
        for (int i = 0; i < initial + 1; i++) {
            list.add(i);
        }
        assertTrue(list.capacity() > initial);
        assertEquals(initial + 1, list.size());
    }

    @Test
    @DisplayName("下标越界抛出 IndexOutOfBoundsException")
    void testRangeCheck() {
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    @DisplayName("负数初始容量抛出 IllegalArgumentException")
    void testNegativeCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleArrayList<>(-1));
    }
}
