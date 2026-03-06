package com.jayhello.string;

public class MyStringBuilder {
    private char[] buf;
    private int idx = 0;
    private int capacity = 16;

    public MyStringBuilder(){
        buf = new char[capacity];
    }

    public MyStringBuilder append(String str){

        return this;
    }

    public String toString(){
        String res = new String(buf);
        return res;
    }
}
