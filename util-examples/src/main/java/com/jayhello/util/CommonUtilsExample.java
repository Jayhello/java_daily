package com.jayhello.util;

import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 常用工具类示例
 */
public class CommonUtilsExample {
    
    /**
     * 字符串工具示例
     */
    public void stringUtilsDemo() {
        String str = "  Hello World  ";
        
        // Apache Commons Lang
        System.out.println("isEmpty: " + StringUtils.isEmpty(str));
        System.out.println("isBlank: " + StringUtils.isBlank(str));
        System.out.println("trim: " + StringUtils.trim(str));
        System.out.println("capitalize: " + StringUtils.capitalize("hello"));
    }
    
    /**
     * 集合工具示例
     */
    public void collectionUtilsDemo() {
        // Guava Lists
        List<String> list = Lists.newArrayList("A", "B", "C");
        List<List<String>> partitions = Lists.partition(list, 2);
        
        System.out.println("原始列表: " + list);
        System.out.println("分区列表: " + partitions);
    }
    
    public static void main(String[] args) {
        CommonUtilsExample example = new CommonUtilsExample();
        
        System.out.println("=== 字符串工具示例 ===");
        example.stringUtilsDemo();
        
        System.out.println("\n=== 集合工具示例 ===");
        example.collectionUtilsDemo();
    }
}
