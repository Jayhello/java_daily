package com.jayhello.spi;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

class SpiTest {

    @Test
    void testServiceLoaderFindsExactlyTwoImplementations() {
        ServiceLoader<Animal> loader = ServiceLoader.load(Animal.class);
        List<Animal> animals = new ArrayList<>();
        for (Animal animal : loader) {
            animals.add(animal);
        }
        assertEquals(2, animals.size());
    }

    @Test
    void testAllImplementationsReturnNonNullName() {
        ServiceLoader<Animal> loader = ServiceLoader.load(Animal.class);
        for (Animal animal : loader) {
            assertNotNull(animal.getName());
            assertFalse(animal.getName().isEmpty());
        }
    }

    @Test
    void testAllImplementationsReturnNonNullSound() {
        ServiceLoader<Animal> loader = ServiceLoader.load(Animal.class);
        for (Animal animal : loader) {
            assertNotNull(animal.sound());
            assertFalse(animal.sound().isEmpty());
        }
    }

    @Test
    void testDogImplementation() {
        Animal dog = findAnimalByName("Dog");
        assertNotNull(dog);
        assertEquals("Woof", dog.sound());
    }

    @Test
    void testCatImplementation() {
        Animal cat = findAnimalByName("Cat");
        assertNotNull(cat);
        assertEquals("Meow", cat.sound());
    }

    private Animal findAnimalByName(String name) {
        ServiceLoader<Animal> loader = ServiceLoader.load(Animal.class);
        for (Animal animal : loader) {
            if (name.equals(animal.getName())) {
                return animal;
            }
        }
        return null;
    }
}
