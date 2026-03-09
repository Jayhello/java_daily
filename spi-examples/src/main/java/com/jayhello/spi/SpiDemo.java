package com.jayhello.spi;

import java.util.List;

public class SpiDemo {

    public static void main(String[] args) {
        List<Animal> animals = CustomServiceLoader.load(Animal.class).loadAll();
        for (Animal animal : animals) {
            System.out.println(animal.getName() + " says: " + animal.sound());
        }
    }
}
