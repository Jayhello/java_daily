package com.jayhello.spi.impl;

import com.jayhello.spi.Animal;

/**
 * Animal SPI 实现：狗
 */
public class DogImpl implements Animal {

    @Override
    public String getName() {
        return "Dog";
    }

    @Override
    public String sound() {
        return "Woof";
    }
}
