package com.jayhello.spi.impl;

import com.jayhello.spi.Animal;

/**
 * Animal SPI 实现：猫
 */
public class CatImpl implements Animal {

    @Override
    public String getName() {
        return "Cat";
    }

    @Override
    public String sound() {
        return "Meow";
    }
}
