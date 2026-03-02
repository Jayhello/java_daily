package com.jayhello.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TemplateExample {
    public static void case1(){
        List<String> strList = new ArrayList<String>();
        List<Integer> intList = new ArrayList<Integer>();
        System.out.println(strList.getClass()); // 都输出class java.util.ArrayList
        System.out.println(intList.getClass());
    }

    public static void case2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(1);  //这样调用 add 方法只能存储整形，因为泛型类型的实例为 Integer

        list.getClass().getMethod("add", Object.class).invoke(list, "asd");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        case1();
//        case2();
    }
}
