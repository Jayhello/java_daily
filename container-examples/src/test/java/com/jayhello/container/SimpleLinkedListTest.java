package com.jayhello.container;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SimpleLinkedList 单元测试
 */
@DisplayName("SimpleLinkedList 测试")
class SimpleLinkedListTest {

    private SimpleLinkedList<String> list;

    @BeforeEach
    void setUp() {
        list = new SimpleLinkedList<>();
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
        list.add("A");
        list.add("B");
        list.add("C");
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    @DisplayName("add(index) 在指定位置插入元素")
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
    @DisplayName("add(0) 在头部插入元素")
    void testAddAtHead() {
        list.add("B");
        list.add(0, "A");
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
    }

    @Test
    @DisplayName("set 替换指定位置元素并返回旧值")
    void testSet() {
        list.add("X");
        list.add("Y");
        String old = list.set(0, "Z");
        assertEquals("X", old);
        assertEquals("Z", list.get(0));
    }

    @Test
    @DisplayName("remove(index) 删除指定下标元素")
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
    @DisplayName("remove(Object) 删除第一个匹配元素")
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
    @DisplayName("contains 检查是否包含元素")
    void testContains() {
        list.add("hello");
        assertTrue(list.contains("hello"));
        assertFalse(list.contains("world"));
    }

    @Test
    @DisplayName("indexOf 返回元素首次出现的下标")
    void testIndexOf() {
        list.add("A");
        list.add("B");
        list.add("A");
        assertEquals(0, list.indexOf("A"));
        assertEquals(1, list.indexOf("B"));
        assertEquals(-1, list.indexOf("Z"));
    }

    @Test
    @DisplayName("clear 清空所有元素")
    void testClear() {
        list.add("A");
        list.add("B");
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("toArray 返回包含所有元素的数组")
    void testToArray() {
        list.add("X");
        list.add("Y");
        Object[] arr = list.toArray();
        assertEquals(2, arr.length);
        assertEquals("X", arr[0]);
        assertEquals("Y", arr[1]);
    }

    @Test
    @DisplayName("iterator 可以正确遍历所有元素")
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
    @DisplayName("下标越界抛出 IndexOutOfBoundsException")
    void testRangeCheck() {
        list.add("A");
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    @DisplayName("null 元素支持")
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
