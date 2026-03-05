package com.jayhello.container;

public class ContainerDemo {

    public static void main(String[] args) {
        System.out.println("=== 简化版 Java 容器演示 ===\n");
        demoInheritanceHierarchy();
        demoSimpleArrayList();
        demoSimpleLinkedList();
        demoArrayListVsLinkedList();
        demoSimpleHashMap();
    }

    private static String repeat(String s, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) sb.append(s);
        return sb.toString();
    }

    private static void demoInheritanceHierarchy() {
        System.out.println("【1】容器继承关系演示");
        System.out.println(repeat("─", 50));

        SimpleArrayList<String> arrayList = new SimpleArrayList<>();
        SimpleLinkedList<String> linkedList = new SimpleLinkedList<>();
        SimpleHashMap<String, Integer> hashMap = new SimpleHashMap<>();

        SimpleList<String> list1 = arrayList;
        SimpleList<String> list2 = linkedList;
        SimpleCollection<String> col1 = arrayList;
        SimpleIterable<String> iterable1 = arrayList;
        SimpleMap<String, Integer> map1 = hashMap;

        System.out.println("SimpleArrayList  instanceof SimpleList        : " + (arrayList instanceof SimpleList));
        System.out.println("SimpleArrayList  instanceof SimpleCollection  : " + (arrayList instanceof SimpleCollection));
        System.out.println("SimpleArrayList  instanceof SimpleIterable    : " + (arrayList instanceof SimpleIterable));
        System.out.println("SimpleLinkedList instanceof SimpleList        : " + (linkedList instanceof SimpleList));
        System.out.println("SimpleHashMap    instanceof SimpleMap         : " + (hashMap instanceof SimpleMap));
        System.out.println();
    }

    private static void demoSimpleArrayList() {
        System.out.println("【2】SimpleArrayList（动态数组）演示");
        System.out.println(repeat("─", 50));

        SimpleArrayList<Integer> list = new SimpleArrayList<>();

        System.out.println("初始容量: " + list.capacity() + "，元素数量: " + list.size());

        for (int i = 1; i <= 12; i++) {
            list.add(i);
        }
        System.out.println("添加12个元素后 → 容量: " + list.capacity() + "，元素数量: " + list.size());
        System.out.println("内容: " + list);

        System.out.println("get(0) = " + list.get(0) + "，get(11) = " + list.get(11));

        list.add(0, 100);
        System.out.println("在下标0插入100后: " + list);

        list.remove(0);
        System.out.println("删除下标0后: " + list);

        System.out.println("indexOf(7) = " + list.indexOf(7));
        System.out.println("contains(5) = " + list.contains(5));

        System.out.print("迭代输出: ");
        SimpleIterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println("\n");
    }

    private static void demoSimpleLinkedList() {
        System.out.println("【3】SimpleLinkedList（双向链表）演示");
        System.out.println(repeat("─", 50));

        SimpleLinkedList<String> list = new SimpleLinkedList<>();

        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        System.out.println("添加 A/B/C/D 后: " + list);

        list.add(2, "X");
        System.out.println("在下标2插入X后: " + list);

        list.remove("X");
        System.out.println("删除X后: " + list);

        System.out.println("get(1) = " + list.get(1));

        System.out.println();
    }

    private static void demoArrayListVsLinkedList() {
        System.out.println("【4】ArrayList vs LinkedList 性能对比（末尾追加 50000 次）");
        System.out.println(repeat("─", 50));

        int n = 50000;

        long t1 = System.nanoTime();
        SimpleArrayList<Integer> arrayList = new SimpleArrayList<>();
        for (int i = 0; i < n; i++) {
            arrayList.add(i);
        }
        long arrayListTime = System.nanoTime() - t1;

        long t2 = System.nanoTime();
        SimpleLinkedList<Integer> linkedList = new SimpleLinkedList<>();
        for (int i = 0; i < n; i++) {
            linkedList.add(i);
        }
        long linkedListTime = System.nanoTime() - t2;

        System.out.printf("SimpleArrayList  末尾追加 %d 次耗时: %d ms%n", n, arrayListTime / 1_000_000);
        System.out.printf("SimpleLinkedList 末尾追加 %d 次耗时: %d ms%n", n, linkedListTime / 1_000_000);

        long t3 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            arrayList.get(n / 2);
        }
        long arrayGetTime = System.nanoTime() - t3;

        long t4 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            linkedList.get(n / 2);
        }
        long linkedGetTime = System.nanoTime() - t4;

        System.out.printf("SimpleArrayList  随机访问 1000 次耗时: %d ms%n", arrayGetTime / 1_000_000);
        System.out.printf("SimpleLinkedList 随机访问 1000 次耗时: %d ms%n", linkedGetTime / 1_000_000);
        System.out.println("→ ArrayList 随机访问 O(1)，LinkedList 随机访问 O(n)，差异明显\n");
    }

    private static void demoSimpleHashMap() {
        System.out.println("【5】SimpleHashMap（哈希表）演示");
        System.out.println(repeat("─", 50));

        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();

        System.out.println("初始桶容量: " + map.capacity());

        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);
        map.put("date", 4);
        System.out.println("存入 4 个键值对后: " + map);

        System.out.println("get(\"apple\") = " + map.get("apple"));
        System.out.println("get(\"fig\")   = " + map.get("fig"));

        map.put("apple", 100);
        System.out.println("覆盖 apple 后: get(\"apple\") = " + map.get("apple"));

        System.out.println("containsKey(\"banana\") = " + map.containsKey("banana"));
        System.out.println("containsValue(3)      = " + map.containsValue(3));

        map.remove("banana");
        System.out.println("删除 banana 后: " + map);

        System.out.println("keySet() = " + map.keySet());

        System.out.println("\n--- 扩容演示 ---");
        SimpleHashMap<Integer, Integer> bigMap = new SimpleHashMap<>(4, 0.75f);
        System.out.println("初始桶容量: " + bigMap.capacity() + "，阈值约 " + (int)(4 * 0.75f));
        for (int i = 1; i <= 10; i++) {
            bigMap.put(i, i * i);
            System.out.printf("  put(%2d, %3d) → size=%2d, capacity=%d%n",
                    i, i * i, bigMap.size(), bigMap.capacity());
        }
        System.out.println("→ 可观察到 size 超过阈值后容量翻倍\n");
    }
}
