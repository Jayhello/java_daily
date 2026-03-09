package com.jayhello.spi.impl;

import com.jayhello.spi.Animal;

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
