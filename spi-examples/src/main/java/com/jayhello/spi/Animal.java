package com.jayhello.spi;

/**
 * SPI 接口示例：定义动物的行为
 * 通过 java.util.ServiceLoader 在运行时加载具体实现
 */
public interface Animal {

    /**
     * 返回动物名称
     */
    String getName();

    /**
     * 返回动物叫声
     */
    String sound();
}
